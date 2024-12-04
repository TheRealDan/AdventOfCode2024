package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day04 {

    private List<List<String>> wordSearch = new ArrayList<>();

    public Day04(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        int part2 = part2(file);
        System.out.println("Day 4: Part 1: " + part1 + " Part 2: " + part2);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            wordSearch.add(Arrays.asList(line.split("")));
        }

        int appearances = 0;
        int i = 0, j = 0;
        for (List<String> row : wordSearch) {
            for (String letter : row) {
                if (letter.equals("X")) {
                    for (Direction direction : Direction.values()) {
                        if (isXMAS(getWord(i, j, direction))) {
                            appearances++;
                        }
                    }
                }
                i++;
            }
            i = 0;
            j++;
        }

        return appearances;
    }

    public int part2(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        wordSearch = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            wordSearch.add(Arrays.asList(line.split("")));
        }

        int appearances = 0;
        int i = 0, j = 0;
        for (List<String> row : wordSearch) {
            for (String letter : row) {
                if (letter.equals("A")) {
                    if (isXMAS(i, j, "M", "M", "S", "S") ||
                            isXMAS(i, j, "S", "M", "S", "M") ||
                            isXMAS(i, j, "S", "S", "M", "M") ||
                            isXMAS(i, j, "M", "S", "M", "S")) {
                        appearances++;
                    }
                }
                i++;
            }
            i = 0;
            j++;
        }

        return appearances;
    }

    public boolean isXMAS(String input) {
        return input.equals("XMAS");
    }

    public String getWord(int i, int j, Direction direction) {
        String word = "";
        switch (direction) {
            case LEFT:
                word = getLetter(i, j) + getLetter(i - 1, j) + getLetter(i - 2, j) + getLetter(i - 3, j);
                break;
            case RIGHT:
                word = getLetter(i, j) + getLetter(i + 1, j) + getLetter(i + 2, j) + getLetter(i + 3, j);
                break;
            case UP:
                word = getLetter(i, j) + getLetter(i, j - 1) + getLetter(i, j - 2) + getLetter(i, j - 3);
                break;
            case DOWN:
                word = getLetter(i, j) + getLetter(i, j + 1) + getLetter(i, j + 2) + getLetter(i, j + 3);
                break;
            case DIAGONAL_1:
                word = getLetter(i, j) + getLetter(i - 1, j - 1) + getLetter(i - 2, j - 2) + getLetter(i - 3, j - 3);
                break;
            case DIAGONAL_2:
                word = getLetter(i, j) + getLetter(i + 1, j - 1) + getLetter(i + 2, j - 2) + getLetter(i + 3, j - 3);
                break;
            case DIAGONAL_3:
                word = getLetter(i, j) + getLetter(i - 1, j + 1) + getLetter(i - 2, j + 2) + getLetter(i - 3, j + 3);
                break;
            case DIAGONAL_4:
                word = getLetter(i, j) + getLetter(i + 1, j + 1) + getLetter(i + 2, j + 2) + getLetter(i + 3, j + 3);
                break;
        }
        return word;
    }

    public String getLetter(int i, int j) {
        if (i < 0 || j < 0 || i >= wordSearch.get(0).size() || j >= wordSearch.size()) return ".";
        return wordSearch.get(j).get(i);
    }

    public boolean isXMAS(int i, int j, String a, String b, String c, String d) {
        return (getLetter(i - 1, j - 1).equals(a) && getLetter(i + 1, j - 1).equals(b) && getLetter(i - 1, j + 1).equals(c) && getLetter(i + 1, j + 1).equals(d));
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN,
        DIAGONAL_1, DIAGONAL_2, DIAGONAL_3, DIAGONAL_4
    }
}
