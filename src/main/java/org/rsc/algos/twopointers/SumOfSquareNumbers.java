package org.rsc.algos.twopointers;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;

/**
 * https://leetcode.com/problems/sum-of-square-numbers/description/
 *
 * Given a non-negative integer c, decide whether there're two integers a and b such that a2 + b2 = c.
 *
 *
 * Example 1:
 *
 * Input: c = 5
 * Output: true
 * Explanation: 1 * 1 + 2 * 2 = 5
 * Example 2:
 *
 * Input: c = 3
 * Output: false
 *
 *
 * Constraints:
 *
 * 0 <= c <= 231 - 1
 */
public class SumOfSquareNumbers {

    public static void main(String[] args) {

        System.out.println("Judge Square Sum for number 5 - " + judgeSquareSum(5)) ;
        System.out.println("Judge Square Sum for number 3 - " + judgeSquareSum(3)) ;
        System.out.println("Judge Square Sum for number 4 - " + judgeSquareSum(4)) ;
        System.out.println("Judge Square Sum for number 2 - " + judgeSquareSum(2)) ;
        System.out.println("Judge Square Sum for number 1 - " + judgeSquareSum(1)) ;

    }

    public static boolean judgeSquareSum(int c) {
        long i = 0;
        long j = (long) Math.sqrt(c) ;

        while (i <= j) {
            long iSquare = i * i ;
            long jSquare = j * j ;

            if (iSquare + jSquare == c) {
                return Boolean.TRUE ;
            }
            else if (iSquare + jSquare < c) {
                i++ ;
            }
            else {
                j-- ;
            }
        }
        return Boolean.FALSE ;
    }

}
