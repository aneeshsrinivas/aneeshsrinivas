class Solution {
    public int maximumGain(String s, int x, int y) {
        // Prioritize the more valuable pair
        if (y > x) {
            return maxScore(s, 'b', 'a', y, x);
        } else {
            return maxScore(s, 'a', 'b', x, y);
        }
    }

    private int maxScore(String s, char first, char second, int firstScore, int secondScore) {
        Stack<Character> stack = new Stack<>();
        int score = 0;

        // First pass: remove most valuable pairs
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peek() == first && c == second) {
                stack.pop();
                score += firstScore;
            } else {
                stack.push(c);
            }
        }

        // Reconstruct remaining string after first pass
        StringBuilder remaining = new StringBuilder();
        while (!stack.isEmpty()) {
            remaining.append(stack.pop());
        }

        // Second pass: remove the other pairs
        stack.clear();
        for (int i = remaining.length() - 1; i >= 0; i--) {
            char c = remaining.charAt(i);
            if (!stack.isEmpty() && stack.peek() == second && c == first) {
                stack.pop();
                score += secondScore;
            } else {
                stack.push(c);
            }
        }

        return score;
    }
}
