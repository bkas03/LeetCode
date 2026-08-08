import java.util.*;

class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        /*
         * nextDiff[i] = first index >= i whose character is
         * different from word1[i].
         */
        int[] nextDiff = new int[n];

        if (n > 0) {
            nextDiff[n - 1] = n;

            for (int i = n - 2; i >= 0; i--) {
                if (word1.charAt(i) != word1.charAt(i + 1)) {
                    nextDiff[i] = i + 1;
                } else {
                    nextDiff[i] = nextDiff[i + 1];
                }
            }
        }

        /*
         * prevDiff[i] = last index < i whose character is
         * different from word1[i].
         */
        int[] prevDiff = new int[n];

        if (n > 0) {
            prevDiff[0] = -1;

            for (int i = 1; i < n; i++) {
                if (word1.charAt(i) != word1.charAt(i - 1)) {
                    prevDiff[i] = i - 1;
                } else {
                    prevDiff[i] = prevDiff[i - 1];
                }
            }
        }

        /*
         * Store positions of every character.
         */
        ArrayList<Integer>[] pos = new ArrayList[26];

        for (int c = 0; c < 26; c++) {
            pos[c] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            pos[word1.charAt(i) - 'a'].add(i);
        }

        /*
         * latest[j][k]:
         *
         * Maximum possible index of the FIRST character of
         * word2[j..] in a valid subsequence using at most k
         * mismatches.
         *
         * This is the important suffix feasibility information.
         */
        int[][] latest = new int[m + 1][2];

        // Empty suffix can start after any position.
        latest[m][0] = n;
        latest[m][1] = n;

        for (int j = m - 1; j >= 0; j--) {
            int target = word2.charAt(j) - 'a';

            for (int k = 0; k <= 1; k++) {
                int limit = latest[j + 1][k];

                // Try matching word2[j] exactly.
                int best = lastOccurrenceBefore(pos[target], limit);

                // Try using the mismatch here.
                if (k == 1 && limit > 0) {
                    int p = limit - 1;

                    int different;

                    if (word1.charAt(p) - 'a' != target) {
                        different = p;
                    } else {
                        different = prevDiff[p];
                    }

                    best = Math.max(best, different);
                }

                latest[j][k] = best;
            }
        }

        int[] ans = new int[m];

        int prev = -1;

        // 0 = mismatch has not been used
        // 1 = mismatch has already been used
        int used = 0;

        for (int j = 0; j < m; j++) {
            int from = prev + 1;

            /*
             * Candidate 1:
             * Match word2[j] exactly.
             *
             * If mismatch has not been used, we still have
             * one mismatch available for the suffix.
             */
            int target = word2.charAt(j) - 'a';

            int exact = firstOccurrenceAtOrAfter(pos[target], from);

            if (exact < n) {
                int remaining = 1 - used;

                if (exact >= latest[j + 1][remaining]) {
                    exact = n;
                }
            }

            /*
             * Candidate 2:
             * Use our one mismatch at this position.
             */
            int mismatch = n;

            if (used == 0 && from < n) {
                if (word1.charAt(from) - 'a' != target) {
                    mismatch = from;
                } else {
                    mismatch = nextDiff[from];
                }

                /*
                 * After using the mismatch, the remaining suffix
                 * must match exactly.
                 */
                if (mismatch >= latest[j + 1][0]) {
                    mismatch = n;
                }
            }

            /*
             * We need the smallest possible index because the
             * answer array itself must be lexicographically smallest.
             */
            if (exact < mismatch) {
                if (exact == n) {
                    return new int[0];
                }

                ans[j] = exact;
                prev = exact;
            } else {
                if (mismatch == n) {
                    return new int[0];
                }

                ans[j] = mismatch;
                prev = mismatch;
                used = 1;
            }
        }

        return ans;
    }

    private int firstOccurrenceAtOrAfter(
            ArrayList<Integer> list, int x) {

        int lo = 0;
        int hi = list.size();

        while (lo < hi) {
            int mid = (lo + hi) >>> 1;

            if (list.get(mid) < x) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }

        return lo < list.size() ? list.get(lo) : Integer.MAX_VALUE;
    }

    private int lastOccurrenceBefore(
            ArrayList<Integer> list, int x) {

        int lo = 0;
        int hi = list.size();

        while (lo < hi) {
            int mid = (lo + hi) >>> 1;

            if (list.get(mid) < x) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }

        return lo == 0 ? -1 : list.get(lo - 1);
    }
}