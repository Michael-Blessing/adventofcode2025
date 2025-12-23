package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SolutionPartOne {

    public static void main(String[] args) {
        String filePath = "day9/tests/input.txt";
        long result = 0;
        try {
            List<String> floor = Files.readAllLines(Path.of(filePath));
            ArrayList<Tile> tiles = new ArrayList<>();

            for (int i = 0; i < floor.size(); i++) {
                String[] cords = floor.get(i).split(",");
                tiles.add(new Tile(Integer.parseInt(cords[0]), Integer.parseInt(cords[1])));
            }

            for (int i = 0; i < tiles.size() - 1; i++) {
                for (int j = i + 1; j < tiles.size(); j++) {
                    long currentArea = (long) (Math.abs(tiles.get(j).x - tiles.get(i).x) + 1) * (1 + Math.abs(tiles.get(j).y - tiles.get(i).y));
                    result = Math.max(currentArea, result);
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
}
