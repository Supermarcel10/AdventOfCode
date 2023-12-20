package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


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

			System.out.println("Total Cards: " + countTotalScratchCards(createScratchCardWinningMap(linesArray)));
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
	}

	public static int getPoints(String[] scratchCards) {
		int sum = 0;

		for (String scratchCard : scratchCards) {
			String[] cleanScratchCard = scratchCard.split(": ")[1].split(" \\| ");

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

	public static Map<Integer, HashSet<Integer>> createScratchCardWinningMap(String[] scratchCards) {
		Map<Integer, HashSet<Integer>> cardWinningMap = new HashMap<>();
		int cardCounter = 1;

		for (String scratchCard : scratchCards) {
			String[] cleanScratchCard = scratchCard.split(": ")[1].split(" \\| ");

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

			cardWinningMap.put(cardCounter++, Arrays.stream(winningNumbersArray).filter(Arrays.asList(scratchNumbersArray)::contains).collect(Collectors.toCollection(HashSet::new)));
		}

		return cardWinningMap;
	}

	public static int countTotalScratchCards(Map<Integer, HashSet<Integer>> cardWinningMap) {
		Map<Integer, Integer> cardCounts = new HashMap<>();

		for (Integer cardNumber : cardWinningMap.keySet()) {
			cardCounts.put(cardNumber, 1);
		}

		for (Map.Entry<Integer, HashSet<Integer>> entry : cardWinningMap.entrySet()) {
			int cardNumber = entry.getKey();
			HashSet<Integer> matches = entry.getValue();
			int currentCardCount = cardCounts.get(cardNumber);

			for (int i = 1; i <= matches.size(); i++) {
				int nextCardNumber = cardNumber + i;
				if (cardWinningMap.containsKey(nextCardNumber)) {
					cardCounts.put(nextCardNumber, cardCounts.getOrDefault(nextCardNumber, 0) + currentCardCount);
				}
			}
		}

		return cardCounts.values().stream().mapToInt(Integer::intValue).sum();
	}
}
