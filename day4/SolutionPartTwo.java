package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class SolutionPartTwo {
    public static void main(String[] args) {
        String filePath = "day4/tests/input.txt";
        int result = 0;
        List<IntPair> pairs = new ArrayList<>();
        try {
            List<String> paperGrid = Files.readAllLines(Path.of(filePath));
            boolean paperRollRemoved = true;
            while (paperRollRemoved) {
                paperRollRemoved = false;
                for (int i = 0; i < paperGrid.size(); i++) {
                    for (int j = 0; j < paperGrid.get(i).length(); j++) {
                        int amountOfPaper = 0;
                        if (paperGrid.get(i).charAt(j) == '@') {

                            for (int k = -1; k <= 1; k++) {
                                for (int l = -1; l <= 1; l++) {

                                    int currentY = i + k;
                                    int currentX = j + l;

                                    if (currentY < 0 || currentY >= paperGrid.size() || currentX < 0
                                            || currentX >= paperGrid.get(i).length()
                                            || (currentX == j && currentY == i)) {
                                        continue;
                                    }

                                    amountOfPaper += paperGrid.get(currentY).charAt(currentX) == '@' ? 1 : 0;
                                }
                            }
                            if (amountOfPaper < 4) {
                                result++;
                                pairs.add(new IntPair(i, j));
                                paperRollRemoved = true;
                            }
                        }
                    }
                }

                char[][] chars = new char[paperGrid.size()][];
                for (int i = 0; i < paperGrid.size(); i++) {
                    chars[i] = paperGrid.get(i).toCharArray();
                }
                for (IntPair p : pairs) {
                    chars[p.first()][p.second()] = '.';
                }
                paperGrid.clear();
                for (int i = 0; i < chars.length; i++) {
                    paperGrid.add(new String(chars[i]));
                }
            }
        } catch (IOException ex) {
            System.out.println("Some error happened");
        } finally {
            System.out.println(String.format("Result: %d", result));
        }

    }

    private record IntPair(int first, int second) {
    }
}
