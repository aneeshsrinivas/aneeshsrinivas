class Solution {
    public String triangleType(int[] nums) {
        // Sort the array to simplify the triangle inequality check
        Arrays.sort(nums);
        
        // Check if the sides satisfy the triangle inequality theorem
        if (nums[0] + nums[1] <= nums[2]) {
            return "none";
        }
        
        // Check for the type of triangle
        if (nums[0] == nums[1] && nums[1] == nums[2]) {
            return "equilateral";
        } else if (nums[0] == nums[1] || nums[1] == nums[2]) {
            return "isosceles";
        } else {
            return "scalene";
        }
    }
}
