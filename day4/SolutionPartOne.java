package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class SolutionPartOne {
    public static void main(String[] args) {
        String filePath = "day4/tests/input.txt";
        int result = 0;
        try {
            List<String> paperGrid = Files.readAllLines(Path.of(filePath));
            for (int i = 0; i < paperGrid.size(); i++) {
                for (int j = 0; j < paperGrid.get(i).length(); j++) {
                    int amountOfPaper = 0;
                    if (paperGrid.get(i).charAt(j) == '@') {

                        for (int k = -1; k <= 1; k++) {
                            for (int l = -1; l <= 1; l++) {

                                int currentY = i + k;
                                int currentX = j + l;

                                if (currentY < 0 || currentY >= paperGrid.size() || currentX < 0
                                        || currentX >= paperGrid.get(i).length() || (currentX == j && currentY == i)) {
                                    continue;
                                }

                                amountOfPaper += paperGrid.get(currentY).charAt(currentX) == '@' ? 1 : 0;
                            }
                        }
                        result += amountOfPaper < 4 ? 1 : 0;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Some error happened");
        } finally {
            System.out.println(String.format("Result: %d", result));
        }

    }
}