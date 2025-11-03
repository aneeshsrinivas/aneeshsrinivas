class Solution {
    public int minCost(String colors, int[] neededTime) {
        int totalTime = 0;
        int n = colors.length();  // ✅ Use length() for String

        int i = 0;
        while (i < n) {
            char currentColor = colors.charAt(i);
            int maxTime = neededTime[i];
            int sumTime = neededTime[i];
            int j = i + 1;

            while (j < n && colors.charAt(j) == currentColor) {
                sumTime += neededTime[j];
                maxTime = Math.max(maxTime, neededTime[j]);
                j++;
            }

            totalTime += (sumTime - maxTime);
            i = j;
        }

        return totalTime;
    }
}
