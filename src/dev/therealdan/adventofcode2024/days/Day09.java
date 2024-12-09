package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day09 {

    public Day09(String path) throws FileNotFoundException {
        File file = new File(path);
        long part1 = part1(file);
        System.out.println("Day 9: Part 1: " + part1);
    }

    public long part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        String input = scanner.nextLine();
        List<String> disk = getDisk(input);
        disk = shiftBlocks(disk);

        long checksum = 0;
        long position = 0;
        for (String block : disk) {
            checksum += Long.parseLong(block) * position;
            position++;
        }

        return checksum;
    }

    public List<String> shiftBlocks(List<String> disk) {
        while (disk.contains(".")) {
            String block = disk.get(disk.size() - 1);
            disk.remove(disk.size() - 1);
            int index = disk.indexOf(".");
            if (index == -1) continue;
            disk.remove(index);
            disk.add(index, block);
        }
        return disk;
    }

    public List<String> getDisk(String denseFormat) {
        boolean files = true;
        long id = 0;
        List<String> disk = new ArrayList<>();
        for (String digit : denseFormat.split("")) {
            for (int i = 0; i < Long.parseLong(digit); i++)
                disk.add(files ? Long.toString(id) : ".");
            if (files) id++;
            files = !files;
        }
        return disk;
    }
}
