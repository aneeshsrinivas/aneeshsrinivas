class Solution {
    public int numOfSubarrays(int[] arr) {
        int n = arr.length;
        int cntEv = 0;
        int cntOdd = 0;
        int pre = 0; int mod = (int)1e9 + 7;
        int ans = 0;
        for(int i = 0; i < n;i++){
            pre += arr[i];

            if((pre&1) == 0){ //so we need to remove subarrays(0...i) having sum as odd to make the resulting subarray odd
                ans = (ans + cntOdd) % mod;
                cntEv++;
            }else{
                ans = (ans + cntEv) % mod;
                ans++;
                cntOdd++;
            }
        }
        return ans;
    }
}