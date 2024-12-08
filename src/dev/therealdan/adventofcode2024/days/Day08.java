package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day08 {

    public Day08(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        System.out.println("Day 8: Part 1: " + part1);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        int width = 0, height = 0;
        List<Antenna> antennas = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            width = 0;
            for (String letter : line.split("")) {
                if (!letter.equals(".")) antennas.add(new Antenna(width, height, letter));
                width++;
            }
            height++;
        }

        List<Antinode> antinodes = new ArrayList<>();
        for (Antenna antenna : antennas) {
            nextAntenna:
            for (Antenna otherAntenna : antennas) {
                if (antenna.equals(otherAntenna)) continue;
                if (!antenna.frequency.equals(otherAntenna.frequency)) continue;
                int xDistance = Math.abs(antenna.x - otherAntenna.x);
                int yDistance = Math.abs(antenna.y - otherAntenna.y);
                Antinode antinode = new Antinode(antenna.x + (otherAntenna.x > antenna.x ? -xDistance : xDistance), antenna.y + (otherAntenna.y > antenna.y ? -yDistance : yDistance));
                if (antinode.x >= width || antinode.y >= height || antinode.x < 0 || antinode.y < 0) continue;
                for (Antinode existingAntinode : antinodes)
                    if (antinode.x == existingAntinode.x && antinode.y == existingAntinode.y)
                        continue nextAntenna;
                antinodes.add(antinode);
            }
        }

        return antinodes.size();
    }

    public class Antenna {
        public int x;
        public int y;
        public String frequency;

        public Antenna(int x, int y, String frequency) {
            this.x = x;
            this.y = y;
            this.frequency = frequency;
        }
    }

    public class Antinode {
        public int x;
        public int y;

        public Antinode(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
