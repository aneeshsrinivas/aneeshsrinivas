import java.util.HashMap;

class Solution {
    public int countCompleteSubarrays(int[] nums) {
        int n = nums.length;
        int totalDistinct = getDistinctCount(nums); // Get total distinct elements in the array
        int count = 0;

        // Sliding window approach
        for (int i = 0; i < n; i++) {
            HashMap<Integer, Integer> frequencyMap = new HashMap<>();
            for (int j = i; j < n; j++) {
                frequencyMap.put(nums[j], frequencyMap.getOrDefault(nums[j], 0) + 1);
                if (frequencyMap.size() == totalDistinct) { // If distinct count equals total distinct
                    count++;
                }
            }
        }
        return count;
    }

    private int getDistinctCount(int[] nums) {
        HashMap<Integer, Boolean> distinctMap = new HashMap<>();
        for (int num : nums) {
            distinctMap.put(num, true);
        }
        return distinctMap.size();
    }
}
