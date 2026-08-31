class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int first = -1;
        int prev = -1;

        int minDist = Integer.MAX_VALUE;
        int maxDist = -1;

        int index = 1;

        ListNode left = head;
        ListNode curr = head.next;

        while (curr.next != null) {

            ListNode right = curr.next;

            // Check if curr is a critical point
            boolean isCritical =
                (curr.val > left.val && curr.val > right.val) ||
                (curr.val < left.val && curr.val < right.val);

            if (isCritical) {

                if (first == -1) {
                    // First critical point
                    first = index;
                } else {
                    // Distance from previous critical point
                    minDist = Math.min(minDist, index - prev);

                    // Distance from first critical point
                    maxDist = index - first;
                }

                prev = index;
            }

            left = curr;
            curr = curr.next;
            index++;
        }

        if (first == -1 || first == prev) {
            return new int[]{-1, -1};
        }

        return new int[]{minDist, maxDist};
    }
}