package org.rsc.practice;

public class Palindrome {

    public static void main(String[] args) {
        String [] tests = {"bbaa", "bacCab", "110011", "", "zz", "z"} ;

        for (String test : tests) {
            try {
                System.out.println("String " + test + " is palindrome - " + isPalindrome(test)) ;
            }
            catch (IllegalArgumentException iaex) {
                System.out.println(iaex.getMessage()) ;
            }
        }
    }

    public static boolean isPalindrome(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("String cannot be null or empty") ;
        }

        str = str.toLowerCase() ;

        int start = 0 ;
        int end = str.length() - 1 ;
        char [] arr = str.toCharArray() ;
        boolean palindrome = Boolean.TRUE ;
        while (start < end) {
            if (arr[start] != arr[end]) {
                palindrome = Boolean.FALSE ;
                break ;
            }
            start ++ ;
            end -- ;
        }
        return palindrome ;
    }

}
