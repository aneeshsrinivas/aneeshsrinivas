class Solution {
    public double soupServings(int n) {
        if (n >= 4800) return 1.0; // Optimization for large n

        int N = (n + 24) / 25; // Convert to units of 25 mL
        Double[][] memo = new Double[N + 1][N + 1];

        return helper(N, N, memo);
    }

    private double helper(int a, int b, Double[][] memo) {
        if (a <= 0 && b <= 0) return 0.5;
        if (a <= 0) return 1.0;
        if (b <= 0) return 0.0;
        if (memo[a][b] != null) return memo[a][b];

        memo[a][b] = 0.25 * (
            helper(Math.max(a - 4, 0), b, memo) +
            helper(Math.max(a - 3, 0), Math.max(b - 1, 0), memo) +
            helper(Math.max(a - 2, 0), Math.max(b - 2, 0), memo) +
            helper(Math.max(a - 1, 0), Math.max(b - 3, 0), memo)
        );

        return memo[a][b];
    }
}
