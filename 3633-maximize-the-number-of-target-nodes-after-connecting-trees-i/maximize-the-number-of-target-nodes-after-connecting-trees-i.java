import java.util.*;

class Solution {
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        int n = edges1.length + 1;
        int m = edges2.length + 1;
        
        List<List<Integer>> tree1 = buildGraph(edges1, n);
        List<List<Integer>> tree2 = buildGraph(edges2, m);

        int[] reachable1 = new int[n]; 
        int[] reachable2 = new int[m]; 

        // Compute reachable nodes within distance k in Tree 1
        for (int i = 0; i < n; i++) {
            reachable1[i] = bfs(tree1, i, k);
        }

        // Compute reachable nodes within distance k-1 in Tree 2
        for (int j = 0; j < m; j++) {
            reachable2[j] = bfs(tree2, j, k - 1);
        }

        int[] answer = new int[n];

        // Find optimal connection for each node in Tree 1
        for (int i = 0; i < n; i++) {
            int maxNodes = 0;
            for (int j = 0; j < m; j++) {
                maxNodes = Math.max(maxNodes, reachable1[i] + reachable2[j]);
            }
            answer[i] = maxNodes;
        }

        return answer;
    }

    // Helper function to build adjacency list representation
    private List<List<Integer>> buildGraph(int[][] edges, int size) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }

    // BFS to calculate reachable nodes within a given distance
    private int bfs(List<List<Integer>> graph, int start, int maxDist) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{start, 0});
        Set<Integer> visited = new HashSet<>();
        visited.add(start);
        
        int count = 0;

        while (!queue.isEmpty()) {
            int[] node = queue.poll();
            int u = node[0], dist = node[1];

            if (dist > maxDist) continue;
            count++;

            for (int neighbor : graph.get(u)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(new int[]{neighbor, dist + 1});
                }
            }
        }
        return count;
    }
}
