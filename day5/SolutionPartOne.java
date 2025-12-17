package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class SolutionPartOne {
    public static void main(String[] args) {
        String filePath = "day5/tests/input.txt";
        int result = 0;
        List<LongPair> ranges = new ArrayList<>();
        try {
            List<String> ingredients = Files.readAllLines(Path.of(filePath));
            for (int i = 0; i < ingredients.size(); i++) {
                if (ingredients.get(i).contains("-")) {
                    String[] numbers = ingredients.get(i).split("-");
                    ranges.add(new LongPair(Long.parseLong(numbers[0]), Long.parseLong(numbers[1])));
                } else {
                    if (ingredients.get(i).length() <= 0)
                        continue;
                    long currentNum = Long.parseLong(ingredients.get(i));
                    for (LongPair p : ranges) {
                        if (p.first() <= currentNum && currentNum <= p.second()) {
                            result++;
                            break;
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

    private record LongPair(long first, long second) {
    }
}
