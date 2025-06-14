package org.rsc.practice;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCacheWithReentrantLock {

    protected final int capacity ;
    protected final ConcurrentHashMap<Integer, Integer> cache ;
    protected final ConcurrentLinkedDeque<Integer> accessOrder ;
    protected final ReentrantLock lock = new ReentrantLock() ;

    public LRUCacheWithReentrantLock(int capacity) {
        this.capacity = capacity ;
        cache = new ConcurrentHashMap<Integer, Integer>() ;
        accessOrder = new ConcurrentLinkedDeque<>() ;
    }

    public int get(int key) {
        lock.lock() ;
        try {
            if (capacity <= 0 || !cache.containsKey(key)) {
                return  -1 ;
            }
            else {
                accessOrder.remove(key) ;
                accessOrder.addFirst(key) ;
                return cache.get(key) ;
            }
        } finally {
            lock.unlock() ;
        }
    }

    public void put(int key, int value) {
        lock.lock() ;
        try {
            if (capacity <= 0) {
                return ;
            }

            if (cache.size() >= capacity) {
                // Use remove last. It will throw an exception if the
                // queue is empty. Whereas PollLast, instead of throwing
                // and exception, returns a null value if the queue is
                // empty.
                Integer removalKey = accessOrder.removeLast() ;
                cache.remove(removalKey) ;
            }
            accessOrder.addFirst(key) ;
            cache.put(key, value) ;
        }
        finally {
            lock.unlock() ;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        // Test 1: Accessing non-existent key
        LRUCacheWithReentrantLock cache = new LRUCacheWithReentrantLock(2);
        assert cache.get(1) == -1 : "Test 1 failed";

        // Test 2: Adding elements up to capacity
        cache.put(1, 10);
        cache.put(2, 20);
        assert cache.get(1) == 10 : "Test 2 failed";
        assert cache.get(2) == 20 : "Test 2 failed";

        // Test 3: Adding element beyond capacity (eviction)
        cache.put(3, 30); // Should evict key 1
        assert cache.get(1) == -1 : "Test 3 failed";
        assert cache.get(2) == 20 : "Test 3 failed";
        assert cache.get(3) == 30 : "Test 3 failed";

        // Test 4: Updating existing key
        cache.put(2, 200);
        assert cache.get(2) == 200 : "Test 4 failed";

        // Test 5: Accessing a key to make it most recently used
        cache.get(3); // Now 2 is LRU
        cache.put(4, 40); // Should evict key 2
        assert cache.get(2) == -1 : "Test 5 failed";
        assert cache.get(3) == 30 : "Test 5 failed";
        assert cache.get(4) == 40 : "Test 5 failed";

        // Test 6: Repeatedly accessing the same key
        cache.get(3);
        cache.get(3);
        cache.put(5, 50); // Should evict key 4
        assert cache.get(4) == -1 : "Test 6 failed";
        assert cache.get(3) == 30 : "Test 6 failed";
        assert cache.get(5) == 50 : "Test 6 failed";

        // Test 7: Capacity 1
        LRUCacheWithReentrantLock singleCache = new LRUCacheWithReentrantLock(1);
        singleCache.put(1, 100);
        assert singleCache.get(1) == 100 : "Test 7 failed";
        singleCache.put(2, 200);
        assert singleCache.get(1) == -1 : "Test 7 failed";
        assert singleCache.get(2) == 200 : "Test 7 failed";

        // Test 8: Capacity 0 (should not store anything)
        LRUCacheWithReentrantLock zeroCache = new LRUCacheWithReentrantLock(0);
        zeroCache.put(1, 1);
        assert zeroCache.get(1) == -1 : "Test 8 failed";

        // Test 9: Negative capacity (should not store anything)
        LRUCacheWithReentrantLock negCache = new LRUCacheWithReentrantLock(-1);
        negCache.put(1, 1);
        assert negCache.get(1) == -1 : "Test 9 failed";

        // Test 10: Duplicate puts (should update and move to front)
        LRUCacheWithReentrantLock dupCache = new LRUCacheWithReentrantLock(2);
        dupCache.put(1, 1);
        dupCache.put(1, 2);
        assert dupCache.get(1) == 2 : "Test 10 failed";
        dupCache.put(2, 2);
        dupCache.put(3, 3); // Should evict 1
        assert dupCache.get(1) == -1 : "Test 10 failed";

        // Test 11: Thread safety and race conditions
        final LRUCacheWithReentrantLock threadCache = new LRUCacheWithReentrantLock(50);
        final int threads = 10;
        final int opsPerThread = 1000;
        final CountDownLatch latch = new CountDownLatch(threads);
        final AtomicBoolean failed = new AtomicBoolean(false);

        for (int t = 0; t < threads; t++) {
            final int threadId = t;
            new Thread(() -> {
                try {
                    for (int i = 0; i < opsPerThread; i++) {
                        int key = (threadId * opsPerThread + i) % 100;
                        threadCache.put(key, key);
                        int val = threadCache.get(key);
                        if (val != key) {
                            failed.set(true);
                        }
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        assert !failed.get() : "Test 11 (thread safety) failed";

        System.out.println("All tests passed!");
    }

}
