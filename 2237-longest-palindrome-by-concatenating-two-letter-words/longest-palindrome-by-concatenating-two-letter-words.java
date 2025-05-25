import java.util.HashMap;
import java.util.Map;

class Solution {
    public int longestPalindrome(String[] words) {
        Map<String, Integer> countMap = new HashMap<>();
        int palindromeLength = 0;
        boolean hasMiddle = false;

        // Count occurrences of each word
        for (String word : words) {
            countMap.put(word, countMap.getOrDefault(word, 0) + 1);
        }

        for (String word : countMap.keySet()) {
            String reversed = new StringBuilder(word).reverse().toString();

            if (word.equals(reversed)) { // Words like "xx", "ll"
                int count = countMap.get(word);
                if (count % 2 == 0) {
                    palindromeLength += count * 2;
                } else {
                    palindromeLength += (count - 1) * 2;
                    hasMiddle = true;
                }
            } else if (countMap.containsKey(reversed)) { // Reverse pairs like "lb" & "bl"
                int pairs = Math.min(countMap.get(word), countMap.get(reversed));
                palindromeLength += pairs * 4;
                countMap.put(word, countMap.get(word) - pairs);
                countMap.put(reversed, countMap.get(reversed) - pairs);
            }
        }

        return hasMiddle ? palindromeLength + 2 : palindromeLength;
    }
}
