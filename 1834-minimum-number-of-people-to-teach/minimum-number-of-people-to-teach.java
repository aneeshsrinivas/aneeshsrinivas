class Solution {
    public int minimumTeachings(int n, int[][] languages, int[][] friendships) {
        int m = languages.length;
        Set<Integer> needTeach = new HashSet<>();
        List<Set<Integer>> langSets = new ArrayList<>();
        for (int[] lang : languages) {
            Set<Integer> set = new HashSet<>();
            for (int l : lang) set.add(l);
            langSets.add(set);
        }

        for (int[] f : friendships) {
            int u = f[0] - 1, v = f[1] - 1;
            boolean canTalk = false;
            for (int l : langSets.get(u)) {
                if (langSets.get(v).contains(l)) {
                    canTalk = true;
                    break;
                }
            }
            if (!canTalk) {
                needTeach.add(u);
                needTeach.add(v);
            }
        }

        int minTeach = Integer.MAX_VALUE;
        for (int i = 1; i <= n; ++i) {
            int count = 0;
            for (int u : needTeach) {
                if (!langSets.get(u).contains(i)) count++;
            }
            minTeach = Math.min(minTeach, count);
        }
        return minTeach;
    }
}
