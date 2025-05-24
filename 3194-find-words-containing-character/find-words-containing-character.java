import java.util.*;

class Solution {
    public List<Integer> findWordsContaining(String[] words, char x) {
        List<Integer> result = new ArrayList<>();
        
        for (int i = 0; i < words.length; i++) {
            if (words[i].indexOf(x) != -1) {
                result.add(i);
            }
        }
        
        return result;
    }
}

// Example Usage
class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        String[] words1 = {"leet","code"};
        char x1 = 'e';
        System.out.println(sol.findWordsContaining(words1, x1));  // Output: [0,1]

        String[] words2 = {"abc","bcd","aaaa","cbc"};
        char x2 = 'a';
        System.out.println(sol.findWordsContaining(words2, x2));  // Output: [0,2]

        String[] words3 = {"abc","bcd","aaaa","cbc"};
        char x3 = 'z';
        System.out.println(sol.findWordsContaining(words3, x3));  // Output: []
    }
}
