package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolutionPartOne {

    public static void main(String[] args) {
        String filePath = "day10/tests/input.txt";
        int result = 0;
        try {
            Pattern pattern = Pattern.compile("(\\(([^)]*)\\))|(\\[([^]]*)\\])|(\\{([^}]*)\\})");

            List<String> manual = Files.readAllLines(Path.of(filePath));

            for (int i = 0; i < manual.size(); i++) {
                int[] lights = new int[0];
                ArrayList<ArrayList<Integer>> buttons = new ArrayList<>();
                int[] voltages = new int[0];
                Matcher matcher = pattern.matcher(manual.get(i));
                while (matcher.find()) {
                    if (matcher.group(2) != null) {
                        String[] buttonsString = matcher.group(2).split(",");
                        ArrayList<Integer> currentButtons = new ArrayList<>();
                        for (String button : buttonsString) {
                            currentButtons.add(Integer.valueOf(button));
                        }
                        buttons.add(currentButtons);
                    } else if (matcher.group(4) != null) {
                        int[] currentLights = new int[matcher.group(4).length()];
                        for (int j = 0; j < matcher.group(4).length(); j++) {
                            currentLights[j] = matcher.group(4).charAt(j) == '#' ? 1 : 0;
                        }
                        lights = currentLights;
                    } else if (matcher.group(6) != null) {
                        String[] voltagesString = matcher.group(6).split(",");
                        int[] currentVoltages = new int[voltagesString.length];
                        for (int j = 0; j < voltagesString.length; j++) {
                            currentVoltages[j] = Integer.parseInt(voltagesString[j]);
                        }
                        voltages = currentVoltages;
                    }
                }

                int currentResult = solve(lights, buttons);
                result += currentResult == -1 ? 0 : currentResult;
            }

        } catch (IOException ex) {
            System.out.println("Some error happened");
        } finally {
            System.out.println(String.format("Result: %d", result));
        }

    }

    public static int solve(int[] targetLights, ArrayList<ArrayList<Integer>> buttons) {
        int numLights = targetLights.length;
        int numButtons = buttons.size();

        int[][] grid = new int[numLights][numButtons + 1];
        for (int i = 0; i < numButtons; i++) {
            ArrayList<Integer> toggles = buttons.get(i);
            for (int lightIndex : toggles) {
                grid[lightIndex][i] = 1;
            }
        }

        for (int i = 0; i < numLights; i++) {
            grid[i][numButtons] = targetLights[i];
        }

        int currentRow = 0;
        int[] pivotMapping = new int[numLights];
        for (int i = 0; i < numLights; i++) {
            pivotMapping[i] = -1;
        }

        for (int col = 0; col < numButtons && currentRow < numLights; col++) {
            int swapRow = currentRow;
            while (swapRow < numLights && grid[swapRow][col] == 0) {
                swapRow++;
            }

            if (swapRow == numLights) {
                continue;
            }

            int[] temp = grid[swapRow];
            grid[swapRow] = grid[currentRow];
            grid[currentRow] = temp;

            pivotMapping[currentRow] = col;

            for (int row = 0; row < numLights; row++) {
                if (row != currentRow && grid[row][col] == 1) {
                    for (int k = col; k <= numButtons; k++) {
                        grid[row][k] = grid[row][k] ^ grid[currentRow][k];
                    }
                }
            }
            currentRow++;
        }

        for (int i = currentRow; i < numLights; i++) {
            if (grid[i][numButtons] == 1) {
                return -1;
            }
        }

        ArrayList<Integer> freeButtons = new ArrayList<>();
        boolean[] isPivot = new boolean[numButtons];
        for (int p : pivotMapping) {
            if (p != -1) {
                isPivot[p] = true;
            }
        }
        for (int i = 0; i < numButtons; i++) {
            if (!isPivot[i]) {
                freeButtons.add(i);
            }
        }

        return findMin(0, new int[numButtons], freeButtons, grid, pivotMapping, currentRow);
    }

    private static int findMin(int index, int[] presses, ArrayList<Integer> freeButtons, int[][] grid, int[] pivotMapping, int totalRows) {
        if (index == freeButtons.size()) {
            int count = 0;
            int[] finalPresses = presses.clone();

            for (int i = 0; i < totalRows; i++) {
                int pCol = pivotMapping[i];
                int requiredState = grid[i][grid[0].length - 1];

                for (int fCol : freeButtons) {
                    if (grid[i][fCol] == 1 && finalPresses[fCol] == 1) {
                        requiredState ^= 1;
                    }
                }
                finalPresses[pCol] = requiredState;
            }

            for (int p : finalPresses) {
                if (p == 1) {
                    count++;
                }
            }
            return count;
        }

        presses[freeButtons.get(index)] = 0;
        int opt1 = findMin(index + 1, presses, freeButtons, grid, pivotMapping, totalRows);

        presses[freeButtons.get(index)] = 1;
        int opt2 = findMin(index + 1, presses, freeButtons, grid, pivotMapping, totalRows);

        return Math.min(opt1, opt2);
    }
}
