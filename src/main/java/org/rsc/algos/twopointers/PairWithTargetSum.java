package org.rsc.algos.twopointers;

/*
 *
 * Core Idea
 * You maintain two pointers that can move in the same or opposite directions, depending on the problem:
 *
 * 1. Start and End Pointers
 * Use when the array is sorted, and you're looking for pairs that meet a condition.
 *
 * Example: left = 0, right = n - 1
 *
 * Move them inward based on conditions.
 *
 * 2. Fast and Slow Pointers (Variant)
 * Use when traversing at different speeds to detect patterns like duplicates, cycles, etc.
 *
 */


import java.util.Arrays;

/*
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/
 *
 * Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order, find two numbers such that
 * they add up to a specific target number.
 *
 * Let these two numbers be numbers[index1] and numbers[index2] where 1 <= index1 < index2 <= numbers.length.
 *
 * Return the indices of the two numbers, index1 and index2, added by one as an integer array [index1, index2] of length 2.
 *
 * The tests are generated such that there is exactly one solution. You may not use the same element twice.
 *
 * Your solution must use only constant extra space.
 *
 * Example 1:
 *
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
 * Example 2:
 *
 * Input: numbers = [2,3,4], target = 6
 * Output: [1,3]
 * Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].
 * Example 3:
 *
 * Input: numbers = [-1,0], target = -1
 * Output: [1,2]
 * Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We return [1, 2].
 */
public class PairWithTargetSum {

    public static void main(String[] args) {
        int [] arr = {2,7,11,15} ;
        int sum = 9 ;

        int [] ans = twoSum(arr, sum) ;

        System.out.print(Arrays.toString(ans)) ;
    }


    public static int[] twoSum(int[] numbers, int target) {
        int [] ans = new int[2] ;
        int count = 0, i = 0, j = numbers.length-1 ;
        while  (i <= j) {
            if (numbers[i] + numbers[j] == target) {
                ans[count++] = i+1 ;
                ans[count++] = j+1 ;
                break ;
            }
            else if (numbers[i] + numbers[j] < target) {
                i = i + 1 ;
            }
            else {
                j = j - 1 ;
            }
        }
        return ans ;
    }

}


