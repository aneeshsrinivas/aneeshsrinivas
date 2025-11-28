class Solution {
    int ans, k;
    List<Integer>[] g;

    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        this.k = k;
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
        for (int[] e : edges) {
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        dfs(0, -1, values);
        return ans;
    }

    long dfs(int u, int p, int[] values) {
        long sum = values[u];
        for (int v : g[u]) {
            if (v != p) sum += dfs(v, u, values);
        }
        if (sum % k == 0) ans++;
        return sum % k;
    }
}
