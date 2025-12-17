package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class SolutionPartTwo {
    public static void main(String[] args) {
        Path filePath = Paths.get("day2/tests/input.txt");
        long invalidIDValue = 0;
        try {
            String content = Files.readString(filePath);
            content = content.replace("\n", "").replace("\r", "");
            String[] ranges = content.split(",");
            for (String range : ranges) {
                long firstNum = Long.parseLong(range.substring(0, range.indexOf("-")));
                long lastNum = Long.parseLong(range.substring(range.indexOf("-") + 1));
                for (long i = firstNum; i <= lastNum; i++) {
                    String currentNum = String.valueOf(i);
                    if (currentNum.charAt(0) == '0')
                        invalidIDValue += Long.parseLong(currentNum);

                    boolean doesRepeat = false;

                    for (int j = 1; j <= currentNum.length() / 2; j++) {
                        if (currentNum.length() % j != 0)
                            continue;
                        String part = currentNum.substring(0, j);

                        StringBuilder sb = new StringBuilder();
                        int times = currentNum.length() / j;
                        for (int k = 0; k < times; k++) {
                            sb.append(part);
                        }

                        if (sb.toString().equals(currentNum)) {
                            doesRepeat = true;
                        }
                    }

                    if (doesRepeat)
                        invalidIDValue += Long.parseLong(currentNum);

                }
            }
            System.out.println("Result: " + invalidIDValue);
        } catch (IOException e) {
            System.out.println("something went wrong");
        }

    }
}
