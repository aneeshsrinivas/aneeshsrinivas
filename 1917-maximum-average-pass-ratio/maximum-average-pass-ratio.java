import java.util.PriorityQueue;

class Solution {
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        // Max heap based on the gain in pass ratio by adding one student
        PriorityQueue<double[]> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b[0], a[0]));

        // Initialize heap with gain, pass, total for each class
        for (int[] c : classes) {
            int pass = c[0], total = c[1];
            double gain = gain(pass, total);
            maxHeap.offer(new double[]{gain, pass, total});
        }

        // Assign each extra student to the class with the highest gain
        while (extraStudents-- > 0) {
            double[] top = maxHeap.poll();
            int pass = (int) top[1], total = (int) top[2];
            pass++; total++;
            double newGain = gain(pass, total);
            maxHeap.offer(new double[]{newGain, pass, total});
        }

        // Calculate final average pass ratio
        double totalRatio = 0;
        while (!maxHeap.isEmpty()) {
            double[] entry = maxHeap.poll();
            int pass = (int) entry[1], total = (int) entry[2];
            totalRatio += (double) pass / total;
        }

        return totalRatio / classes.length;
    }

    // Helper to compute gain in pass ratio by adding one student
    private double gain(int pass, int total) {
        return ((double)(pass + 1) / (total + 1)) - ((double) pass / total);
    }
}
