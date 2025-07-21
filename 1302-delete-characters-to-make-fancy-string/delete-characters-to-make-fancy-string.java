class Solution {
    public String makeFancyString(String s) {
        StringBuilder result = new StringBuilder();
        int count = 1; // Keeps track of consecutive character count

        result.append(s.charAt(0)); // Always add the first character

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                count = 1;
            }

            // Only add the character if there are fewer than 3 consecutive matches
            if (count < 3) {
                result.append(s.charAt(i));
            }
        }

        return result.toString();
    }
}
