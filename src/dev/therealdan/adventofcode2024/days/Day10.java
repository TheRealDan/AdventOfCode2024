package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day10 {

    public Day10(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        int part2 = part2(file);
        System.out.println("Day 10: Part 1: " + part1 + " Part 2: " + part2);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        List<Position> positions = new ArrayList<>();
        int y = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            int x = 0;
            for (String number : line.split("")) {
                positions.add(new Position(x, y, Integer.parseInt(number)));
                x++;
            }
            y++;
        }

        int totalTrailheads = 0;
        for (Position position : positions) {
            if (position.height != 0) continue;
            List<Position> trailheads = searchTrailheads(positions, position);
            totalTrailheads += trailheads.size();
        }

        return totalTrailheads;
    }

    public int part2(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        List<Position> positions = new ArrayList<>();
        int y = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            int x = 0;
            for (String number : line.split("")) {
                positions.add(new Position(x, y, Integer.parseInt(number)));
                x++;
            }
            y++;
        }

        int totalTrailheadRating = 0;
        for (Position position : positions) {
            if (position.height != 0) continue;
            List<Position> trailheads = searchRatings(positions, position);
            totalTrailheadRating += trailheads.size();
        }

        return totalTrailheadRating;
    }

    public List<Position> searchTrailheads(List<Position> positions, Position currentPosition) {
        List<Position> trailheads = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Position nextPosition = currentPosition.getPosition(positions, direction);
            if (nextPosition == null) continue;
            if (currentPosition.height + 1 == nextPosition.height) {
                if (nextPosition.height == 9) {
                    trailheads.add(nextPosition);
                } else {
                    for (Position position : searchTrailheads(positions, nextPosition)) {
                        if (trailheads.stream().noneMatch(trailhead -> trailhead.x == position.x && trailhead.y == position.y)) {
                            trailheads.add(position);
                        }
                    }
                }
            }
        }
        return trailheads;
    }

    public List<Position> searchRatings(List<Position> positions, Position currentPosition) {
        List<Position> trailheads = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Position nextPosition = currentPosition.getPosition(positions, direction);
            if (nextPosition == null) continue;
            if (currentPosition.height + 1 == nextPosition.height) {
                if (nextPosition.height == 9) {
                    trailheads.add(nextPosition);
                } else {
                    trailheads.addAll(searchRatings(positions, nextPosition));
                }
            }
        }
        return trailheads;
    }

    public class Position {
        public int x;
        public int y;
        public int height;

        public Position(int x, int y, int height) {
            this.x = x;
            this.y = y;
            this.height = height;
        }

        public Position getPosition(List<Position> positions, Direction direction) {
            int x = this.x + (direction.equals(Direction.LEFT) ? -1 : direction.equals(Direction.RIGHT) ? 1 : 0);
            int y = this.y + (direction.equals(Direction.UP) ? -1 : direction.equals(Direction.DOWN) ? 1 : 0);
            for (Position position : positions)
                if (position.x == x && position.y == y)
                    return position;
            return null;
        }
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
