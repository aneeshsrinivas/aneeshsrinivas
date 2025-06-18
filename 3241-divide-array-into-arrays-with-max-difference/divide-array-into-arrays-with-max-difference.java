import java.util.Arrays;

class Solution {
    public int[][] divideArray(int[] nums, int k) {
        Arrays.sort(nums); // Step 1: Sort the array
        int n = nums.length;
        int[][] result = new int[n / 3][3];
        
        for (int i = 0; i < n; i += 3) {
            if (nums[i + 2] - nums[i] > k) { // Step 3: Validate difference
                return new int[0][0]; // Return empty array if condition fails
            }
            result[i / 3] = new int[]{nums[i], nums[i + 1], nums[i + 2]};
        }
        
        return result; // Return valid groups
    }
}
