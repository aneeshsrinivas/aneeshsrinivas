class Solution {
    public int maximumDifference(int[] nums) {
        int minValue = nums[0]; // Start with the first element as the minimum
        int maxDiff = -1; // Initialize the maximum difference as -1
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > minValue) {
                maxDiff = Math.max(maxDiff, nums[i] - minValue); // Update max difference
            }
            minValue = Math.min(minValue, nums[i]); // Update the minimum value
        }
        
        return maxDiff;
    }
}
