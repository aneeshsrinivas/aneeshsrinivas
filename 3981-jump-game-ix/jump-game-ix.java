class Solution {
    public int[] maxValue(int[] nums) {
        int n=nums.length;
        Stack<Integer> st=new Stack();
        UnionFind uf = new UnionFind(n);
        for(int i=0;i<n;i++){
            int prev = i;
            if(!st.isEmpty()) prev=st.peek();
            while(!st.isEmpty() && nums[i]<nums[st.peek()]){
                uf.union(st.pop(), i);
            }
            if(nums[i]>nums[prev]) st.push(i);
            else
                st.push(prev);
        }
        st.clear();
        for(int i=n-1;i>=0;i--){
            int prev=i;
            if(!st.isEmpty()) prev=st.peek();
            while(!st.isEmpty() && nums[i]>nums[st.peek()]){
                uf.union(st.pop(), i);
            }
            // st.push(i);
            if(nums[i]<nums[prev]) st.push(i);
            else
                st.push(prev);
        }
        HashMap<Integer, Integer> map=new HashMap<>();
        for(int i=0;i<n;i++){
            int root=uf.find(i);
            map.put(root, Math.max(map.getOrDefault(root, Integer.MIN_VALUE), nums[i]));
        }
        int[] ans=new int[n];
        for(int i=0;i<n;i++){
            // ans[i]=nums[uf.maxi[uf.find(i)]];
            ans[i] = map.get(uf.find(i));
        }
        return ans;
    }
    class UnionFind{
        int[] par, rank, maxi;
        UnionFind(int n){
            par=new int[n];
            rank=new int[n];
            // maxi=new int[n];
            // for(int i=0;i<n;i++) maxi[i]=i;
            for(int i=0;i<n;i++) par[i]=i;
        }
        int find(int x) {
            if(par[x]!=x) par[x]=find(par[x]);
            return par[x];
        }
        void union(int x, int y){
            x=find(x);
            y=find(y);
            // maxi[x]=Math.max(x, y);
            // maxi[y]=Math.max(x, y);
            if(x==y) return;
            if(rank[x]<rank[y]) par[x]=y;
            else if(rank[x]>rank[y]) par[y]=x;
            else{
                par[y]=x;
                rank[x]++;
            }
        }
    }
}