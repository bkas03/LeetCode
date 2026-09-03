
class Solution {
    public boolean uniformArray(int[] nums1) {
        int minOdd = Integer.MAX_VALUE;
        int minEven = Integer.MAX_VALUE;

        for (int x : nums1) {
            if ((x & 1) == 0) {
                minEven = Math.min(minEven, x);
            } else {
                minOdd = Math.min(minOdd, x);
            }
        }

        // All numbers are even
        if (minOdd == Integer.MAX_VALUE) {
            return true;
        }

        // All numbers are odd
        if (minEven == Integer.MAX_VALUE) {
            return true;
        }

        // Make every element odd:
        // every even number must be greater than the smallest odd number.
        return minOdd < minEven;
    }
}
