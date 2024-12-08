package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day06 {

    public Day06(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        int part2 = part2(file);
        System.out.println("Day 6: Part 1: " + part1 + " Part 2: " + part2);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        List<Square> squares = new ArrayList<>();
        Guard guard = null;
        int x = 0, y = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            for (String letter : line.split("")) {
                squares.add(new Square(x, y, letter.equals("#")));
                if (letter.equals("^")) guard = new Guard(x, y);
                x++;
            }
            x = 0;
            y++;
        }

        while (getSquare(squares, guard.x, guard.y) != null) {
            Square currentSquare = getSquare(squares, guard.x, guard.y);
            currentSquare.setVisited();
            Square nextSquare = guard.getNextSquare(squares);
            if (nextSquare == null) break;
            if (nextSquare.obstacle) {
                guard.rotate();
                continue;
            }
            guard.setPosition(nextSquare);
        }

        int visited = 0;
        for (Square square : squares)
            if (square.visited)
                visited++;

        return visited;
    }

    public int part2(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        List<Square> squares = new ArrayList<>();
        Guard guard = null;
        int x = 0, y = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            for (String letter : line.split("")) {
                squares.add(new Square(x, y, letter.equals("#")));
                if (letter.equals("^")) guard = new Guard(x, y);
                x++;
            }
            x = 0;
            y++;
        }
        Square start = new Square(guard.x, guard.y, false);

        while (getSquare(squares, guard.x, guard.y) != null) {
            Square currentSquare = getSquare(squares, guard.x, guard.y);
            currentSquare.setVisited();
            Square nextSquare = guard.getNextSquare(squares);
            if (nextSquare == null) break;
            if (nextSquare.obstacle) {
                guard.rotate();
                continue;
            }
            guard.setPosition(nextSquare);
        }

        int possibleObstructions = 0;
        for (Square square : squares) {
            if (!square.visited) continue;
            if (square.is(start)) continue;
            Square rock = new Square(square.x, square.y, true);
            guard.reset(start);
            guard.clearHistory();
            while (true) {
                if (guard.dejavu()) {
                    possibleObstructions++;
                    break;
                }
                Square nextSquare = guard.getNextSquare(squares);
                if (nextSquare == null) break;
                if (nextSquare.obstacle || nextSquare.is(rock)) {
                    guard.rotate();
                    continue;
                }
                guard.addHistory();
                guard.setPosition(nextSquare);
            }
        }

        return possibleObstructions;
    }

    public Square getSquare(List<Square> squares, int x, int y) {
        for (Square square : squares)
            if (square.x == x && square.y == y)
                return square;
        return null;
    }

    public enum Direction {
        UP, RIGHT, DOWN, LEFT;

        public Direction next() {
            return equals(Direction.values()[Direction.values().length - 1]) ? Direction.values()[0] : Direction.values()[this.ordinal() + 1];
        }
    }

    public class Guard {
        public int x;
        public int y;
        public Direction direction;
        public List<Guard> history = new ArrayList<>();

        public Guard(int x, int y) {
            this(x, y, Direction.UP);
        }

        public Guard(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public void reset(Square square) {
            setPosition(square);
            this.direction = Direction.UP;
        }

        public void setPosition(Square square) {
            this.x = square.x;
            this.y = square.y;
        }

        public void addHistory() {
            this.history.add(new Guard(x, y, direction));
        }

        public void clearHistory() {
            this.history.clear();
        }

        public Square getNextSquare(List<Square> squares) {
            switch (direction) {
                case UP:
                    return getSquare(squares, x, y - 1);
                case DOWN:
                    return getSquare(squares, x, y + 1);
                case LEFT:
                    return getSquare(squares, x - 1, y);
                case RIGHT:
                    return getSquare(squares, x + 1, y);
            }
            return null;
        }

        public void rotate() {
            direction = direction.next();
        }

        public boolean dejavu() {
            for (Guard past : history)
                if (past.x == x && past.y == y && past.direction.equals(direction))
                    return true;
            return false;
        }
    }

    public class Square {
        public int x;
        public int y;
        public boolean obstacle;
        public boolean visited;

        public Square(int x, int y, boolean obstacle) {
            this.x = x;
            this.y = y;
            this.obstacle = obstacle;
        }

        public void setVisited() {
            visited = true;
        }

        public boolean is(Square square) {
            return x == square.x && y == square.y;
        }
    }
}