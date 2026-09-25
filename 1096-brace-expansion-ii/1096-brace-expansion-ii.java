
import java.util.*;

class Solution {

    private String s;
    private int index;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        index = 0;

        Set<String> result = parseExpression();

        // TreeSet sorts the strings lexicographically
        return new ArrayList<>(new TreeSet<>(result));
    }

    private Set<String> parseExpression() {

        Set<String> result = new HashSet<>();

        // Represents the current concatenated expression
        Set<String> current = new HashSet<>();
        current.add("");

        while (index < s.length() && s.charAt(index) != '}') {

            if (s.charAt(index) == ',') {

                // Comma means union
                result.addAll(current);

                current.clear();
                current.add("");

                index++;

            } else {

                // Parse the next factor
                Set<String> part = parseFactor();

                // Concatenate current × part
                Set<String> next = new HashSet<>();

                for (String a : current) {
                    for (String b : part) {
                        next.add(a + b);
                    }
                }

                current = next;
            }
        }

        // Add the final part
        result.addAll(current);

        return result;
    }

    private Set<String> parseFactor() {

        // Nested expression
        if (s.charAt(index) == '{') {

            index++; // skip '{'

            Set<String> result = parseExpression();

            index++; // skip '}'

            return result;
        }

        // Single lowercase letter
        Set<String> result = new HashSet<>();
        result.add(String.valueOf(s.charAt(index)));

        index++;

        return result;
    }
}
