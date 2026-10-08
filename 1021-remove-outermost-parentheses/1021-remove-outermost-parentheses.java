class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder result = new StringBuilder();
        int depth = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                // If depth is 0, this is the outermost '('
                if (depth > 0) {
                    result.append(ch);
                }
                depth++;
            } 
            else {
                depth--;

                // If depth becomes 0, this is the outermost ')'
                if (depth > 0) {
                    result.append(ch);
                }
            }
        }

        return result.toString();
    }
}