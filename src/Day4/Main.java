package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
			File file = new File("src/Day4/input.txt");
			Scanner scanner = new Scanner(file);
			ArrayList<String> lines = new ArrayList<>();

			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}

			scanner.close();

			String[] linesArray = lines.toArray(new String[0]);
			System.out.println("Total Points: " + getPoints(linesArray));
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
	}

	public static int getPoints(String[] scratchCards) {
		int sum = 0;

		for (String scratchCard : scratchCards) {
			String cleanScratchCard[] = scratchCard.split(": ")[1].split(" \\| ");

			Integer[] winningNumbersArray = Arrays.stream(cleanScratchCard[0].split("\\s+"))
					.filter(s -> !s.isEmpty())
					.distinct()
					.map(Integer::parseInt)
					.toArray(Integer[]::new);

			Integer[] scratchNumbersArray = Arrays.stream(cleanScratchCard[1].split("\\s+"))
					.filter(s -> !s.isEmpty())
					.distinct()
					.map(Integer::parseInt)
					.toArray(Integer[]::new);

			long count = Arrays.stream(winningNumbersArray)
					.filter(Arrays.asList(scratchNumbersArray)::contains)
					.count();

			sum += count > 0 ? (int) Math.pow(2, count - 1) : 0;
		}

		return sum;
	}
}
