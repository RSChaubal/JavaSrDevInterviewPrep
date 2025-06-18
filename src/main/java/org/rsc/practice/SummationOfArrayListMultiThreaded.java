package org.rsc.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *  Program for performing summation of a huge array list using multithreading concepts.
 *
 *  Last execution numbers:
 *  Total sum: 5000000050000000
 *  Time taken: 243 ms
 */
public class SummationOfArrayListMultiThreaded {

    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors() ;

    // A small task class to run summation in small chunks
    // on different threads
    static class SumTask implements Callable<Long> {
        private final List<Integer> list ;
        private final int start, end ;

        SumTask(List<Integer> list, int start, int end) {
            this.list = list ;
            this.start = start ;
            this.end = end ;
        }

        public Long call() {
            long sum = 0 ;
            for (int i = start; i < end; i++) {
                sum += list.get(i) ;
            }
            return sum ;
        }
    }


    public static long parallelSum(List<Integer> list) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS) ;
        List<Future<Long>> futures = new ArrayList<>() ;

        int chunkSize = list.size() / NUM_THREADS ;
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * chunkSize ;
            int end = (i == NUM_THREADS - 1) ? list.size() : start + chunkSize ;
            futures.add(executorService.submit(new SumTask(list, start, end))) ;
        }

        long total = 0 ;
        for (Future<Long> future: futures) {
            total += future.get() ;
        }

        executorService.shutdown() ;
        return total ;
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        List<Integer> numbers = new ArrayList<>(100_000_000) ;
        for(int i = 1; i <= 100_000_000; i++) {
            numbers.add(i) ;
        }

        long startTime = System.currentTimeMillis() ;
        long totalSum = parallelSum(numbers) ;
        long endTime = System.currentTimeMillis() ;

        System.out.println("Total sum: " + totalSum) ;
        System.out.println("Time taken: " + (endTime - startTime) + " ms") ;
    }

    /**
     * Do we need BigInteger for summing a huge ArrayList<Integer>?
     * No, not unless the total sum exceeds the maximum value of a long, which is: Long.MAX_VALUE = 9,223,372,036,854,775,807
     */
}



