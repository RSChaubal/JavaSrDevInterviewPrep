package org.rsc.practice;

import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;


/**
 *  Program for performing summation of a huge array list.
 *
 *  Last execution numbers:
 *      Sum: 5000000050000000
 *      Time taken: 253 ms
 *      Array lenght: 100000000
 */
public class SummationOfArrayList {

    public static void main(String[] args) {

        //  Using int[] instead of ArrayList<Integer> gives a big
        //  performance gain (no boxing).
        int [] arr = IntStream.rangeClosed(1, 100_000_000).toArray() ;

        LongAdder sum = new LongAdder() ;
        long start = System.currentTimeMillis() ;
        IntStream.range(0, arr.length).parallel().forEach(i -> sum.add(arr[i])) ;
        long end = System.currentTimeMillis() ;

        System.out.println("Sum: " + sum.sum()) ;
        System.out.println("Time taken: " + (end - start) + " ms") ;
        System.out.println("Array lenght: " + arr.length) ;

        // The reason this works fast is:
        // Usage of primitives. Using regular for loop on ArrayList<Integer> would result in a slow performance due to boxing/unboxing.
        // Usage of LongAdder. Using synchronized or atomic values would unnecessarily slow down the entire process because of locking and waiting.

        /*
            AtomicInteger vs LongAdder

            1) Internal Design
                -> AtomicInteger
                    - Backed by a single variable.
                    - Uses Compare-And-Swap (CAS) for all updates.
                    - All threads contend on the same memory location.
                -> LongAdder
                    - Uses a striped array of counters (cells).
                    - Each thread updates a different cell to reduce contention.
                    - On retrieval (sum()), all cells are combined.

            2) Performance
                -> AtomicInteger
                    - It is better for Low contention scenario (fewer threads) because it has simpler and more memory-efficient
                -> LongAdder
                    - It is better for High contention (many threads) because it reduces CAS collisions and scales better

            3) Read Semantics
                -> AtomicInteger
                    - AtomicInteger.get() gives a precise, up-to-date value.
                -> LongAdder
                    - gives an approximate value (may slightly lag due to parallel cell structure), but typically accurate enough for most use cases like counters, stats, etc.

         */
    }

}
