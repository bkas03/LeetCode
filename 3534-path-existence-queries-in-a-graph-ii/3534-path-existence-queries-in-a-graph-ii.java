import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {

        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        // position of original index in sorted order
        int[] pos = new int[n];

        // connected component id in sorted order
        int[] comp = new int[n];

        int cid = 0;
        for (int i = 0; i < n; i++) {
            pos[arr[i][1]] = i;
            if (i > 0 && arr[i][0] - arr[i - 1][0] > maxDiff) {
                cid++;
            }
            comp[i] = cid;
        }

        // next[i] = farthest position reachable in one edge
        int[] next = new int[n];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (r < i) r = i;
            while (r + 1 < n && arr[r + 1][0] - arr[i][0] <= maxDiff) {
                r++;
            }
            next[i] = r;
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        int[][] up = new int[LOG][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = next[i];
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        int[] ans = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {

            int u = queries[qi][0];
            int v = queries[qi][1];

            if (u == v) {
                ans[qi] = 0;
                continue;
            }

            int pu = pos[u];
            int pv = pos[v];

            if (comp[pu] != comp[pv]) {
                ans[qi] = -1;
                continue;
            }

            if (pu > pv) {
                int tmp = pu;
                pu = pv;
                pv = tmp;
            }

            int cur = pu;
            int res = 0;

            for (int k = LOG - 1; k >= 0; k--) {
                if (up[k][cur] < pv) {
                    cur = up[k][cur];
                    res += 1 << k;
                }
            }

            ans[qi] = res + 1;
        }

        return ans;
    }
}