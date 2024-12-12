package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day11 {

    private HashMap<String, Long> cache = new HashMap<>();

    public Day11(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        long part2 = part2(file);
        System.out.println("Day 11: Part 1: " + part1 + " Part 2: " + part2);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        String line = scanner.nextLine();
        List<String> stones = Arrays.stream(line.split(" ")).collect(Collectors.toList());

        for (int i = 0; i < 25; i++)
            stones = blink(stones);

        return stones.size();
    }

    public long part2(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        String line = scanner.nextLine();

        return getStones(line, 75);
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

    public long getStones(String stones, long blinks) {
        if (cache.containsKey(stones + ";" + blinks)) return cache.get(stones + ";" + blinks);

        long total = 0;
        long remainingBlinks = blinks - 1;
        if (remainingBlinks == -1) return stones.split(" ").length;

        for (String stone : stones.split(" ")) {
            if (stone.equals("0")) {
                total += getStones("1", remainingBlinks);
            } else if (stone.length() % 2 == 0) {
                total += getStones(stone.substring(0, stone.length() / 2), remainingBlinks);
                stone = stone.substring(stone.length() / 2);
                while (stone.startsWith("0") && stone.length() > 1) stone = stone.substring(1);
                total += getStones(stone, remainingBlinks);
            } else {
                total += getStones(Long.toString(Long.parseLong(stone) * 2024), remainingBlinks);
            }
        }

        cache.put(stones + ";" + blinks, total);
        return total;
    }
}
