import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        Map<Integer, Integer> map = new HashMap<>();

        // Store reserved seats as a bitmask for each affected row
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            map.put(row, map.getOrDefault(row, 0) | (1 << col));
        }

        // Every row without reservations can accommodate 2 families
        int ans = (n - map.size()) * 2;

        // Masks for the three possible groups
        int left  = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);
        int middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7);
        int right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9);

        for (int mask : map.values()) {

            boolean canLeft = (mask & left) == 0;
            boolean canRight = (mask & right) == 0;

            if (canLeft && canRight) {
                // Both groups can fit
                ans += 2;
            } 
            else if (canLeft || canRight) {
                // One side can fit
                ans += 1;
            } 
            else if ((mask & middle) == 0) {
                // Only middle group can fit
                ans += 1;
            }
        }

        return ans;
    }
}