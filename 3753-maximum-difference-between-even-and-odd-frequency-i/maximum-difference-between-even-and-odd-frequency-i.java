import java.util.HashMap;

class Solution {
    public int maxDifference(String s) {
        HashMap<Character, Integer> freqMap = new HashMap<>();
        
        // Count frequencies
        for (char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        int oddMax = Integer.MIN_VALUE;
        int evenMin = Integer.MAX_VALUE;

        // Identify max odd frequency and min even frequency
        for (int freq : freqMap.values()) {
            if (freq % 2 == 1) { // Odd frequency
                oddMax = Math.max(oddMax, freq);
            } else { // Even frequency
                evenMin = Math.min(evenMin, freq);
            }
        }

        return oddMax - evenMin;
    }
}
