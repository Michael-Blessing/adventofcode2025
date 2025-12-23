package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class SolutionPartTwo {

    public static void main(String[] args) {
        String filePath = "day5/tests/input.txt";
        long result = 0;
        ArrayList<Range> ranges = new ArrayList<>();
        try {
            List<String> ingredients = Files.readAllLines(Path.of(filePath));
            for (int i = 0; i < ingredients.size(); i++) {
                if (!ingredients.get(i).contains("-")) {
                    continue;
                }
                String[] numbers = ingredients.get(i).split("-");
                ranges.add(new Range(Long.parseLong(numbers[0]), Long.parseLong(numbers[1])));
            }

            ranges.sort(Comparator.comparingLong(r -> r.from));

            long currentStart = ranges.get(0).from;
            long currentEnd = ranges.get(0).to;

            for (int i = 1; i < ranges.size(); i++) {
                Range r = ranges.get(i);
                if (r.from > currentEnd) {
                    result += (currentEnd - currentStart + 1);
                    currentStart = r.from;
                    currentEnd = r.to;
                } else {
                    currentEnd = Math.max(currentEnd, r.to);
                }
            }
            result += (currentEnd - currentStart + 1);
        } catch (IOException ex) {
            System.out.println("Some error happened");
        } finally {
            System.out.println(String.format("Result: %d", result));
        }

    }

    private record Range(long from, long to) {

    }

}
