import java.util.Stack;

class Solution {
    public String robotWithString(String s) {
        int n = s.length();
        char[] minSuffix = new char[n]; // Track smallest char in s from i to end
        Stack<Character> t = new Stack<>();
        StringBuilder result = new StringBuilder();

        // Fill the minSuffix array (smallest character from i to the end)
        minSuffix[n - 1] = s.charAt(n - 1);
        for (int i = n - 2; i >= 0; i--) {
            minSuffix[i] = (char) Math.min(s.charAt(i), minSuffix[i + 1]);
        }

        int index = 0;
        for (char min : minSuffix) {
            while (!t.isEmpty() && t.peek() <= min) {
                result.append(t.pop()); // Write lexicographically smallest character
            }
            t.push(s.charAt(index++));
        }

        // Write remaining characters from stack
        while (!t.isEmpty()) {
            result.append(t.pop());
        }

        return result.toString();
    }
}
