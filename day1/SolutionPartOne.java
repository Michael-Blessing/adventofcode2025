package day1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class SolutionPartOne {
    public static void main(String[] args) {
        String filePath = "day1/tests/input.txt";
        int direction = 50;
        int amountOfZeros = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int moveAmount = Integer.parseInt(line.substring(1));
                boolean moveRight = line.substring(0, 1).equals("R");
                if (moveRight) {
                    direction = (direction + moveAmount) % 100;
                } else {
                    direction = ((((direction - moveAmount) % 100) + 100) % 100);
                }
                if (direction == 0)
                    amountOfZeros++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("couldn't detect file");
        } catch (IOException e) {
            System.out.println("something went wrong");
        } finally {
            System.out.println("Result: " + amountOfZeros);
        }

    }
}