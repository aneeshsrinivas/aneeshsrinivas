class Solution {
    public long flowerGame(int n, int m) {
        long oddX = n / 2 + (n % 2);
        long evenX = n - oddX;
        long oddY = m / 2 + (m % 2);
        long evenY = m - oddY;
        
        return oddX * evenY + evenX * oddY;
    }
}
