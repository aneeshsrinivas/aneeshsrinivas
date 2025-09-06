class Solution {
    public long minOperations(int[][] queries) {
        long totalOps = 0;

        for (int[] query : queries) {
            int l = query[0], r = query[1];
            long ops = 0;
            long prev = 1;
            int d = 1;

            while (prev <= r) {
                long cur = prev * 4;
                long left = Math.max(prev, l);
                long right = Math.min(cur - 1, r);

                if (right >= left) {
                    ops += (right - left + 1) * d;
                }

                prev = cur;
                d++;
            }

            totalOps += (ops + 1) / 2; 
        }

        return totalOps;
    }
}
