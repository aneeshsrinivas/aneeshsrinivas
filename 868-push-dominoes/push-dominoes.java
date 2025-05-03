class Solution {
    public String pushDominoes(String dominoes) {
        char[] arr = dominoes.toCharArray();
        int n = arr.length;
        int[] forces = new int[n];

        // **Left-to-Right pass** (Apply 'R' forces)
        int force = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 'R') force = n;  // Assign maximum force
            else if (arr[i] == 'L') force = 0;  // Stop force
            else force = Math.max(force - 1, 0);
            
            forces[i] += force;
        }

        // **Right-to-Left pass** (Apply 'L' forces)
        force = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] == 'L') force = n;  // Assign maximum force
            else if (arr[i] == 'R') force = 0;  // Stop force
            else force = Math.max(force - 1, 0);
            
            forces[i] -= force;
        }

        // **Construct final result**
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (forces[i] > 0) result.append('R');
            else if (forces[i] < 0) result.append('L');
            else result.append('.');
        }

        return result.toString();
    }
}
