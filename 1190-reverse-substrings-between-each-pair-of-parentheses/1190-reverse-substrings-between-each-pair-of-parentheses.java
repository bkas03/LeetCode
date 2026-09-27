import java.util.*;

class Solution {
    public String reverseParentheses(String s) {
        Stack<StringBuilder> stack = new Stack<>();
        StringBuilder current = new StringBuilder();

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                // Save the string before this '('
                stack.push(current);
                current = new StringBuilder();

            } else if (ch == ')') {
                // Reverse the substring inside parentheses
                current.reverse();

                // Add it back to the previous level
                StringBuilder previous = stack.pop();
                previous.append(current);
                current = previous;

            } else {
                // Normal lowercase character
                current.append(ch);
            }
        }

        return current.toString();
    }
}