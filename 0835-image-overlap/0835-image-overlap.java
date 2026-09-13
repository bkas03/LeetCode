
class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        int ans = 0;

        // Shift img1 by (dr, dc)
        // dr and dc range from -(n-1) to (n-1)
        for (int dr = -(n - 1); dr <= n - 1; dr++) {
            for (int dc = -(n - 1); dc <= n - 1; dc++) {

                int overlap = 0;

                for (int r = 0; r < n; r++) {
                    for (int c = 0; c < n; c++) {

                        // Position (r, c) in img1 after translation
                        int nr = r + dr;
                        int nc = c + dc;

                        // Outside the image -> erased
                        if (nr < 0 || nr >= n || nc < 0 || nc >= n) {
                            continue;
                        }

                        // Both images contain 1 at the same position
                        if (img1[r][c] == 1 && img2[nr][nc] == 1) {
                            overlap++;
                        }
                    }
                }

                ans = Math.max(ans, overlap);
            }
        }

        return ans;
    }
}
