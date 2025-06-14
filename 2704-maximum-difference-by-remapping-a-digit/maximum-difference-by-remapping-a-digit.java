class Solution {
    public int minMaxDifference(int num) {
        String numStr = String.valueOf(num);
        
        // Find the maximum value by replacing the first non-nine digit with 9
        char maxReplace = ' ';
        for (char c : numStr.toCharArray()) {
            if (c != '9') {
                maxReplace = c;
                break;
            }
        }
        String maxStr = maxReplace == ' ' ? numStr : numStr.replace(maxReplace, '9');
        int maxValue = Integer.parseInt(maxStr);
        
        // Find the minimum value by replacing the first non-zero digit with 0
        char minReplace = numStr.charAt(0); // Always replace the first digit
        String minStr = numStr.replace(minReplace, '0');
        int minValue = Integer.parseInt(minStr);
        
        return maxValue - minValue;
    }
}
