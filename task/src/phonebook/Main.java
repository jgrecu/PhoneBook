package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String pathToFile = "/Users/i337469/Downloads/find.txt";
        String pathSearchFile = "/Users/i337469/Downloads/directory.txt";
        File file = new File(pathToFile);
        File searchFile = new File(pathSearchFile);
        int counter = 0;
        int foundCounter = 0;
        try (Scanner scanner = new Scanner(file)) {
            System.out.println("Start searching...");
            long timeStart = System.currentTimeMillis();
            while (scanner.hasNextLine()) {
                String toFind = scanner.nextLine();
                counter++;
                try (Scanner sc = new Scanner(searchFile)) {
                    while (sc.hasNextLine()) {
                        if (sc.nextLine().strip().split("\\s+", 2)[1].equals(toFind)) {
                            foundCounter++;
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.printf("File not found: %s", pathSearchFile);
                }
            }
            long timeEnd = System.currentTimeMillis();
            long timeDifference = timeEnd - timeStart;
            long minutes = (timeDifference / 1000) / 60;
            long seconds = (timeDifference / 1000) % 60;
            long milliseconds = timeDifference - ((minutes * 60000) + ( seconds * 1000));
            System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %dms.", foundCounter, counter, minutes, seconds, milliseconds);
        } catch (FileNotFoundException e) {
            System.out.printf("File not found: %s", pathToFile);
        }
    }

}
