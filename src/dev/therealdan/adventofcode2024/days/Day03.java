package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day03 {

    public Day03(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        int part2 = part2(file);
        System.out.println("Day 3: Part 1: " + part1 + " Part 2: " + part2);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        int results = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            for (String a : line.split("mul\\(")) {
                if (a.contains(")")) {
                    String b = a.endsWith(")") && Arrays.stream(a.split("")).filter(c -> c.equals(")")).count() == 1 ? a.substring(0, a.length()-1) : a.split("\\)")[0];
                    try {
                        int result = Integer.parseInt(b.split(",")[0]) * Integer.parseInt(b.split(",")[1]);
                        results += result;
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }

        return results;
    }

    public int part2(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        int results = 0;
        boolean mulInstructionsEnabled = true;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            for (String a : line.split("mul\\(")) {
                if (a.contains(")") && mulInstructionsEnabled) {
                    String b = a.endsWith(")") && Arrays.stream(a.split("")).filter(c -> c.equals(")")).count() == 1 ? a.substring(0, a.length()-1) : a.split("\\)")[0];
                    try {
                        int result = Integer.parseInt(b.split(",")[0]) * Integer.parseInt(b.split(",")[1]);
                        results += result;
                    } catch (NumberFormatException e) {
                    }
                }
                if (a.contains("do()")) mulInstructionsEnabled = true;
                if (a.contains("don't()")) mulInstructionsEnabled = false;
            }
        }

        return results;
    }
}
