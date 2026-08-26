class Solution {
    public String shortestBeautifulSubstring(String s, int k) {

        int n = s.length();

        int left = 0;
        int ones = 0;

        String ans = "";

        for (int right = 0; right < n; right++) {

            if (s.charAt(right) == '1') {
                ones++;
            }

            // We have exactly k ones
            if (ones == k) {

                // Remove unnecessary leading zeros
                while (s.charAt(left) == '0') {
                    left++;
                }

                String current = s.substring(left, right + 1);

                // First valid answer
                if (ans.equals("")) {
                    ans = current;
                }
                // Shorter answer
                else if (current.length() < ans.length()) {
                    ans = current;
                }
                // Same length -> lexicographically smaller
                else if (current.length() == ans.length()
                         && current.compareTo(ans) < 0) {
                    ans = current;
                }

                // Move past the first 1
                left++;
                ones--;
            }
        }

        return ans;
    }
}