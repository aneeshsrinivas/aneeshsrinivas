class Solution {
    private int maxOR = 0;
    private int count = 0;

    public int countMaxOrSubsets(int[] nums) {
        // First compute the maximum OR possible
        for (int num : nums) {
            maxOR |= num;
        }

        // Use DFS to explore all subsets
        dfs(nums, 0, 0);
        return count;
    }

    private void dfs(int[] nums, int index, int currOR) {
        if (index == nums.length) {
            if (currOR == maxOR) {
                count++;
            }
            return;
        }

        // Include nums[index]
        dfs(nums, index + 1, currOR | nums[index]);
        
        // Exclude nums[index]
        dfs(nums, index + 1, currOR);
    }
}
