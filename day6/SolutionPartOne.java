package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class SolutionPartOne {

    public static void main(String[] args) {
        String filePath = "day6/tests/input.txt";
        long result = 0;
        try {
            List<String> worksheet = Files.readAllLines(Path.of(filePath));
            int problemAmount = worksheet.get(0).trim().split("\\s+").length;
            for (int x = 0; problemAmount > x; x++) {

                String[] operations = worksheet.get(worksheet.size() - 1).trim().split("\\s+");
                boolean isMultiplication = "*".equals(operations[x]);
                long currentResult = Integer.parseInt(worksheet.get(0).trim().split("\\s+")[x]);
                for (int y = 1; y < worksheet.size() - 1; y++) {
                    if (isMultiplication) {
                        currentResult *= Integer.parseInt(worksheet.get(y).trim().split("\\s+")[x]);
                    } else {
                        currentResult += Integer.parseInt(worksheet.get(y).trim().split("\\s+")[x]);
                    }
                }
                result += currentResult;
            }
        } catch (IOException ex) {
            System.out.println("Some error happened");
        } finally {
            System.out.println(String.format("Result: %d", result));
        }

    }
}
