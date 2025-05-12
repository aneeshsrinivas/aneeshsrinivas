import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

class Solution {
    public int[] findEvenNumbers(int[] digits) {
        Set<Integer> result = new HashSet<>();
        int n = digits.length;

        // Iterate through all possible 3-digit combinations
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    // Ensure no repeating indices
                    if (i != j && j != k && i != k) {
                        int d1 = digits[i], d2 = digits[j], d3 = digits[k];

                        // Check for valid number
                        if (d1 != 0 && d3 % 2 == 0) { // No leading zero & last digit must be even
                            int num = d1 * 100 + d2 * 10 + d3;
                            result.add(num);
                        }
                    }
                }
            }
        }

        // Convert Set to sorted array
        int[] sortedResult = result.stream().mapToInt(Integer::intValue).sorted().toArray();
        return sortedResult;
    }
}
