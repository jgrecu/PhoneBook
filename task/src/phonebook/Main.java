package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String pathToFile = "/Users/i337469/Downloads/find.txt";
        String pathSearchFile = "/Users/i337469/Downloads/directory.txt";
        /*File file = new File(pathToFile);
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
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.printf("File not found: %s", pathSearchFile);
                }
            }
            long timeEnd = System.currentTimeMillis();

            System.out.printf("Found %d / %d entries. %s%n", foundCounter, counter, calculateTimeTaken(timeStart, timeEnd));
        } catch (FileNotFoundException e) {
            System.out.printf("File not found: %s", pathToFile);
        }*/

        List<String> find = readFileToList(pathToFile);
        List<String> directory = readFileToList(pathSearchFile);

        linearSearch(directory, find);

        System.out.println();
    }

    private static String calculateTimeTaken(long start, long end) {
        long timeDifference = end - start;
        long minutes = (timeDifference / 1000) / 60;
        long seconds = (timeDifference / 1000) % 60;
        long milliseconds = timeDifference - ((minutes * 60000) + (seconds * 1000));
        return "Time taken: " + minutes + " min. " + seconds + " sec. " + milliseconds + "ms.";
    }

    private static List<String> readFileToList(String path) {
        List<String> list = new ArrayList<>();
        File file = new File(path);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String temp = scanner.nextLine().strip();
                list.add(temp);
            }
        } catch (FileNotFoundException e) {
            System.out.printf("File not found: %s", path);
        }
        return list;
    }

    private static void linearSearch(List<String> directory, List<String> find) {
        System.out.println("Start searching (linear search)...");
        long timeStart = System.currentTimeMillis();
        int found = 0;
        for (String toFind : find) {
            for (String entry : directory) {
                if (entry.strip().split("\\s+", 2)[1].equals(toFind)) {
                    found++;
                    break;
                }
            }
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Found " + found + " / " + find.size() + " entries. " + calculateTimeTaken(timeStart, timeEnd));
    }
}
