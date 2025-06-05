class Solution {
    int[] parent = new int[26];

    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        // Initialize each character as its own representative
        for (int i = 0; i < 26; i++) {
            parent[i] = i;
        }

        // Union equivalent characters
        for (int i = 0; i < s1.length(); i++) {
            union(s1.charAt(i) - 'a', s2.charAt(i) - 'a');
        }

        // Convert baseStr using smallest lexicographical representatives
        StringBuilder result = new StringBuilder();
        for (char c : baseStr.toCharArray()) {
            result.append((char) ('a' + find(c - 'a')));
        }

        return result.toString();
    }

    // Find function with path compression
    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }

    // Union function
    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            parent[Math.max(rootX, rootY)] = Math.min(rootX, rootY);  // Keep the smaller as root
        }
    }
}
