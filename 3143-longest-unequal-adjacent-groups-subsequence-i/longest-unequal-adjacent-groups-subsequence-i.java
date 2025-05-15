import java.util.*;

class Solution {
    public List<String> getLongestSubsequence(String[] words, int[] groups) {
        List<String> result = new ArrayList<>();
        
        // Start with the first element
        result.add(words[0]);
        int prevGroup = groups[0];
        
        // Iterate through the array
        for (int i = 1; i < words.length; i++) {
            if (groups[i] != prevGroup) {
                result.add(words[i]);
                prevGroup = groups[i]; // Update previous group
            }
        }
        
        return result;
    }
}
