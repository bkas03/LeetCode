
import java.util.*;

class Solution {

    static class Interval {
        int l, r, w, idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    static class State {
        long score;
        List<Integer> indices;

        State(long score, List<Integer> indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    Interval[] arr;
    int n;
    State[][] dp;

    public int[] maximumWeight(List<List<Integer>> intervals) {

        n = intervals.size();

        arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            List<Integer> x = intervals.get(i);

            arr[i] = new Interval(
                x.get(0),
                x.get(1),
                x.get(2),
                i
            );
        }

        // Sort by left endpoint.
        Arrays.sort(arr, (a, b) -> {
            if (a.l != b.l) {
                return Integer.compare(a.l, b.l);
            }
            if (a.r != b.r) {
                return Integer.compare(a.r, b.r);
            }
            return Integer.compare(a.idx, b.idx);
        });

        dp = new State[n + 1][5];

        State ans = solve(0, 4);

        int[] result = new int[ans.indices.size()];

        for (int i = 0; i < ans.indices.size(); i++) {
            result[i] = ans.indices.get(i);
        }

        return result;
    }

    private State solve(int i, int k) {

        if (i >= n || k == 0) {
            return new State(0, new ArrayList<>());
        }

        if (dp[i][k] != null) {
            return dp[i][k];
        }

        // Option 1: Skip current interval.
        State skip = solve(i + 1, k);

        // Option 2: Take current interval.
        int next = findNext(i);

        State nextState = solve(next, k - 1);

        long takeScore = arr[i].w + nextState.score;

        List<Integer> takeIndices = new ArrayList<>();

        takeIndices.add(arr[i].idx);
        takeIndices.addAll(nextState.indices);

        // Required output is lexicographically smallest
        // array of original indices.
        Collections.sort(takeIndices);

        State take = new State(takeScore, takeIndices);

        State best;

        if (take.score > skip.score) {
            best = take;
        } 
        else if (take.score < skip.score) {
            best = skip;
        } 
        else {
            // Same score -> lexicographically smaller indices.
            if (compare(take.indices, skip.indices) < 0) {
                best = take;
            } else {
                best = skip;
            }
        }

        dp[i][k] = best;

        return best;
    }

    private int findNext(int i) {

        // Because intervals sharing a boundary overlap,
        // next.l must be strictly greater than current.r.
        int target = arr[i].r + 1;

        int lo = i + 1;
        int hi = n;

        while (lo < hi) {

            int mid = lo + (hi - lo) / 2;

            if (arr[mid].l >= target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    private int compare(List<Integer> a, List<Integer> b) {

        int len = Math.min(a.size(), b.size());

        for (int i = 0; i < len; i++) {

            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(a.get(i), b.get(i));
            }
        }

        // If one is a prefix of the other,
        // shorter array is lexicographically smaller.
        return Integer.compare(a.size(), b.size());
    }
}
