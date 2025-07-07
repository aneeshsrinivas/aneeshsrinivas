import java.util.*;

class Solution {
    public int maxEvents(int[][] events) {
        // Sort events by start day
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int i = 0, n = events.length;
        int day = 1, maxEvents = 0;

        // Find the last day to consider
        int lastDay = 0;
        for (int[] e : events) lastDay = Math.max(lastDay, e[1]);

        // Iterate through each day
        while (day <= lastDay) {
            // Add events starting today
            while (i < n && events[i][0] == day) {
                minHeap.offer(events[i][1]); // push endDay
                i++;
            }

            // Remove expired events
            while (!minHeap.isEmpty() && minHeap.peek() < day) {
                minHeap.poll();
            }

            // Attend one event today
            if (!minHeap.isEmpty()) {
                minHeap.poll();
                maxEvents++;
            }

            day++;
        }

        return maxEvents;
    }
}

