class Solution {
    public char findKthBit(int n, int k) {
        boolean flip = false;  // whether we have inverted odd number of times
        
        while (n > 1) {
            int mid = 1 << (n - 1);      // 2^(n-1)
            int len = (1 << n) - 1;      // 2^n - 1
            
            if (k == mid) {
                // middle is always '1', but may be flipped
                return flip ? '0' : '1';
            }
            
            if (k > mid) {
                // mirror position and toggle flip
                k = len - k + 1;
                flip = !flip;
            }
            
            // move to S(n-1)
            n--;
        }
        
        // base case: S1 = "0"
        return flip ? '1' : '0';
    }
}