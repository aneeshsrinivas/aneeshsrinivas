import java.util.*;

class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> toRemove = new HashSet<>();
        for (int num : nums) {
            toRemove.add(num);
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = dummy;

        while (current.next != null) {
            if (toRemove.contains(current.next.val)) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }

        return dummy.next;
    }
}
