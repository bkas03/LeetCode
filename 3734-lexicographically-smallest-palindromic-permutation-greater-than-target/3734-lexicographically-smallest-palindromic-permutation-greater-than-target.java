import java.util.*;

class Solution {

    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();

        int[] cnt = new int[26];

        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        // Check whether a palindrome permutation is possible.
        int odd = 0;
        int middle = -1;

        for (int c = 0; c < 26; c++) {
            if ((cnt[c] & 1) == 1) {
                odd++;
                middle = c;
            }
        }

        if (odd > 1) {
            return "";
        }

        int m = n / 2;

        // Counts available in the left half.
        int[] halfCnt = new int[26];

        for (int c = 0; c < 26; c++) {
            halfCnt[c] = cnt[c] / 2;
        }

        /*
         * First find the largest prefix of target that can be
         * exactly matched by a permutation of the half.
         */
        char[] half = new char[m];

        int[] remaining = halfCnt.clone();

        int matched = 0;

        while (matched < m) {
            int c = target.charAt(matched) - 'a';

            if (remaining[c] == 0) {
                break;
            }

            half[matched] = target.charAt(matched);
            remaining[c]--;
            matched++;
        }

        /*
         * Case 1:
         * We matched the entire first half of target.
         *
         * Construct that palindrome and see if it is already
         * strictly greater than target.
         */
        if (matched == m) {
            String candidate = build(half, middle, n);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * Now we need to make the palindrome larger.
         *
         * Try changing the rightmost possible position.
         *
         * We reconstruct the remaining counts for each position.
         */
        for (int i = Math.min(matched, m - 1); i >= 0; i--) {

            // Restore all characters from i onward.
            int[] rem = halfCnt.clone();

            for (int j = 0; j < i; j++) {
                int c = half[j] - 'a';
                rem[c]--;
            }

            int currentTarget = target.charAt(i) - 'a';

            /*
             * Choose the smallest available character strictly
             * greater than target[i].
             */
            for (int c = currentTarget + 1; c < 26; c++) {

                if (rem[c] == 0) {
                    continue;
                }

                char[] resultHalf = new char[m];

                // Keep prefix equal to target.
                for (int j = 0; j < i; j++) {
                    resultHalf[j] = target.charAt(j);
                }

                // Make this position larger.
                resultHalf[i] = (char) ('a' + c);
                rem[c]--;

                // Fill suffix with smallest available characters.
                int p = i + 1;

                for (int x = 0; x < 26; x++) {
                    while (rem[x] > 0) {
                        resultHalf[p++] = (char) ('a' + x);
                        rem[x]--;
                    }
                }

                return build(resultHalf, middle, n);
            }
        }

        return "";
    }

    private String build(char[] half, int middle, int n) {
        char[] res = new char[n];

        int m = half.length;

        // Left half
        for (int i = 0; i < m; i++) {
            res[i] = half[i];
        }

        // Middle
        if ((n & 1) == 1) {
            res[m] = (char) ('a' + middle);
        }

        // Right half
        for (int i = 0; i < m; i++) {
            res[n - 1 - i] = half[i];
        }

        return new String(res);
    }
}