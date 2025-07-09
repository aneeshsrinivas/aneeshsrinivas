class Solution {
    public int maxFreeTime(int eventTime, int k, int[] startTime, int[] endTime) {
        int n = startTime.length;

        // Step 1: collect all gaps between meetings and at boundaries
        int[] gaps = new int[n + 1];
        gaps[0] = startTime[0];  // free time before first meeting
        for (int i = 0; i < n - 1; i++) {
            gaps[i + 1] = startTime[i + 1] - endTime[i];  // between meetings
        }
        gaps[n] = eventTime - endTime[n - 1];  // after last meeting

        // Step 2: apply sliding window of size k+1 to gaps
        int maxFree = 0, window = 0;
        for (int i = 0; i < gaps.length; i++) {
            window += gaps[i];
            if (i >= k + 1) window -= gaps[i - (k + 1)];
            if (i >= k) maxFree = Math.max(maxFree, window);
        }

        return maxFree;
    }
}
