package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static long linearSearchTime;
    private static long sortingTime;
    private static long jumpSearchTime;
    public static void main(String[] args) {
        String pathToFile = "/Users/i337469/Downloads/find.txt";
        String pathSearchFile = "/Users/i337469/Downloads/directory.txt";

        List<String> find = readFileToList(pathToFile);
        List<String> directory = readFileToList(pathSearchFile);

        System.out.println("Start searching (linear search)...");
        long foundLinear = linearSearch(directory, find);
        System.out.printf("Found %d / %d entries. Time taken: %s%n%n", foundLinear, find.size(), calculateTimeTaken(linearSearchTime));

        System.out.println("Start searching (bubble sort + jump search)...");
        List<String> sortedDirectory = bubbleSortList(directory);
        if (!sortedDirectory.isEmpty()) {
            long foundJump = jumpSearchList(sortedDirectory, find);
            System.out.printf("Found %d / %d entries. Time taken: %s%n", foundJump, find.size(), calculateTimeTaken(jumpSearchTime + sortingTime));
            System.out.printf("Sorting time: %s%n", calculateTimeTaken(sortingTime));
            System.out.printf("Searching time: %s%n", calculateTimeTaken(jumpSearchTime));
        } else {
            foundLinear = linearSearch(directory, find);
            System.out.printf("Found %d / %d entries. Time taken: %s%n", foundLinear, find.size(), calculateTimeTaken(linearSearchTime + sortingTime));
            System.out.printf("Sorting time: %s - STOPPED, moved to linear search%n", calculateTimeTaken(sortingTime));
            System.out.printf("Searching time: %s%n", calculateTimeTaken(linearSearchTime));
        }
    }

    private static String calculateTimeTaken(long timeDifference) {
        long minutes = (timeDifference / 1000) / 60;
        long seconds = (timeDifference / 1000) % 60;
        long milliseconds = timeDifference - ((minutes * 60000) + (seconds * 1000));
        return minutes + " min. " + seconds + " sec. " + milliseconds + "ms.";
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

    private static long linearSearch(List<String> directory, List<String> find) {

        long timeStart = System.currentTimeMillis();
        long found = 0;
        for (String toFind : find) {
            for (String entry : directory) {
                if (entry.strip().split("\\s+", 2)[1].equals(toFind)) {
                    found++;
                    break;
                }
            }
        }
        long timeEnd = System.currentTimeMillis();
        linearSearchTime = timeEnd - timeStart;
        return found;
    }

    private static long jumpSearchList(List<String> directory, List<String> find) {
        long jumpTimeStart = System.currentTimeMillis();
        long found = 0;


        for (String toFind : find) {
            long result = jumpSearchElement(directory, toFind);
            if (result == 1) {
                found++;
            }
        }
        long jumpTimeStop = System.currentTimeMillis();
        jumpSearchTime = jumpTimeStop - jumpTimeStart;
        return found;
    }

    private static List<String> bubbleSortList(List<String> directory) {
        long sortingTimeStart = System.currentTimeMillis();
        List<String> list = new ArrayList<>(directory);
        int n = list.size();
        String temp;
        for (int i = 0; i < n; i++) {
            boolean swapped = false;
            for (int j = 1; j < (n - i); j++) {
                long tempTimeDifference = System.currentTimeMillis() - sortingTimeStart;
                if (tempTimeDifference > (10 * linearSearchTime)) {
                    sortingTime = tempTimeDifference;
                    return new ArrayList<>();
                }
                if (list.get(j - 1).split("\\s+", 2)[1].compareTo(list.get(j).split("\\s+", 2)[1]) > 0) {
                    temp =  list.get(j - 1);
                    list.set(j - 1, list.get(j));
                    list.set(j, temp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        /*list.sort(Comparator.comparing(a -> a.split("\\s+", 2)[1]));*/
        long sortingTimeStop = System.currentTimeMillis();
        sortingTime = sortingTimeStop - sortingTimeStart;
        return list;
    }
    
    private static long jumpSearchElement(List<String> list, String element) {
        int listSize = list.size();
        int jumpBlock = (int) Math.floor(Math.sqrt(listSize));
        if (element.compareTo(list.get(listSize - 1).split("\\s+", 2)[1]) > 0 ||
                element.compareTo(list.get(0).split("\\s+", 2)[1]) < 0) {
            return -1;
        }
        int startPosition = 0;
        int currentPosition = jumpBlock;
        while (list.get(currentPosition).split("\\s+", 2)[1].compareTo(element) < 0) {
            startPosition = currentPosition;
            currentPosition += jumpBlock;
            if (currentPosition > listSize - 1) {
                currentPosition = listSize - 1;
            }
        }
        for (int k = startPosition; k <= currentPosition; k++) {
            if (list.get(k).split("\\s+", 2)[1].compareTo(element) == 0) {
                return 1;
            }
        }
        return -1;
    }
}
