class Solution {
    public boolean areSimilar(int[][] mat, int k) {
        for(int i=0;i<mat.length;i++){
            int v=k%mat[i].length;
            if(i%2==0)
                if(!check(mat[i],v,i))
                    return false;
            if(i%2!=0)
                if(!check(mat[i],v,i))
                    return false;
        }
        return true;
    }
    public static boolean check(int[] arr,int k,int row){
        int idx;
        for(int i=0;i<arr.length;i++){
            if(row%2==0)
                idx=(i+k)%arr.length;
            else
                idx = (i - k + arr.length) % arr.length;   
            if(arr[i]!=arr[idx])
                return false;
        }
        return true;
    }
}