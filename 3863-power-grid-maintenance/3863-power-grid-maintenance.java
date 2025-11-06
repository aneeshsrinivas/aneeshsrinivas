class Solution {
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        List<Integer>[] graph = new ArrayList[c + 1];
        for (int i = 1; i <= c; i++) graph[i] = new ArrayList<>();
        for (int[] conn : connections) {
            graph[conn[0]].add(conn[1]);
            graph[conn[1]].add(conn[0]);
        }

        int[] comp = new int[c + 1];
        int compId = 1;
        boolean[] visited = new boolean[c + 1];
        for (int i = 1; i <= c; i++) {
            if (!visited[i]) {
                dfs(i, compId++, graph, visited, comp);
            }
        }

        TreeSet<Integer>[] online = new TreeSet[compId];
        for (int i = 0; i < compId; i++) online[i] = new TreeSet<>();
        for (int i = 1; i <= c; i++) online[comp[i]].add(i);

        List<Integer> res = new ArrayList<>();
        for (int[] q : queries) {
            int type = q[0], x = q[1];
            int cid = comp[x];
            if (type == 1) {
                if (online[cid].contains(x)) res.add(x);
                else if (!online[cid].isEmpty()) res.add(online[cid].first());
                else res.add(-1);
            } else {
                online[cid].remove(x);
            }
        }

        int[] ans = new int[res.size()];
        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }

    private void dfs(int node, int cid, List<Integer>[] graph, boolean[] visited, int[] comp) {
        visited[node] = true;
        comp[node] = cid;
        for (int nei : graph[node]) {
            if (!visited[nei]) dfs(nei, cid, graph, visited, comp);
        }
    }
}
