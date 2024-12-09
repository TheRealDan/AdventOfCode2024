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
        long part2 = part2(file);
        System.out.println("Day 9: Part 1: " + part1 + " Part 2: " + part2);
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

    public long part2(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        String input = scanner.nextLine();
        List<String> disk = getDisk(input);
        disk = shiftFiles(disk);

        long checksum = 0;
        long position = 0;
        for (String block : disk) {
            if (!block.equals("."))
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

    public List<String> shiftFiles(List<String> disk) {
        long id = Long.parseLong(disk.get(disk.size() - 1));

        while (id > 0) {
            if (disk.contains(Long.toString(id))) {
                String idString = Long.toString(id);
                long size = disk.stream().filter(block -> block.equals(idString)).count();
                long index = getFreeSpace(disk, size);
                if (index == -1 || index > disk.indexOf(idString)) {
                    id--;
                    continue;
                }
                for (long i = 0; i < size; i++) {
                    long ti = disk.indexOf(Long.toString(id));
                    disk.remove(Long.toString(id));
                    disk.add((int) ti, ".");
                }
                for (long i = 0; i < size; i++) {
                    disk.remove((int) (index + i));
                    disk.add((int) (index + i), Long.toString(id));
                }
            }
            id--;
        }

        return disk;
    }

    public long getFreeSpace(List<String> disk, long size) {
        long index = 0;
        nextIndex:
        while (true) {
            if (index + size > disk.size()) return -1;
            for (long i = 0; i < size; i++) {
                if (!disk.get((int) (index + i)).equals(".")) {
                    index++;
                    continue nextIndex;
                }
            }
            return index;
        }
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
