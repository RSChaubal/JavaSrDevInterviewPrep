package org.rsc.practice;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class CustomBlockingQueueReentrantLock<E> {

    private static final Logger logger = Logger.getLogger(CustomBlockingQueue.class.getName()) ;

    private int capacity ;
    private Queue<E> queue ;
    private ReentrantLock lock = new ReentrantLock() ;
    private Condition notFull = lock.newCondition() ;
    private Condition notEmpty = lock.newCondition() ;

    public CustomBlockingQueueReentrantLock(int capacity) {
        queue = new LinkedList<>() ;
        this.capacity = capacity ;
    }

    public void put(E element) throws InterruptedException {
        lock.lock() ;
        try {
            while (capacity == queue.size()) {
                notFull.await() ;
            }
            queue.add(element) ;
            notEmpty.signalAll() ;
        }
        finally {
            lock.unlock() ;
        }
    }

    public E take() throws InterruptedException {
        lock.lock() ;
        try {
            while (queue.isEmpty()) {
                notEmpty.await() ;
            }
            E element = queue.remove() ;
            notFull.signalAll() ;
            return element ;
        }
        finally {
            lock.unlock() ;
        }
    }

    public static void main(String[] args) {
        logger.info("Entering main()");
        try {
            testBasicPutAndTake();
            testTakeBlocksWhenEmpty();
            //testPutBlocksWhenFull();
            testMultiThreadedStress();
            testSingleElementQueue();
            System.out.println("All tests passed!");
        } catch (InterruptedException e) {
            System.err.println("Test interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        logger.info("Exiting main()");
    }

    private static void testBasicPutAndTake() throws InterruptedException {
        logger.info("Entering testBasicPutAndTake()");
        CustomBlockingQueue<Integer> queue = new CustomBlockingQueue<>(2);
        queue.put(1);
        queue.put(2);
        assert queue.take() == 1 : "Test 1 failed";
        assert queue.take() == 2 : "Test 1 failed";
        logger.info("Exiting testBasicPutAndTake()");
    }

    private static void testTakeBlocksWhenEmpty() throws InterruptedException {
        logger.info("Entering testTakeBlocksWhenEmpty()");
        CustomBlockingQueue<Integer> queue2 = new CustomBlockingQueue<>(1);
        Thread taker = new Thread(() -> {
            try {
                assert queue2.take() == 42 : "Test 2 failed";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        taker.start();
        Thread.sleep(100);
        queue2.put(42);
        taker.join();
        logger.info("Exiting testTakeBlocksWhenEmpty()");
    }

    private static void testPutBlocksWhenFull() throws InterruptedException {
        logger.info("Entering testPutBlocksWhenFull()");
        CustomBlockingQueue<Integer> queue3 = new CustomBlockingQueue<>(1);
        queue3.put(99);

        final java.util.concurrent.CountDownLatch readyToPut = new java.util.concurrent.CountDownLatch(1);
        Thread putter = new Thread(() -> {
            try {
                readyToPut.await(); // Wait until main thread is ready to take
                queue3.put(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        putter.start();

        assert queue3.take() == 99 : "Test 3 failed";
        // Signal the putter thread to proceed, then take the element
        readyToPut.countDown();
        putter.join();

        // Now the putter should have successfully put the second element
        assert queue3.take() == 100 : "Test 3 failed";
        logger.info("Exiting testPutBlocksWhenFull()");
    }

    private static void testMultiThreadedStress() throws InterruptedException {
        logger.info("Entering testMultiThreadedStress()");
        final CustomBlockingQueue<Integer> queue4 = new CustomBlockingQueue<>(10);
        final int producers = 5, consumers = 5, itemsPerProducer = 1000;
        Thread[] threads = new Thread[producers + consumers];

        for (int i = 0; i < producers; i++) {
            final int id = i;
            threads[i] = new Thread(() -> {
                logger.info("Producer " + id + " started");
                try {
                    for (int j = 0; j < itemsPerProducer; j++) {
                        queue4.put(id * itemsPerProducer + j);
                    }
                    logger.info("Producer " + id + " finished");
                } catch (InterruptedException e) {
                    logger.warning("Producer " + id + " interrupted");
                    throw new RuntimeException(e);
                }
            });
        }

        final int[] consumed = new int[producers * itemsPerProducer];
        for (int i = 0; i < consumers; i++) {
            final int consumerId = i;
            threads[producers + i] = new Thread(() -> {
                logger.info("Consumer " + consumerId + " started");
                try {
                    for (int j = 0; j < (producers * itemsPerProducer) / consumers; j++) {
                        int val = queue4.take();
                        consumed[val] = 1;
                    }
                    logger.info("Consumer " + consumerId + " finished");
                } catch (InterruptedException e) {
                    logger.warning("Consumer " + consumerId + " interrupted");
                    throw new RuntimeException(e);
                }
            });
        }

        logger.info("Starting all producer and consumer threads");
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        logger.info("All producer and consumer threads have completed");

        for (int i = 0; i < consumed.length; i++) {
            if (consumed[i] != 1) {
                logger.severe("Test 5 failed at " + i);
            }
            assert consumed[i] == 1 : "Test 5 failed at " + i;
        }
        logger.info("Exiting testMultiThreadedStress()");
    }

    private static void testSingleElementQueue() throws InterruptedException {
        logger.info("Entering testSingleElementQueue()");
        CustomBlockingQueue<String> singleQueue = new CustomBlockingQueue<>(1);
        singleQueue.put("A");
        assert singleQueue.take().equals("A") : "Test 6 failed";
        logger.info("Exiting testSingleElementQueue()");
    }
}
