class Solution {
    public boolean judgePoint24(int[] cards) {
        List<Double> nums = new ArrayList<>();
        for (int card : cards) {
            nums.add((double) card);
        }
        return backtrack(nums);
    }

    private boolean backtrack(List<Double> nums) {
        if (nums.size() == 1) {
            return Math.abs(nums.get(0) - 24) < 1e-6;
        }

        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.size(); j++) {
                if (i == j) continue;

                List<Double> nextRound = new ArrayList<>();
                for (int k = 0; k < nums.size(); k++) {
                    if (k != i && k != j) {
                        nextRound.add(nums.get(k));
                    }
                }

                for (double result : compute(nums.get(i), nums.get(j))) {
                    nextRound.add(result);
                    if (backtrack(nextRound)) return true;
                    nextRound.remove(nextRound.size() - 1); // backtrack
                }
            }
        }
        return false;
    }

    private List<Double> compute(double a, double b) {
        List<Double> results = new ArrayList<>();
        results.add(a + b);
        results.add(a - b);
        results.add(b - a);
        results.add(a * b);
        if (Math.abs(b) > 1e-6) results.add(a / b);
        if (Math.abs(a) > 1e-6) results.add(b / a);
        return results;
    }
}
