class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        backtrack(result, "", 0, 0, n);

        return result;
    }

    private void backtrack(List<String> result, String s,
                            int open, int close, int n) {

        // We have used all n pairs
        if (s.length() == 2 * n) {
            result.add(s);
            return;
        }

        // We can add '(' if we still have some left
        if (open < n) {
            backtrack(result, s + "(", open + 1, close, n);
        }

        // We can add ')' only if there is an unmatched '('
        if (close < open) {
            backtrack(result, s + ")", open, close + 1, n);
        }
    }
}