package org.rsc.algos.twopointers;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * You can return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
 * Example 2:
 *
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 * Example 3:
 *
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 */
public class TwoPointerSumWoSorting {

    public static void main(String[] args) {

        int [] arr = {2,7,11,15} ;
        int sum = 9 ;
        int [] res = twoSum(arr, sum) ;
        System.out.println("Printing output from the first test case: {2,7,11,15} ") ;
        System.out.println(Arrays.toString(res)) ;

        int [] arr1 = {3,2,4} ;
        int sum1 = 6 ;
        int [] res1 = twoSum(arr1, sum1) ;
        System.out.println("Printing output from the second test case: {3,2,4} ") ;
        System.out.println(Arrays.toString(res1)) ;

        int [] arr2 = {3,3} ;
        int sum2 = 6 ;
        int [] res2 = twoSum(arr2, sum2) ;
        System.out.println("Printing output from the third test case: {3,3} ") ;
        System.out.println(Arrays.toString(res2)) ;
    }

    public static int[] twoSum(int[] nums, int target) {

        Map<Integer, Integer> map = new HashMap<>() ;

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i] ;

            if (map.containsKey(complement)) {
                return new int[] {map.get(complement), i} ;
            }
            map.put(nums[i], i) ;
        }
        return new int[] {0, 0} ;
    }
}
