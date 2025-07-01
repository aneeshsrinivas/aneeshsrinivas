class Solution {
    public int possibleStringCount(String word) {
        int totalVariants = 0;
        int i = 0;
        
        while (i < word.length()) {
            int j = i;
            // Find the end of the current group
            while (j + 1 < word.length() && word.charAt(j + 1) == word.charAt(i)) {
                j++;
            }
            int groupLength = j - i + 1;
            if (groupLength > 1) {
                totalVariants += groupLength - 1;
            }
            i = j + 1;
        }
        
        return totalVariants + 1; // +1 for the original string itself
    }
}
