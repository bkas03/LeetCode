class Solution {

    static class Node {
        int leftChar, rightChar;
        int leftLen, rightLen;
        int best, len;

        Node() {}

        Node(int c) {
            leftChar = rightChar = c;
            leftLen = rightLen = best = len = 1;
        }
    }

    Node[] tree;
    char[] s;

    public int[] longestRepeating(
            String s,
            String queryCharacters,
            int[] queryIndices) {

        this.s = s.toCharArray();

        int n = s.length();
        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            int index = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            // Update the actual character array
            this.s[index] = ch;

            update(1, 0, n - 1, index, ch - 'a');

            ans[i] = tree[1].best;
        }

        return ans;
    }

    private void build(int node, int l, int r) {
        if (l == r) {
            tree[node] = new Node(s[l] - 'a');
            return;
        }

        int mid = l + (r - l) / 2;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(int node, int l, int r, int index, int ch) {
        if (l == r) {
            tree[node] = new Node(ch);
            return;
        }

        int mid = l + (r - l) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, ch);
        } else {
            update(node * 2 + 1, mid + 1, r, index, ch);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private Node merge(Node a, Node b) {
        Node res = new Node();

        res.len = a.len + b.len;

        res.leftChar = a.leftChar;
        res.rightChar = b.rightChar;

        // Prefix
        res.leftLen = a.leftLen;

        if (a.leftLen == a.len &&
            a.rightChar == b.leftChar) {

            res.leftLen = a.len + b.leftLen;
        }

        // Suffix
        res.rightLen = b.rightLen;

        if (b.rightLen == b.len &&
            a.rightChar == b.leftChar) {

            res.rightLen = b.len + a.rightLen;
        }

        // Best inside either half
        res.best = Math.max(a.best, b.best);

        // Best crossing the middle
        if (a.rightChar == b.leftChar) {
            res.best = Math.max(
                res.best,
                a.rightLen + b.leftLen
            );
        }

        return res;
    }
}