class Solution {
    public long distributeCandies(int n, int limit) {
        long ways = 0;
        
        for (int i = 0; i <= Math.min(n, limit); i++) {
            int minJ = Math.max(0, n - i - limit);
            int maxJ = Math.min(limit, n - i);
            ways += Math.max(maxJ - minJ + 1, 0);
        }
        
        return ways;
    }
}
