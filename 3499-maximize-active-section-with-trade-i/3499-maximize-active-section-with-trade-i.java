class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int n = s.length();

        int ones = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') ones++;
        }

        ArrayList<Character> type = new ArrayList<>();
        ArrayList<Integer> len = new ArrayList<>();

        int i = 0;
        while (i < n) {
            char c = s.charAt(i);
            int j = i;
            while (j < n && s.charAt(j) == c) j++;
            type.add(c);
            len.add(j - i);
            i = j;
        }

        int best = 0;

        for (int k = 1; k + 1 < type.size(); k++) {
            if (type.get(k) == '1' &&
                type.get(k - 1) == '0' &&
                type.get(k + 1) == '0') {

                best = Math.max(best, len.get(k - 1) + len.get(k + 1));
            }
        }

        return ones + best;
    }
}