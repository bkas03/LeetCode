import java.util.*;

class Solution {

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        String mid = "";
        ArrayList<Integer> half = new ArrayList<>();
        int halfLen = 0;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1) mid = String.valueOf((char) ('a' + i));
            half.add(freq[i] / 2);
            halfLen += freq[i] / 2;
        }

        long limit = k;
        long total = countWays(half, halfLen, limit);
        if (total < k) return "";

        StringBuilder first = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {
            for (int c = 0; c < 26; c++) {
                if (half.get(c) == 0) continue;

                half.set(c, half.get(c) - 1);
                long ways = countWays(half, halfLen - pos - 1, limit);

                if (ways >= k) {
                    first.append((char) ('a' + c));
                    break;
                } else {
                    k -= ways;
                    half.set(c, half.get(c) + 1);
                }
            }
        }

        String second = new StringBuilder(first).reverse().toString();
        return first.toString() + mid + second;
    }

    private long countWays(ArrayList<Integer> cnt, int total, long limit) {
        long res = 1;
        int rem = total;

        for (int f : cnt) {
            if (f == 0) continue;
            res = multiplyChoose(res, rem, f, limit);
            if (res >= limit) return limit;
            rem -= f;
        }
        return Math.min(res, limit);
    }

    private long multiplyChoose(long cur, int n, int r, long limit) {
        if (r == 0) return cur;
        r = Math.min(r, n - r);

        for (int i = 1; i <= r; i++) {
            long a = n - r + i;
            long b = i;

            long g = gcd(a, b);
            a /= g;
            b /= g;

            g = gcd(cur, b);
            cur /= g;
            b /= g;

            if (cur > limit / a) return limit;
            cur *= a;
            cur /= b;

            if (cur >= limit) return limit;
        }

        return cur;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}