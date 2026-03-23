package org.rsc.algos.prefixsum;

/**
 * Range Sum Query - Immutable
 * 
 * Problem: Given an integer array, handle multiple queries to calculate
 * the sum of elements between indices left and right (inclusive).
 * 
 * Approach: Prefix Sum Array
 * - Time: O(n) initialization, O(1) per query
 * - Space: O(n) for prefix sum array
 * 
 * The prefix sum at index i represents the sum of all elements from 0 to i-1.
 * For a range [left, right], the sum = prefixSum[right+1] - prefixSum[left]
 */
public class RangeSumQuery {
    
    private int[] prefixSum;
    
    /**
     * Initialize with the integer array.
     * Build prefix sum array where prefixSum[i] = sum(nums[0...i-1])
     * 
     * @param nums the input array
     */
    public RangeSumQuery(int[] nums) {
        // prefixSum[0] = 0, prefixSum[1] = nums[0], prefixSum[2] = nums[0] + nums[1], etc.
        prefixSum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
    }
    
    /**
     * Calculate the sum of elements between indices left and right (inclusive).
     * 
     * @param left the start index
     * @param right the end index
     * @return the sum of elements from left to right
     */
    public int sumRange(int left, int right) {
        // Sum from left to right = prefixSum[right+1] - prefixSum[left]
        // prefixSum[right+1] gives sum from 0 to right
        // prefixSum[left] gives sum from 0 to left-1
        // Subtracting gives sum from left to right
        return prefixSum[right + 1] - prefixSum[left];
    }
    
    public static void main(String[] args) {
        // Test case from the problem
        int[] arr = {-2, 0, 3, -5, 2, -1};
        RangeSumQuery numArray = new RangeSumQuery(arr);
        
        System.out.println("Test Cases:");
        System.out.println("sumRange(0, 2) = " + numArray.sumRange(0, 2)); // Expected: 1 (-2 + 0 + 3)
        System.out.println("sumRange(2, 5) = " + numArray.sumRange(2, 5)); // Expected: -1 (3 + -5 + 2 + -1)
        System.out.println("sumRange(0, 5) = " + numArray.sumRange(0, 5)); // Expected: -3 (sum of all)
    }
}
