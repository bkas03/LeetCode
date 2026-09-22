class Solution {

    static class Node {
        int prod;
        int[] pref;

        Node(int k) {
            pref = new int[k];
        }
    }

    int n;
    int k;
    int[] nums;
    Node[] tree;

    Node createLeaf(int value) {
        Node node = new Node(k);

        int r = value % k;

        node.prod = r;
        node.pref[r] = 1;

        return node;
    }

    Node merge(Node left, Node right) {
        Node res = new Node(k);

        // Product of the whole combined segment
        res.prod = (int) ((long) left.prod * right.prod % k);

        // Prefixes completely inside the left segment
        for (int r = 0; r < k; r++) {
            res.pref[r] += left.pref[r];
        }

        // Prefixes that contain the entire left segment
        // and then some prefix of the right segment.
        for (int r = 0; r < k; r++) {
            if (right.pref[r] == 0) {
                continue;
            }

            int newRemainder =
                (int) ((long) left.prod * r % k);

            res.pref[newRemainder] += right.pref[r];
        }

        return res;
    }

    void build(int node, int l, int r) {
        if (l == r) {
            tree[node] = createLeaf(nums[l]);
            return;
        }

        int mid = (l + r) >>> 1;

        build(node << 1, l, mid);
        build(node << 1 | 1, mid + 1, r);

        tree[node] = merge(
            tree[node << 1],
            tree[node << 1 | 1]
        );
    }

    void update(int node, int l, int r, int index, int value) {
        if (l == r) {
            tree[node] = createLeaf(value);
            return;
        }

        int mid = (l + r) >>> 1;

        if (index <= mid) {
            update(node << 1, l, mid, index, value);
        } else {
            update(node << 1 | 1, mid + 1, r, index, value);
        }

        tree[node] = merge(
            tree[node << 1],
            tree[node << 1 | 1]
        );
    }

    Node query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = (l + r) >>> 1;

        if (qr <= mid) {
            return query(node << 1, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node << 1 | 1, mid + 1, r, ql, qr);
        }

        Node left = query(
            node << 1,
            l,
            mid,
            ql,
            qr
        );

        Node right = query(
            node << 1 | 1,
            mid + 1,
            r,
            ql,
            qr
        );

        return merge(left, right);
    }

    public int[] resultArray(
        int[] nums,
        int k,
        int[][] queries
    ) {
        this.nums = nums;
        this.k = k;
        this.n = nums.length;

        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Persistent update
            nums[index] = value;

            update(
                1,
                0,
                n - 1,
                index,
                value
            );

            // After removing nums[0..start-1],
            // remaining array is nums[start..n-1].
            Node res = query(
                1,
                0,
                n - 1,
                start,
                n - 1
            );

            // We need prefixes beginning exactly at 'start'.
            result[i] = res.pref[x];
        }

        return result;
    }
}