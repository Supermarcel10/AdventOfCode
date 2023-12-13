package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Main {
    private static int sum = 0;
    private static int gearSum = 0;
    private static char[][] data;
    private static final boolean[][] checkedCells = new boolean[512][];

    private static final Pattern symbolPattern = Pattern.compile("[^\\w.\\s]");
    private static final Pattern digitPattern = Pattern.compile("\\d");

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/home/marcel/IdeaProjects/AoC2023/src/Day3/input.txt");
        Scanner reader = new Scanner(file);

        // Initialize the 2D array with the correct dimensions
        int lines = 0;
        while (reader.hasNextLine()) {
            reader.nextLine();
            lines++;
        }
        data = new char[lines][];

        reader.close();
        reader = new Scanner(file);

        // Add all lines to the 2D array
        int i = 0;
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            data[i] = line.toCharArray();
            i++;
        }

        // Initialize the checkedCells array
        for (int j = 0; j < data.length; j++) {
            if (data[j] != null) {
                checkedCells[j] = new boolean[data[j].length];
            }
        }

        // Rest of your logic
        for (int x = 0; x < data.length; x++) {
            if (data[x] == null) continue;

            for (int y = 0; y < data[x].length; y++) {
                if (symbolPattern.matcher(String.valueOf(data[x][y])).matches()) {
                    // Check all adjacent cells
                    checkAdjacentCells(x, y);
                }
            }
        }

        System.out.println("Sum: " + sum + "\nGear sum: " + gearSum);
    }

    private static void checkAdjacentCells(int x, int y) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Skip the cell itself
                int adjX = x + dx;
                int adjY = y + dy;
                if (adjX >= 0 && adjY >= 0 && adjX < data.length && adjY < data[0].length && isDigit(adjX, adjY)) {
                    int[] leftPos = findLeftmost(adjX, adjY);

                    if (leftPos != null) sum += getFullNumber(leftPos[0], leftPos[1]);
                }
            }
        }

        // New logic for checking gears
        if (data[x][y] == '*') {
            checkGear(x, y);
        }
    }

    private static int[] findLeftmost(int x, int y) {
        // Start from the current position and move left to find the leftmost digit
        int startY = y;
        while (startY > 0 && isDigit(x, startY - 1)) {
            startY--;
        }

        // If the leftmost digit of the number has been checked, return
        if (checkedCells[x][startY]) return null;

        // Mark all digits of the number as checked
        int endY = startY;
        while (endY < data[x].length && isDigit(x, endY)) {
            checkedCells[x][endY] = true;
            endY++;
        }

        // Get the full number starting from the leftmost digit
        return new int[]{x, startY};
    }

    private static boolean isDigit(int x, int y) {
        if (x < 0 || x >= data.length || data[x] == null) return false;
        if (y < 0 || y >= data[x].length) return false;
        return digitPattern.matcher(Character.toString(data[x][y])).matches();
    }

    private static int getFullNumber(int x, int y) {
        // Check if the coordinates are within the bounds of the matrix
        if (x < 0 || x >= data.length || y < 0 || y >= data[0].length) {
            return 0;
        }

        if (isDigit(x, y)) {
            // Recursively get the number from the next position and build the current number
            return Integer.parseInt(Character.toString(data[x][y])) * (int) Math.pow(10, countDigits(x, y + 1)) + getFullNumber(x, y + 1);
        }

        return 0;
    }

    private static int countDigits(int x, int y) {
        if (y >= data[0].length || !digitPattern.matcher(Character.toString(data[x][y])).matches()) {
            return 0;
        }

        return 1 + countDigits(x, y + 1);
    }

    private static void checkGear(int x, int y) {
        int[] numbers = new int[2];
        int count = 0;

        for (int dx = -1; dx <= 1 && count < 2; dx++) {
            for (int dy = -1; dy <= 1 && count < 2; dy++) {
                if (dx == 0 && dy == 0) continue; // Skip the cell itself
                int adjX = x + dx;
                int adjY = y + dy;
                if (adjX >= 0 && adjY >= 0 && adjX < data.length && adjY < data[0].length && isDigit(adjX, adjY)) {
                    findLeftmost(adjX, adjY); // This also marks the number as checked
                    numbers[count++] = getFullNumber(adjX, adjY);
                }
            }
        }

        if (count == 2) {
            gearSum += numbers[0] * numbers[1];
        }
    }
}
