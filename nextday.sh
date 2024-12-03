echo 'package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day'$1' {

    public Day'$1'(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        System.out.println("Day '$1': Part 1: " + part1);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
        }

        return 0;
    }
}' >"src/dev/therealdan/adventofcode2024/days/Day$1.java"

echo '' >data/day$1.txt
echo '' >data/day$1example.txt