import java.util.*;

class Solution {

    public List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<>();

        // Find how many '(' and ')' must be removed
        int left = 0;
        int right = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                left++;
            } else if (c == ')') {
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }

        dfs(s, 0, left, right, ans);

        return ans;
    }

    private void dfs(String s, int start, int leftRemove,
                     int rightRemove, List<String> ans) {

        // If no removals are left, check whether the string is valid
        if (leftRemove == 0 && rightRemove == 0) {
            if (isValid(s)) {
                ans.add(s);
            }
            return;
        }

        for (int i = start; i < s.length(); i++) {

            // Avoid removing the same consecutive parenthesis
            if (i > start && s.charAt(i) == s.charAt(i - 1)) {
                continue;
            }

            // Remove '('
            if (leftRemove > 0 && s.charAt(i) == '(') {

                String next = s.substring(0, i) + s.substring(i + 1);

                dfs(next, i, leftRemove - 1, rightRemove, ans);
            }

            // Remove ')'
            if (rightRemove > 0 && s.charAt(i) == ')') {

                String next = s.substring(0, i) + s.substring(i + 1);

                dfs(next, i, leftRemove, rightRemove - 1, ans);
            }
        }
    }

    private boolean isValid(String s) {

        int balance = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;

                if (balance < 0) {
                    return false;
                }
            }
        }

        return balance == 0;
    }
}