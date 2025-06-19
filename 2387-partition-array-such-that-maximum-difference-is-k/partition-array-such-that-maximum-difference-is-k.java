import java.util.Arrays;

class Solution {
    public int partitionArray(int[] nums, int k) {
        Arrays.sort(nums); // Step 1: Sort the array
        int subsequences = 0;
        int i = 0;

        while (i < nums.length) {
            int minValue = nums[i];  // Start a new subsequence
            while (i < nums.length && nums[i] - minValue <= k) {
                i++;  // Extend subsequence while condition holds
            }
            subsequences++;  // A subsequence is formed
        }

        return subsequences;
    }
}
