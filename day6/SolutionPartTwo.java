package day6;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

class SolutionPartTwo {

    public static void main(String[] args) {
        String filePath = "day6/tests/input.txt";
        BigInteger result = BigInteger.ZERO;

        try {
            List<String> worksheet = Files.readAllLines(Path.of(filePath));
            int problemAmount = worksheet.get(0).trim().split("\\s+").length;
            int currentOffset = worksheet.get(0).length() - 1;
            for (int x = problemAmount - 1; x >= 0; x--) {

                String[] operations = worksheet.get(worksheet.size() - 1).trim().split("\\s+");
                boolean isMultiplication = "*".equals(operations[x]);
                BigInteger currentResult = isMultiplication
                        ? BigInteger.ONE
                        : BigInteger.ZERO;

                int amountOfDigits = 0;
                for (int y = 0; y < worksheet.size() - 1; y++) {
                    amountOfDigits = Math.max(worksheet.get(y).trim().split("\\s+")[x].length(), amountOfDigits);
                }

                StringBuilder[] sb = new StringBuilder[amountOfDigits];
                Arrays.setAll(sb, i -> new StringBuilder());
                for (int i = 0; i < amountOfDigits; i++) {
                    for (int y = 0; y < worksheet.size() - 1; y++) {
                        int colIndex = currentOffset - (amountOfDigits - 1 - i);
                        if (colIndex >= 0 && colIndex < worksheet.get(y).length()) {
                            if (worksheet.get(y).charAt(currentOffset - i) != ' ') {
                                sb[i].append(worksheet.get(y).charAt(currentOffset - i));
                            }
                        }
                    }
                }

                for (StringBuilder sb1 : sb) {
                    if (!sb1.isEmpty()) {
                        BigInteger value = new BigInteger(sb1.toString());
                        currentResult = isMultiplication
                                ? currentResult.multiply(value)
                                : currentResult.add(value);
                    }
                }

                currentOffset -= amountOfDigits + 1;
                result = result.add(currentResult);
            }
        } catch (IOException ex) {
            System.out.println("Some error happened");
        } finally {
            System.out.println(String.format("Result: %d", result));
        }
    }
}
