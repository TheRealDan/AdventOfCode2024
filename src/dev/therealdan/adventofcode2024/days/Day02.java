package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day02 {

    public Day02(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        int part2 = part2(file);
        System.out.println("Day 2: Part 1: " + part1 + " Part 2: " + part2);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        int safeReports = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            List<Integer> report = Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            if (isSafe(report))
                safeReports++;
        }

        return safeReports;
    }

    public int part2(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        int safeReports = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            List<Integer> report = Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            if (isSafe(report)) {
                safeReports++;
            } else {
                for (int i = 0; i < report.size(); i++) {
                    List<Integer> problemDampener = new ArrayList<>(report);
                    problemDampener.remove(i);
                    if (isSafe(problemDampener)) {
                        safeReports++;
                        break;
                    }
                }
            }
        }

        return safeReports;
    }

    public boolean isSafe(List<Integer> report) {
        int change = 0;
        int previous = report.get(0);
        for (int level : report.stream().skip(1).collect(Collectors.toList())) {
            int difference = Math.abs(level - previous);
            if (difference < 1 || difference > 3) return false;
            if (change == 0) change = level > previous ? 1 : -1;
            if (change != (level > previous ? 1 : -1)) return false;
            previous = level;
        }
        return true;
    }
}