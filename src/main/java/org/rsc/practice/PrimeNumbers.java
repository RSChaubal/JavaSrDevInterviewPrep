package org.rsc.practice;

public class PrimeNumbers {

    public static boolean isPrime(long num) {
        // Numbers <= 1 are not prime
        if (num <= 1) {
            return Boolean.FALSE ;
        }

        // Two is the only even prime number
        if (num == 2) {
            return Boolean.TRUE ;
        }

        // Even numbers > 2 are not prime
        if (num % 2 == 0) {
            return Boolean.FALSE ;
        }

        // Start from 3 and increment by 2 (skip the even numbers)
        // Also, iterate only till the square root of the number
        // for saving iterations and providing better performance
        for (int i = 3; i * i <= num ; i += 2) {
            if (num % i == 0) {
                return Boolean.FALSE ;
            }
        }

        return Boolean.TRUE ;
    }

    public static boolean isPrime(int num) {
        return isPrime((long) num) ;
    }

    public static void main(String[] args) {
        int [] tests = {2, 9, 832, 43, 77321} ;
        long [] testsL = {97L, 100L, 999999937L} ;

        System.out.println("Testing integers...") ;
        for (int test : tests) {
            System.out.println("Number " + test + " isPrime: " + isPrime(test)) ;
        }

        System.out.println("\nTesting longs...") ;
        for (long test : testsL) {
            System.out.println("Number " + test + " isPrime: " + isPrime(test)) ;
        }
    }
}
