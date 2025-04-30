class Solution {
    public int findNumbers(int[] nums) {
        int count = 0; // Variable to store the count of numbers with even digits
        
        for (int num : nums) {
            if (hasEvenDigits(num)) {
                count++;
            }
        }
        
        return count; // Return the final count
    }
    
    // Helper method to check if a number has an even number of digits
    private boolean hasEvenDigits(int num) {
        int digits = 0; // Variable to count the number of digits
        
        while (num > 0) {
            digits++; // Increment digit count
            num /= 10; // Remove the last digit of the number
        }
        
        return digits % 2 == 0; // Return true if the number of digits is even
    }
}
