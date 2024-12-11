package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day11 {

    public Day11(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        System.out.println("Day 11: Part 1: " + part1);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        String line = scanner.nextLine();
        List<String> stones = Arrays.stream(line.split(" ")).collect(Collectors.toList());

        for (int i = 0; i < 25; i++)
            stones = blink(stones);

        return stones.size();
    }

    public List<String> blink(List<String> oldStones) {
        List<String> newStones = new ArrayList<>();
        for (String stone : oldStones) {
            if (stone.equals("0")) {
                newStones.add("1");
            } else if (stone.length() % 2 == 0) {
                newStones.add(stone.substring(0, stone.length() / 2));
                stone = stone.substring(stone.length() / 2);
                while (stone.startsWith("0") && stone.length() > 1) stone = stone.substring(1);
                newStones.add(stone);
            } else {
                newStones.add(Long.toString(Long.parseLong(stone) * 2024));
            }
        }
        return newStones;
    }
}
