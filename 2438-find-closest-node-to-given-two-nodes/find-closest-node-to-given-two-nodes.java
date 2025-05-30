import java.util.Arrays;

class Solution {
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int n = edges.length;
        int[] distance1 = new int[n];
        int[] distance2 = new int[n];
        
        Arrays.fill(distance1, Integer.MAX_VALUE);
        Arrays.fill(distance2, Integer.MAX_VALUE);
        
        computeDistances(edges, node1, distance1);
        computeDistances(edges, node2, distance2);
        
        int minMaxDistance = Integer.MAX_VALUE;
        int resultNode = -1;
        
        for (int i = 0; i < n; i++) {
            int maxDistance = Math.max(distance1[i], distance2[i]);
            if (maxDistance < minMaxDistance) {
                minMaxDistance = maxDistance;
                resultNode = i;
            }
        }
        
        return resultNode;
    }
    
    private void computeDistances(int[] edges, int start, int[] distance) {
        int current = start, dist = 0;
        while (current != -1 && distance[current] == Integer.MAX_VALUE) {
            distance[current] = dist++;
            current = edges[current];
        }
    }
}
