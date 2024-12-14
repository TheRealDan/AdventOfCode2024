package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Day13 {

    public Day13(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        System.out.println("Day 13: Part 1: " + part1);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        List<Machine> machines = new ArrayList<>();
        Button a = null, b = null;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.startsWith("Button A")) {
                a = new Button(line);
            } else if (line.startsWith("Button B")) {
                b = new Button(line);
            } else if (line.startsWith("Prize")) {
                machines.add(new Machine(machines.size(), a, b, new Prize(line)));
            }
        }

        int totalTokens = 0;
        Random random = new Random();
        for (Machine machine : machines) {
            long iterations = 0;
            int minimumTokens = 0;
            long start = System.currentTimeMillis();
            while (true) {
                iterations++;
                Claw claw = machine.getBogoClaw(random);
                if (claw.canGrab(machine.prize)) {
                    if (claw.getTokens() < minimumTokens || minimumTokens == 0) {
                        System.out.println("#" + machine.id + " Potential Claw found in " + (System.currentTimeMillis() - start) + "ms after " + iterations + " combinations");
                        minimumTokens = claw.getTokens();
                    }
                    continue;
                }
                if (System.currentTimeMillis() - start > 100) {
                    if (minimumTokens > 0) {
                        totalTokens += minimumTokens;
                        System.out.println("#" + machine.id + " " + claw.pressedA + "a " + claw.pressedB + "b " + claw.x + "x " + claw.y + "y found in " + (System.currentTimeMillis() - start) + "ms after " + iterations + " combinations");
                    } else {
                        System.out.println("#" + +machine.id + " No solution found within " + (System.currentTimeMillis() - start) + "ms, tried " + iterations + " combinations");
                    }
                    break;
                }
            }
        }

        return totalTokens;
    }

    public class Machine {
        public int id;
        public Button a, b;
        public Prize prize;

        public Machine(int id, Button a, Button b, Prize prize) {
            this.id = id;
            this.a = a;
            this.b = b;
            this.prize = prize;
        }

        public Claw getClaw(int pressA, int pressB) {
            Claw claw = new Claw(pressA * a.x + pressB * b.x, pressA * a.y + pressB * b.y);
            claw.pressedA = pressA;
            claw.pressedB = pressB;
            return claw;
        }

        public Claw getBogoClaw(Random random) {
            int pressA = random.nextInt(Math.max(prize.x / a.x, prize.y / a.y));
            int pressB = random.nextInt(Math.max(prize.x / b.x, prize.y / b.y));
            return getClaw(pressA, pressB);
        }
    }

    public class Button {
        public int x;
        public int y;

        public Button(String input) {
            this.x = Integer.parseInt(input.split("X\\+")[1].split(",")[0]);
            this.y = Integer.parseInt(input.split("Y\\+")[1]);
        }
    }

    public class Prize {
        public int x;
        public int y;

        public Prize(String input) {
            this.x = Integer.parseInt(input.split("X=")[1].split(",")[0]);
            this.y = Integer.parseInt(input.split("Y=")[1]);
        }
    }

    public class Claw {
        public int x;
        public int y;
        public int pressedA;
        public int pressedB;

        public Claw(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean canGrab(Prize prize) {
            return x == prize.x && y == prize.y;
        }

        public int getTokens() {
            return pressedA * 3 + pressedB;
        }
    }
}
