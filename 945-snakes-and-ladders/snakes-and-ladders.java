import java.util.*;

class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int[] flattened = new int[n * n + 1];

        // Convert the 2D board into a 1D representation
        int index = 1;
        boolean leftToRight = true;
        for (int i = n - 1; i >= 0; i--) {
            if (leftToRight) {
                for (int j = 0; j < n; j++) {
                    flattened[index++] = board[i][j];
                }
            } else {
                for (int j = n - 1; j >= 0; j--) {
                    flattened[index++] = board[i][j];
                }
            }
            leftToRight = !leftToRight;
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{1, 0}); // {square, moves}
        Set<Integer> visited = new HashSet<>();
        visited.add(1);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int square = current[0], moves = current[1];

            if (square == n * n) return moves; // Reached the last square

            for (int nextSquare = square + 1; nextSquare <= Math.min(square + 6, n * n); nextSquare++) {
                int target = flattened[nextSquare] != -1 ? flattened[nextSquare] : nextSquare;
                
                if (!visited.contains(target)) {
                    visited.add(target);
                    queue.offer(new int[]{target, moves + 1});
                }
            }
        }
        return -1;
    }
}
