class Solution {
    public long countSubarrays(int[] nums, int minK, int maxK) {
        long count = 0;
        int minPosition = -1, maxPosition = -1, startPosition = -1;
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < minK || nums[i] > maxK) {
                startPosition = i;
                minPosition = -1;
                maxPosition = -1;
            }
            if (nums[i] == minK) minPosition = i;
            if (nums[i] == maxK) maxPosition = i;
            
            if (minPosition != -1 && maxPosition != -1) {
                count += Math.max(0, Math.min(minPosition, maxPosition) - startPosition);
            }
        }
        return count;
    }
}
