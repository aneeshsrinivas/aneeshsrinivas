import java.util.*;

class Solution {
    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] result = new int[n - k + 1];

        for (int i = 0; i <= n - k; i++) {
            int[] window = Arrays.copyOfRange(nums, i, i + k);
            Map<Integer, Integer> freq = new HashMap<>();

            for (int num : window) {
                freq.put(num, freq.getOrDefault(num, 0) + 1);
            }

            List<Integer> sorted = new ArrayList<>(freq.keySet());
            sorted.sort((a, b) -> {
                int fa = freq.get(a);
                int fb = freq.get(b);
                if (fa != fb) return fb - fa;
                return b - a;
            });

            Set<Integer> topX = new HashSet<>();
            for (int j = 0; j < Math.min(x, sorted.size()); j++) {
                topX.add(sorted.get(j));
            }

            int sum = 0;
            for (int num : window) {
                if (topX.contains(num)) {
                    sum += num;
                }
            }

            result[i] = sum;
        }

        return result;
    }
}
