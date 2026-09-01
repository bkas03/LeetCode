
import java.util.*;

class Solution {

    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int sr = -1, sc = -1;
        int litterCount = 0;

        // Give every litter cell a bit number.
        int[][] litterId = new int[m][n];
        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = classroom[i].charAt(j);

                if (c == 'S') {
                    sr = i;
                    sc = j;
                } else if (c == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        int fullMask = (1 << litterCount) - 1;

        // State:
        // r, c   -> position
        // mask   -> collected litter
        // e      -> remaining energy
        //
        // Encode everything into one integer.
        int maxStates = m * n * (1 << litterCount) * (energy + 1);

        boolean[] visited = new boolean[maxStates];

        ArrayDeque<int[]> q = new ArrayDeque<>();

        int startMask = 0;
        int startEnergy = energy;

        int startId = encode(
                sr, sc, startMask, startEnergy,
                n, 1 << litterCount, energy + 1
        );

        visited[startId] = true;

        q.offer(new int[]{sr, sc, startMask, startEnergy, 0});

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!q.isEmpty()) {

            int[] cur = q.poll();

            int r = cur[0];
            int c = cur[1];
            int mask = cur[2];
            int e = cur[3];
            int moves = cur[4];

            // All litter collected.
            if (mask == fullMask) {
                return moves;
            }

            for (int d = 0; d < 4; d++) {

                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                char cell = classroom[nr].charAt(nc);

                // Cannot enter obstacle.
                if (cell == 'X') {
                    continue;
                }

                // Need one unit of energy for every move.
                if (e == 0) {
                    continue;
                }

                int ne = e - 1;
                int nmask = mask;

                // Collect litter.
                if (cell == 'L') {
                    int id = litterId[nr][nc];
                    nmask |= (1 << id);
                }

                // Reset energy AFTER entering R.
                if (cell == 'R') {
                    ne = energy;
                }

                int id = encode(
                        nr, nc, nmask, ne,
                        n, 1 << litterCount, energy + 1
                );

                if (!visited[id]) {
                    visited[id] = true;

                    q.offer(new int[]{
                            nr, nc, nmask, ne, moves + 1
                    });
                }
            }
        }

        return -1;
    }

    private int encode(
            int r, int c, int mask, int energy,
            int n, int maskSize, int energySize
    ) {
        return (((r * n + c) * maskSize + mask) * energySize + energy);
    }
}
