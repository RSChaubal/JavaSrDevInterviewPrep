package org.rsc.practice;

public class PalindromeNumber {

    public static void main(String[] args) {
        int [] tests = {12321, 1221, 111, 102193, 0, -102} ;

        for (int test : tests) {
            try {
                System.out.println("Number " + test + " is palindrome: " + isPalindromeNumber(test)) ;
            }
            catch (IllegalArgumentException iaex) {
                System.out.println(iaex.getMessage()) ;
            }
        }
    }

    protected static boolean isPalindromeNumber(long number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number cannot be less than zero.") ; // Can be modified based on requirements
        }

        // Handle single digit numbers. (These will always be palindrome)
        if (number >= 0 && number <= 9) {
            return Boolean.TRUE ;
        }

        long reversed = 0 ;
        long original = number ;
        while (number > 0) {
            long lastDigit = number % 10 ; // Extract last digit from the given number
            reversed = reversed * 10 + lastDigit ;
            number /= 10 ; // Remove last digit from the given number
        }

        return reversed == original ;
    }


}
