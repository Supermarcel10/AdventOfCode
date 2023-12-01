package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("C:\\Users\\Marcel\\IdeaProjects\\AdventOfCode\\src\\input.txt");
		Scanner reader = new Scanner(file);

		Pattern p = Pattern.compile("(one|two|three|four|five|six|seven|eight|nine|\\d)");

		int total = 0;
		StringBuilder number = new StringBuilder();
		while (reader.hasNextLine()) {
			String data = reader.nextLine();
			Matcher m = p.matcher(data);

			int start = 0;
			while (m.find(start)) {
				number.append(m.group().length() == 1 ? m.group() : getNumber(m.group()));
				start = m.start() + 1;
			}

			char[] temp = number.toString().toCharArray();
			total += Integer.parseInt(String.valueOf(temp[0]) + temp[temp.length - 1]);

			number.setLength(0);
		}

		System.out.println(total);
	}

	private static Integer getNumber(String number) {
		return switch (number) {
			case "one" -> 1;
			case "two" -> 2;
			case "three" -> 3;
			case "four" -> 4;
			case "five" -> 5;
			case "six" -> 6;
			case "seven" -> 7;
			case "eight" -> 8;
			case "nine" -> 9;
			default -> null;
		};
	}
}
