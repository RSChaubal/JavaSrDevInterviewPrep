package org.rsc.algos.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/3sum/description/
 *
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 *
 * Notice that the solution set must not contain duplicate triplets.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * Explanation:
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
 * The distinct triplets are [-1,0,1] and [-1,-1,2].
 * Notice that the order of the output and the order of the triplets does not matter.
 *
 *
 * Example 2:
 *
 * Input: nums = [0,1,1]
 * Output: []
 * Explanation: The only possible triplet does not sum up to 0.
 *
 *
 * Example 3:
 *
 * Input: nums = [0,0,0]
 * Output: [[0,0,0]]
 * Explanation: The only possible triplet sums up to 0.
 */
public class ThreeVariableSum {

    public static void main(String[] args) {

        int [] arr = {-1,0,1,2,-1,-4} ;
        List<List<Integer>> ans = findTriplets(arr) ;
        System.out.println("\n Printing 1st test case output: ") ;
        for (List<Integer> i : ans) {
            System.out.print(i + " ") ;
        }

        int [] arr1 = {0,1,1} ;
        List<List<Integer>> ans1 = findTriplets(arr1) ;
        System.out.println("\n Printing 2nd test case output: ") ;
        for (List<Integer> i : ans1) {
            System.out.print(i + " ") ;
        }

        int [] arr2 = {0,0,0} ;
        List<List<Integer>> ans2 = findTriplets(arr2) ;
        System.out.println("\n Printing 3rd test case output: ") ;
        for (List<Integer> i : ans2) {
            System.out.print(i + " ") ;
        }

        int [] arr3 = {2,-3,0,-2,-5,-5,-4,1,2,-2,2,0,2,-4,5,5,-10} ;
        List<List<Integer>> ans3 = findTriplets(arr3) ;
        System.out.println("\n Printing 4th test case output: ") ;
        for (List<Integer> i : ans3) {
            System.out.print(i + " ") ;
        }
        System.out.println("\n") ;
    }

    public static List<List<Integer>> findTriplets(int[] nums) {

        // Sort the array to avoid more nested loops and repeating any sequence
        Arrays.sort(nums) ;
        List<List<Integer>> finalTripletsList = new ArrayList<>() ;

        for (int i = 0; i < nums.length-2; i++) {

            if (i > 0 && nums[i] == nums[i - 1]) continue ; // Skip duplicate i

            int j = i + 1 ;
            int k = nums.length - 1 ;
            while (j < k) {
                if (nums[i] + nums[j] + nums[k] == 0) {
                    finalTripletsList.add(Arrays.asList(nums[i], nums[j], nums[k])) ;

                    // Skip duplicates from both the ends
                    while(j < k && nums[j]==nums[j+1]) {j++;}
                    while(j < k && nums[k]==nums[k-1]) {k--;}

                    j++ ;
                    k-- ;
                }
                else if (nums[i] + nums[j] + nums[k] < 0) {
                    j++ ;
                }
                else if (nums[i] + nums[j] + nums[k] > 0) {
                    k-- ;
                }
            }
        }
        return finalTripletsList ;
    }
}


/**
 * Complexity
 *
 * Sorting: O(n log n)
 * Outer loop: O(n)
 * Two-pointer search: O(n)
 * -------------------------------
 * Total time complexity: O(n²)
 *
 * Space: O(1) (ignoring output).
 *
 */