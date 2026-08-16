class Solution {
    public boolean stoneGameIX(int[] stones) {
        int c0 = 0;
        int c1 = 0;
        int c2 = 0;

        for (int x : stones) {
            int r = x % 3;

            if (r == 0) {
                c0++;
            } else if (r == 1) {
                c1++;
            } else {
                c2++;
            }
        }

        // Even number of 0-mod-3 stones:
        // Alice wins iff both residue classes 1 and 2 exist.
        if (c0 % 2 == 0) {
            return c1 > 0 && c2 > 0;
        }

        // Odd number of 0-mod-3 stones:
        // Alice wins iff the counts differ by at least 3.
        return Math.abs(c1 - c2) >= 3;
    }
}