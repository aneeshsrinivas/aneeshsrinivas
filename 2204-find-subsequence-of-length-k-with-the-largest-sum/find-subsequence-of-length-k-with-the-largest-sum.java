import java.util.*;

class Solution {
    public int[] maxSubsequence(int[] nums, int k) {
        int n = nums.length;
        // Pair each number with its index
        int[][] indexedNums = new int[n][2];
        for (int i = 0; i < n; i++) {
            indexedNums[i][0] = nums[i];
            indexedNums[i][1] = i;
        }

        // Sort by value descending
        Arrays.sort(indexedNums, (a, b) -> Integer.compare(b[0], a[0]));

        // Pick top k elements
        int[][] topK = Arrays.copyOfRange(indexedNums, 0, k);

        // Sort selected elements by original index
        Arrays.sort(topK, Comparator.comparingInt(a -> a[1]));

        // Extract the values
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = topK[i][0];
        }

        return result;
    }
}
