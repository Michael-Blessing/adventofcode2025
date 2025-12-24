package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SolutionPartTwo {

    public static void main(String[] args) {
        String filePath = "day9/tests/input.txt";
        long result = 0;
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            List<Tile> vertices = new ArrayList<>();
            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }
                String[] coords = line.split(",");
                vertices.add(new Tile(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
            }

            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (i == j) {
                        continue;
                    }

                    Tile t1 = vertices.get(i);
                    Tile t2 = vertices.get(j);

                    int xMin = Math.min(t1.x(), t2.x());
                    int xMax = Math.max(t1.x(), t2.x());
                    int yMin = Math.min(t1.y(), t2.y());
                    int yMax = Math.max(t1.y(), t2.y());

                    if (isInside(xMin, yMin, vertices)
                            && isInside(xMax, yMax, vertices)
                            && isInside(xMin, yMax, vertices)
                            && isInside(xMax, yMin, vertices)) {

                        if (!boundaryCutsThrough(xMin, xMax, yMin, yMax, vertices)) {
                            long area = (long) (xMax - xMin + 1) * (yMax - yMin + 1);
                            result = Math.max(result, area);
                        }
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println("Some error happened");
        } finally {
            System.out.println(String.format("Result: %d", result));
        }

    }

    private record Tile(int x, int y) {

    }

    private static boolean isInside(int tx, int ty, List<Tile> poly) {
        boolean inside = false;
        int n = poly.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Tile vi = poly.get(i);
            Tile vj = poly.get(j);

            if (((vi.y() <= ty && ty < vj.y()) || (vj.y() <= ty && ty < vi.y()))
                    && (tx < (double) (vj.x() - vi.x()) * (ty - vi.y()) / (vj.y() - vi.y()) + vi.x())) {
                inside = !inside;
            }

            if (isPointOnSegment(tx, ty, vi, vj)) {
                return true;
            }
        }
        return inside;
    }

    private static boolean isPointOnSegment(int px, int py, Tile a, Tile b) {
        return px >= Math.min(a.x(), b.x()) && px <= Math.max(a.x(), b.x())
                && py >= Math.min(a.y(), b.y()) && py <= Math.max(a.y(), b.y())
                && (long) (py - a.y()) * (b.x() - a.x()) == (long) (px - a.x()) * (b.y() - a.y());
    }

    private static boolean boundaryCutsThrough(int xMin, int xMax, int yMin, int yMax, List<Tile> poly) {
        for (int i = 0; i < poly.size(); i++) {
            Tile v1 = poly.get(i);
            Tile v2 = poly.get((i + 1) % poly.size());

            if (v1.y() == v2.y()) {
                if (v1.y() > yMin && v1.y() < yMax) {
                    int sXMin = Math.min(v1.x(), v2.x());
                    int sXMax = Math.max(v1.x(), v2.x());
                    if (sXMax > xMin && sXMin < xMax) {
                        return true;
                    }
                }
            } else if (v1.x() == v2.x()) {
                if (v1.x() > xMin && v1.x() < xMax) {
                    int sYMin = Math.min(v1.y(), v2.y());
                    int sYMax = Math.max(v1.y(), v2.y());
                    if (sYMax > yMin && sYMin < yMax) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
