package day3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class SolutionPartOne {

    public static void main(String[] args) {
        String filePath = "day3/tests/input.txt";

        int result = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int maxLeft = Integer.MIN_VALUE;
                int maxRight = Integer.MIN_VALUE;
                int positionMaxLeft = -1;
                for (int i = 0; i < line.length() - 1; i++) {
                    if (line.charAt(i) - '0' > maxLeft) {
                        maxLeft = line.charAt(i) - '0';
                        positionMaxLeft = i;
                    }
                }

                for (int i = positionMaxLeft + 1; i < line.length(); i++) {
                    if (line.charAt(i) - '0' > maxRight) {
                        maxRight = line.charAt(i) - '0';
                    }
                }

                result += maxLeft * 10 + maxRight;

            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("couldn't detect file");
        } catch (IOException e) {
            System.out.println("something went wrong");
        } finally {
            System.out.println("Result: " + result);
        }
    }
}
