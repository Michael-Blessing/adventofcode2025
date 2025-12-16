package day3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

class Solution {
    public static void main(String[] args) {
        String filePath = "day3/tests/input.txt";

        BigInteger result = BigInteger.ZERO;
        final int KEEP_DIGITS = 12;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int deletionsRemaining = line.length() - KEEP_DIGITS;
                StringBuilder sequence = new StringBuilder();

                for (int i = 0; i < line.length(); i++) {
                    char currentDigit = line.charAt(i);

                    while (deletionsRemaining > 0 &&
                            sequence.length() > 0 &&
                            currentDigit > sequence.charAt(sequence.length() - 1)) {

                        sequence.deleteCharAt(sequence.length() - 1);
                        deletionsRemaining--;
                    }

                    sequence.append(currentDigit);
                }

                if (sequence.length() > KEEP_DIGITS) {
                    sequence.setLength(KEEP_DIGITS);
                }

                result = result.add(new BigInteger(sequence.toString()));

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
