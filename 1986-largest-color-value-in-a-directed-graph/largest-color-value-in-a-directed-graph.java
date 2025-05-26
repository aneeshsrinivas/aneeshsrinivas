import java.util.*;

class Solution {
    public int largestPathValue(String colors, int[][] edges) {
        int n = colors.length();
        List<Integer>[] graph = new ArrayList[n];
        int[] inDegree = new int[n];
        
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            inDegree[edge[1]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        int[][] dp = new int[n][26]; // Track frequency of colors at each node
        int processedNodes = 0;
        int maxColorValue = 0;
        
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
                dp[i][colors.charAt(i) - 'a'] = 1;
            }
        }
        
        while (!queue.isEmpty()) {
            int node = queue.poll();
            processedNodes++;
            maxColorValue = Math.max(maxColorValue, Arrays.stream(dp[node]).max().getAsInt());
            
            for (int neighbor : graph[node]) {
                for (int c = 0; c < 26; c++) {
                    dp[neighbor][c] = Math.max(dp[neighbor][c], dp[node][c] + (colors.charAt(neighbor) - 'a' == c ? 1 : 0));
                }
                
                if (--inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        return processedNodes == n ? maxColorValue : -1; // Check for cycles
    }
}
