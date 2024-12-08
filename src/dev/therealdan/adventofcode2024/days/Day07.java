package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day07 {

    public Day07(String path) throws FileNotFoundException {
        File file = new File(path);
        long part1 = part1(file);
        System.out.println("Day 7: Part 1: " + part1);
    }

    public long part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        long totalCalibrationResult = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Calibration calibration = new Calibration(Long.parseLong(line.split(":")[0]), Arrays.asList(line.split(": ")[1].split(" ")));

            if (solve(calibration.testValue, line.split(": ")[1])) {
                totalCalibrationResult += calibration.testValue;
            }
        }

        return totalCalibrationResult;
    }

    public boolean solve(long testValue, String input) {
        if (input.contains(" ")) {
            if (solve(testValue, input.replaceFirst(" ", "X+"))) return true;
            if (solve(testValue, input.replaceFirst(" ", "X*"))) return true;
        } else {
            double value = Long.parseLong(input.split("X")[0]);
            for (String number : Arrays.stream(input.split("X")).skip(1).collect(Collectors.toList())) {
                if (number.startsWith("+")) {
                    value += Long.parseLong(number.substring(1));
                } else if (number.startsWith("*")) {
                    value *= Long.parseLong(number.substring(1));
                }
            }
            return value == testValue;
        }
        return false;
    }

    public class Calibration {
        public long testValue;
        public List<String> numbers;

        public Calibration(long testValue, List<String> numbers) {
            this.testValue = testValue;
            this.numbers = numbers;
        }
    }
}
