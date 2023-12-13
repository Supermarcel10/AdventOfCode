package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
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
}
