package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
	public static void main(String[] args) {
		try {
			File file = new File("src/Day3/input.txt");
			Scanner scanner = new Scanner(file);
			ArrayList<String> lines = new ArrayList<>();

			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}

			scanner.close();

			String[] linesArray = lines.toArray(new String[0]);
			System.out.println("Sum of part numbers: " + sumPartNumbers(linesArray));
			System.out.println("Sum of gear ratios: " + sumGearRatios(linesArray));
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
	}

	public static int sumPartNumbers(String[] schematic) {
		int sum = 0;
		String fullSchematic = String.join("\n", schematic);
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(fullSchematic);

		while (matcher.find()) {
			String numberStr = matcher.group();
			int numberStartIndex = matcher.start();

			if (isAdjacentToSymbol(schematic, numberStartIndex, numberStr.length())) {
				sum += Integer.parseInt(numberStr);
			}
		}

		return sum;
	}

	private static boolean isAdjacentToSymbol(String[] schematic, int startIndex, int length) {
		String symbols = "*#$+%@/-";

		int lineLength = schematic[0].length();
		int startRow = startIndex / (lineLength + 1);
		int startColumn = startIndex % (lineLength + 1);

		for (int i = 0; i < length; i++) {
			int column = startColumn + i;
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					int checkRow = startRow + x;
					int checkColumn = column + y;

					if (checkRow >= 0 && checkRow < schematic.length &&
							checkColumn >= 0 && checkColumn < lineLength &&
							symbols.indexOf(getCharAtPosition(schematic, checkRow, checkColumn)) != -1) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private static char getCharAtPosition(String[] schematic, int row, int column) {
		if (row < 0 || row >= schematic.length || column < 0 || column >= schematic[row].length()) {
			return ' ';
		}
		return schematic[row].charAt(column);
	}

	public static int sumGearRatios(String[] schematic) {
		int sum = 0;

		for (int row = 0; row < schematic.length; row++) {
			for (int col = 0; col < schematic[row].length(); col++) {
				if (schematic[row].charAt(col) == '*') {
					sum += lookAround(schematic, row, col);
				}
			}
		}

		return sum;
	}

	public static int lookAround(String[] schematic, int row, int col) {
		List<Integer> foundNumbers = new ArrayList<>();
		Set<String> countedNumbersWithPositions = new HashSet<>();

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) continue; // Skip itself

				int newRow = row + i;
				int newCol = col + j;

				if (newRow >= 0 && newRow < schematic.length && newCol >= 0 && newCol < schematic[0].length()) {
					char character = schematic[newRow].charAt(newCol);
					if (Character.isDigit(character)) {
						String fullNumber = findFullNumber(schematic, newRow, newCol);
						int startCol = newCol;
						while (startCol > 0 && Character.isDigit(schematic[newRow].charAt(startCol - 1))) {
							startCol--;
						}
						String uniqueIdentifier = fullNumber + "@" + newRow + "," + startCol;
						if (!countedNumbersWithPositions.contains(uniqueIdentifier)) {
							foundNumbers.add(Integer.parseInt(fullNumber));
							countedNumbersWithPositions.add(uniqueIdentifier);
						}
					}
				}
			}
		}

		return foundNumbers.size() == 2 ? foundNumbers.get(0) * foundNumbers.get(1) : 0;
	}

	public static String findFullNumber(String[] schematic, int row, int col) {
		StringBuilder number = new StringBuilder();
		int lineLength = schematic[0].length();
		int startColumn = col;

		while (startColumn > 0 && Character.isDigit(schematic[row].charAt(startColumn - 1))) {
			startColumn--;
		}

		while (startColumn < lineLength && Character.isDigit(schematic[row].charAt(startColumn))) {
			number.append(schematic[row].charAt(startColumn));
			startColumn++;
		}

		return number.toString();
	}
}
