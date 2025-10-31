import java.util.*;

class Solution {
    public int[] getSneakyNumbers(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        int[] result = new int[2];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 2) {
                result[index++] = entry.getKey();
                if (index == 2) break;
            }
        }

        return result;
    }
}
