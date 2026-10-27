class Solution {
    public String[] createGrid(int m, int n) {
        String[] grid = new String[m];
        Arrays.fill(grid, "#".repeat(n - 1) + ".");  // walls, then the last cell open
        grid[0] = ".".repeat(n);  // the first row is all open
        return grid;
    }
}