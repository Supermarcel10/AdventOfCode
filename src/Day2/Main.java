package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("C:\\Users\\Marcel\\IdeaProjects\\AdventOfCode\\src\\Day2\\input.txt");
		Scanner reader = new Scanner(file);

		int sum = 0;

		while (reader.hasNextLine()) {
			String line = reader.nextLine();

			String[] idSplit = line.split(": ");
			int id = Integer.parseInt(idSplit[0].split("Game ")[1]);

			String[] games = idSplit[1].split("; ");

			for (String game : games) {
				if (!checkIfWithinLimit(game)) {
					sum -= id;
					break;
				}
			}

			sum += id;
		}

		System.out.println(sum);
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
}
