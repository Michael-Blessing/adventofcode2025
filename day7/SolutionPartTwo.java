package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class SolutionPartTwo {
    public static void main(String[] args) {
        String filePath = "day7/tests/input.txt";
        long result = 0;
        try {
            List<String> manifold = Files.readAllLines(Path.of(filePath));
            for (int i = 0; i < manifold.get(0).length(); i++) {
                if (manifold.get(0).charAt(i) == 'S') {
                    result = tachyomBeam(manifold, i, 0);
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Some error happened");
        } finally {
            System.out.println(String.format("Result: %d", result));
        }

    }

    private static int tachyomBeam(List<String> manifold, int x, int y) {
        if (x >= manifold.get(0).length() || 0 > x || y >= manifold.size() || y < 0 || manifold.get(y).charAt(x) == '|')
            return 0;
        if (manifold.get(y).charAt(x) == '^')
            return tachyomBeam(manifold, x - 1, y) + 1 + tachyomBeam(manifold, x + 1, y);
        addBeam(manifold, x, y);
        return tachyomBeam(manifold, x, y + 1);
    }

    private static void addBeam(List<String> manifest, int x, int y) {
        String s = manifest.get(y);
        String changed = s.substring(0, x) + '|' + s.substring(x + 1);
        manifest.set(y, changed);
    }
}
