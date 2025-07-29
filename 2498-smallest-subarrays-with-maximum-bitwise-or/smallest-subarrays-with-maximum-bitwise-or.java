class Solution {
    public int[] smallestSubarrays(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];
        int[] bitIndex = new int[32]; // stores the last position of each bit
        
        for (int i = n - 1; i >= 0; i--) {
            // Update bitIndex for each bit in nums[i]
            for (int b = 0; b < 32; b++) {
                if ((nums[i] & (1 << b)) != 0) {
                    bitIndex[b] = i;
                }
            }

            // Find the furthest index where any of the bits are set
            int maxPos = i;
            for (int b = 0; b < 32; b++) {
                maxPos = Math.max(maxPos, bitIndex[b]);
            }
            
            answer[i] = maxPos - i + 1;
        }

        return answer;
    }
}
