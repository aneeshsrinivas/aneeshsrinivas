import java.util.*;

class Solution {
    public long maxRunTime(int n, int[] batteries) {
        long sum = 0;
        for (int b : batteries) sum += b;
        long left = 1, right = sum / n, ans = 0;
        while (left <= right) {
            long mid = (left + right) / 2;
            if (canRun(batteries, n, mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    private boolean canRun(int[] batteries, int n, long time) {
        long total = 0;
        for (int b : batteries) total += Math.min(b, time);
        return total >= time * n;
    }
}
