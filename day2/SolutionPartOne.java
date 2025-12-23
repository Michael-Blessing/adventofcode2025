package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class SolutionPartOne {

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
                    if (currentNum.charAt(0) == '0' || currentNum.substring(0, currentNum.length() / 2)
                            .equals(currentNum.substring(currentNum.length() / 2))) {
                        invalidIDValue += Long.parseLong(currentNum);
                    }
                }
            }
            System.out.println(invalidIDValue);
        } catch (IOException e) {
            System.out.println("something went wrong");
        }

    }
}
