package org.rsc.practice;

import java.util.HashMap;
import java.util.Map;

public class MaxCharCount {

    public static void main(String[] args) {
        String [] tests = {"aabbac", "hello", "", "s"} ;

        for (String test : tests) {
            try {
                System.out.println("The character occurring maximum number of times in string " + test + " is: " + findMaxCharCount(test)) ;
            }
            catch (IllegalArgumentException iaex) {
                System.out.println(iaex.toString()) ;
            }
        }
    }

    private static char findMaxCharCount(String str) {

        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("String cannot be null or empty") ;
        }

        Map<Character, Integer> charCountMap = new HashMap<>() ;
        for (char c : str.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0)+ 1) ;
        }

        char maxChar = str.charAt(0) ;
        int maxCharCount = 0 ;
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            if (entry.getValue() > maxCharCount) {
                maxCharCount = entry.getValue() ;
                maxChar = entry.getKey() ;
            }
        }
        return maxChar ;
    }

}
