class Solution {
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        long[][] dis = new long[26][26];
        long INF = (long)1e15;
        for(int i = 0; i<26; i++){
            Arrays.fill(dis[i],INF);
            dis[i][i] = 0;
        }

        for(int i = 0; i<original.length; i++){
            int u = original[i]-'a';
            int v = changed[i]-'a';
            int c = cost[i];
            dis[u][v] = Math.min(dis[u][v],c);
        }
        for(int k = 0; k<26; k++){
            for(int i = 0; i<26; i++){
                for(int j = 0; j<26; j++){
                    dis[i][j] = Math.min(dis[i][j],dis[i][k] + dis[k][j]);
                }
            }
        }

        long ans = 0;
        for(int i = 0; i<source.length(); i++){
            int ch1 = source.charAt(i) -'a';
            int ch2 = target.charAt(i) -'a';
            if(dis[ch1][ch2] == INF){
                return -1;
            }
            ans += dis[ch1][ch2];
        }
        return ans;
    }
}