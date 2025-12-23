package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

class SolutionPartTwo {

    public static void main(String[] args) {
        String filePath = "day7/tests/input.txt";
        long result = 0;
        try {
            List<String> manifold = Files.readAllLines(Path.of(filePath));
            for (int i = 0; i < manifold.get(0).length(); i++) {
                if (manifold.get(0).charAt(i) == 'S') {
                    long[][] memo = new long[manifold.size()][manifold.get(0).length()];
                    for (long[] row : memo) {
                        Arrays.fill(row, -1);
                    }

                    result = tachyomBeam(manifold, i, 0, memo);
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Some error happened");
        } finally {
            System.out.println(String.format("Result: %d", result));
        }

    }

    private static long tachyomBeam(List<String> manifold, int x, int y, long[][] memo) {
        if (x >= manifold.get(0).length() || 0 > x || y >= manifold.size() || y < 0) {
            return 1;
        }

        if (memo[y][x] != -1) {
            return memo[y][x];
        }

        long result;

        if (manifold.get(y).charAt(x) == '^') {
            result = tachyomBeam(manifold, x - 1, y, memo)
                    + tachyomBeam(manifold, x + 1, y, memo);
        } else {
            result = tachyomBeam(manifold, x, y + 1, memo);
        }

        memo[y][x] = result;
        return result;
    }
}
