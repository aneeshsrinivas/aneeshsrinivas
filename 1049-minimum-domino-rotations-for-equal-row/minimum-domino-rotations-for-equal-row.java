class Solution {
    public int minDominoRotations(int[] tops, int[] bottoms) {
        int rotations = check(tops[0], tops, bottoms);
        if (rotations != -1 || tops[0] == bottoms[0]) {
            return rotations;
        }
        return check(bottoms[0], tops, bottoms);
    }
    
    private int check(int target, int[] tops, int[] bottoms) {
        int topRotations = 0, bottomRotations = 0;

        for (int i = 0; i < tops.length; i++) {
            if (tops[i] != target && bottoms[i] != target) {
                return -1;  // If neither side has target value, it's impossible
            } else if (tops[i] != target) {
                topRotations++;  // Swap needed in top row
            } else if (bottoms[i] != target) {
                bottomRotations++;  // Swap needed in bottom row
            }
        }
        return Math.min(topRotations, bottomRotations);
    }
}
