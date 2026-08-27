class Solution {
    int[] cnt = new int[26];
    String target;
    int n;
    char[] ans;

    public String lexGreaterPermutation(String s, String target) {
        this.target = target;
        this.n = s.length();
        this.ans = new char[n];

        for (char c : s.toCharArray()) cnt[c - 'a']++;

        return dfs(0, false) ? new String(ans) : "";
    }

    private boolean dfs(int idx, boolean greater) {
        if (idx == n) return greater;

        if (greater) {
            for (int c = 0; c < 26; c++) {
                while (cnt[c] > 0) {
                    ans[idx++] = (char) ('a' + c);
                    cnt[c]--;
                }
            }
            return true;
        }

        int t = target.charAt(idx) - 'a';

        // Try equal character first
        if (cnt[t] > 0) {
            cnt[t]--;
            ans[idx] = (char) ('a' + t);
            if (dfs(idx + 1, false)) return true;
            cnt[t]++;
        }

        // Try the smallest larger character
        for (int c = t + 1; c < 26; c++) {
            if (cnt[c] > 0) {
                cnt[c]--;
                ans[idx] = (char) ('a' + c);

                int pos = idx + 1;
                while (pos < n) {
                    for (int k = 0; k < 26; k++) {
                        while (cnt[k] > 0 && pos < n) {
                            ans[pos++] = (char) ('a' + k);
                            cnt[k]--;
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }
}