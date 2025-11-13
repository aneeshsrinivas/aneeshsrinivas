class Solution {
    public int maxOperations(String s) {
        int operations = 0;
        int ones = 0;
        int n = s.length();
        
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                ones++;
            } else if (i > 0 && s.charAt(i - 1) == '1') {
                operations += ones;
            }
        }
        
        return operations;
    }
}