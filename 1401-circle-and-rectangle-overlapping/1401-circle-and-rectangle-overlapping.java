class Solution {
    public boolean checkOverlap(int radius, int xCenter, int yCenter,
                                int x1, int y1, int x2, int y2) {

        // Closest x-coordinate in the rectangle to the circle center
        int closestX = Math.max(x1, Math.min(xCenter, x2));

        // Closest y-coordinate in the rectangle to the circle center
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        // Distance squared between circle center and closest point
        int dx = closestX - xCenter;
        int dy = closestY - yCenter;

        return dx * dx + dy * dy <= radius * radius;
    }
}