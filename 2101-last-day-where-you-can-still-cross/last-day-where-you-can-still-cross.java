class Solution {
    public int latestDayToCross(int row, int col, int[][] cells) {
        int left = 1, right = cells.length, ans = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canCross(row, col, cells, mid)) {
                ans = mid;      // crossing possible → try later days
                left = mid + 1;
            } else {
                right = mid - 1; // crossing not possible → try earlier days
            }
        }
        
        return ans;
    }
    
    private boolean canCross(int row, int col, int[][] cells, int day) {
        int[][] grid = new int[row][col];
        
        // Mark flooded cells up to 'day'
        for (int i = 0; i < day; i++) {
            int r = cells[i][0] - 1;
            int c = cells[i][1] - 1;
            grid[r][c] = 1; // water
        }
        
        // BFS from top row
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[row][col];
        
        for (int c = 0; c < col; c++) {
            if (grid[0][c] == 0) {
                q.offer(new int[]{0, c});
                visited[0][c] = true;
            }
        }
        
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];
            
            if (r == row - 1) return true; // reached bottom
            
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < row && nc >= 0 && nc < col &&
                    !visited[nr][nc] && grid[nr][nc] == 0) {
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        
        return false;
    }
}