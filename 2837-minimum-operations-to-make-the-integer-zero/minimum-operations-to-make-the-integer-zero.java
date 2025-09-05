class Solution {
    public int makeTheIntegerZero(int num1, int num2) {
        for (int k = 1; k <= 60; k++) {
            long num = (long) num1 - (long) k * num2;
            if (num < k) continue; // Not enough value to split into k powers of 2
            if (Long.bitCount(num) <= k) return k;
        }
        return -1;
    }
}
