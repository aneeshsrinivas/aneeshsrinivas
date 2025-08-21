class Solution {
    public int numSubmat(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[][] dp = new int[m][n];
        int count = 0;

        // Step 1: Preprocess each row to count consecutive ones ending at (i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1) {
                    dp[i][j] = (j == 0 ? 1 : dp[i][j - 1] + 1);
                }
            }
        }

        // Step 2: For each cell, look upward and count valid submatrices
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                int minWidth = dp[i][j];
                for (int k = i; k >= 0 && minWidth > 0; k--) {
                    minWidth = Math.min(minWidth, dp[k][j]);
                    count += minWidth;
                }
            }
        }

        return count;
    }
}
