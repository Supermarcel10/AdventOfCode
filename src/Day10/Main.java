package Day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("src/Day5/input.txt");
            Scanner scanner = new Scanner(file);
            ArrayList<String> lines = new ArrayList<>();

            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            scanner.close();

            // TODO: Implement solution here
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}
