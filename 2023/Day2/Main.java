package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("C:\\Users\\Marcel\\IdeaProjects\\AdventOfCode\\src\\Day2\\input.txt");
		Scanner reader = new Scanner(file);

		int sumOfValid = 0;
		int sumOfMins = 0;

		int i = 3;
		while (reader.hasNextLine()) {
			String line = reader.nextLine();

			String[] idSplit = line.split(": ");
			int id = Integer.parseInt(idSplit[0].split("Game ")[1]);

			String[] games = idSplit[1].split("; ");

			int minRedGame = 0;
			int minGreenGame = 0;
			int minBlueGame = 0;

			boolean valid = true;
			for (String game : games) {
				if (!checkIfWithinLimit(game) && valid) {
					sumOfValid -= id;
					valid = false;
				}

				int[] mins = getMinimumRequired(game);
				int red = mins[0];
				int green = mins[1];
				int blue = mins[2];

				if (red > minRedGame) minRedGame = red;
				if (green > minGreenGame) minGreenGame = green;
				if (blue > minBlueGame) minBlueGame = blue;
			}

			sumOfValid += id;
			sumOfMins += minRedGame * minGreenGame * minBlueGame;
		}

		System.out.println(sumOfValid);
		System.out.println(sumOfMins);
	}

	private static boolean checkIfWithinLimit(String input) {
		String[] parts = input.split(", ");

		int red = 12;
		int green = 13;
		int blue = 14;

		for (String part : parts) {
			int num = Integer.parseInt(part.split(" ")[0]);
			String color = part.split(" ")[1];

			if (color.equals("red") && num > red
			|| color.equals("blue") && num > blue
			|| color.equals("green") && num > green) return false;
		}

		return true;
	}

	private static int[] getMinimumRequired(String input) {
		String[] parts = input.split(", ");

		int minRed = 0, minGreen = 0, minBlue = 0;

		for (String part : parts) {
			int num = Integer.parseInt(part.split(" ")[0]);
			String color = part.split(" ")[1];

			if (color.equals("red") && minRed < num) minRed = num;
			if (color.equals("blue") && minBlue < num) minBlue = num;
			if ( color.equals("green") && minGreen < num) minGreen = num;
		}

		return new int[] {minRed, minGreen, minBlue};
	}
}
