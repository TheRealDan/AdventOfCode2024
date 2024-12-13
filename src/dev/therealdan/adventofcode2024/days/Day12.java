package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day12 {

    public Day12(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        int part2 = part2(file);
        System.out.println("Day 12: Part 1: " + part1 + " Part 2: " + part2);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        HashMap<String, GardenPlot> gardenPlots = new HashMap<>();
        int y = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            int x = 0;
            for (String plant : line.split("")) {
                gardenPlots.put(x + "-" + y, new GardenPlot(plant, x, y));
                x++;
            }
            y++;
        }

        int totalPrice = 0;
        HashSet<GardenPlot> checked = new HashSet<>();
        for (GardenPlot gardenPlot : gardenPlots.values()) {
            if (checked.contains(gardenPlot)) continue;

            int area = 0;
            int perimeter = 0;
            List<GardenPlot> toCheck = new ArrayList<>();
            toCheck.add(gardenPlot);
            while (toCheck.size() > 0) {
                GardenPlot plot = toCheck.get(0);
                area++;
                toCheck.remove(plot);
                checked.add(plot);
                for (Direction direction : Direction.values()) {
                    GardenPlot adjacentPlot = plot.getAdjacentGardenPlot(gardenPlots, direction);
                    if (adjacentPlot != null && plot.plant.equals(adjacentPlot.plant) && !checked.contains(adjacentPlot) && !toCheck.contains(adjacentPlot)) {
                        toCheck.add(adjacentPlot);
                    } else if (adjacentPlot == null || !plot.plant.equals(adjacentPlot.plant)) {
                        perimeter++;
                    }
                }
            }

            totalPrice += area * perimeter;
        }

        return totalPrice;
    }

    public int part2(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        HashMap<String, GardenPlot> gardenPlots = new HashMap<>();
        int y = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            int x = 0;
            for (String plant : line.split("")) {
                gardenPlots.put(x + "-" + y, new GardenPlot(plant, x, y));
                x++;
            }
            y++;
        }

        List<Garden> gardens = new ArrayList<>();
        nextGardenPlot:
        for (GardenPlot gardenPlot : gardenPlots.values()) {
            for (Garden garden : gardens)
                if (garden.contains(gardenPlot))
                    continue nextGardenPlot;
            gardens.add(new Garden(gardenPlots, gardenPlot));
        }

        int totalPrice = 0;
        for (Garden garden : gardens)
            totalPrice += garden.gardenPlots.size() * garden.getSides();

        return totalPrice;
    }

    public class Garden {
        public HashMap<String, GardenPlot> gardenPlots = new HashMap<>();
        public int minX = Integer.MAX_VALUE, maxX = 0, minY = Integer.MAX_VALUE, maxY = 0;

        public Garden(HashMap<String, GardenPlot> gardenPlots, GardenPlot start) {
            addPlots(gardenPlots, start);
        }

        public void addPlots(HashMap<String, GardenPlot> gardenPlots, GardenPlot gardenPlot) {
            add(gardenPlot);
            for (Direction direction : Direction.values()) {
                GardenPlot adjacentGardenPlot = gardenPlot.getAdjacentGardenPlot(gardenPlots, direction);
                if (adjacentGardenPlot == null) continue;
                if (this.gardenPlots.containsValue(adjacentGardenPlot)) continue;
                if (adjacentGardenPlot.plant.equals(gardenPlot.plant))
                    addPlots(gardenPlots, adjacentGardenPlot);
            }
        }

        public void add(GardenPlot gardenPlot) {
            gardenPlots.put(gardenPlot.x + "-" + gardenPlot.y, gardenPlot);
            if (gardenPlot.x > maxX) maxX = gardenPlot.x;
            if (gardenPlot.y > maxY) maxY = gardenPlot.y;
            if (gardenPlot.x < minX) minX = gardenPlot.x;
            if (gardenPlot.y < minY) minY = gardenPlot.y;
        }

        public boolean contains(GardenPlot gardenPlot) {
            return gardenPlots.containsKey(gardenPlot.x + "-" + gardenPlot.y);
        }

        public int getSides() {
            return getHorizontalSides(true) + getHorizontalSides(false) + getVerticalSides(true) + getVerticalSides(false);
        }

        public int getHorizontalSides(boolean direction) {
            int sides = 0;
            for (int y = minY; y <= maxY; y++) {
                List<GardenPlot> side = new ArrayList<>();
                List<GardenPlot> gardenPlots = new ArrayList<>();
                next:
                for (int x = minX; x <= maxX; x++) {
                    GardenPlot gardenPlot = get(x, y);
                    if (gardenPlot == null) continue;
                    if (!gardenPlot.separatedFrom(gardenPlot.getAdjacentGardenPlot(this.gardenPlots, direction ? Direction.UP : Direction.DOWN))) continue;
                    side.add(gardenPlot);
                    for (GardenPlot each : side)
                        if (gardenPlot.isAdjacentTo(each))
                            continue next;
                    gardenPlots.add(gardenPlot);
                }
                sides += gardenPlots.size();
            }
            return sides;
        }

        public int getVerticalSides(boolean direction) {
            int sides = 0;
            for (int x = minX; x <= maxX; x++) {
                List<GardenPlot> side = new ArrayList<>();
                List<GardenPlot> gardenPlots = new ArrayList<>();
                next:
                for (int y = minY; y <= maxY; y++) {
                    GardenPlot gardenPlot = get(x, y);
                    if (gardenPlot == null) continue;
                    if (!gardenPlot.separatedFrom(gardenPlot.getAdjacentGardenPlot(this.gardenPlots, direction ? Direction.LEFT : Direction.RIGHT))) continue;
                    side.add(gardenPlot);
                    for (GardenPlot each : side)
                        if (gardenPlot.isAdjacentTo(each))
                            continue next;
                    gardenPlots.add(gardenPlot);
                }
                sides += gardenPlots.size();
            }
            return sides;
        }

        public GardenPlot get(int x, int y) {
            return gardenPlots.get(x + "-" + y);
        }
    }

    public class GardenPlot {
        public String plant;
        public int x;
        public int y;

        public GardenPlot(String plant, int x, int y) {
            this.plant = plant;
            this.x = x;
            this.y = y;
        }

        public boolean separatedFrom(GardenPlot gardenPlot) {
            if (gardenPlot == null) return true;
            return !plant.equals(gardenPlot.plant);
        }

        public boolean isAdjacentTo(GardenPlot gardenPlot) {
            return gardenPlot.x == x && Math.abs(gardenPlot.y - y) == 1 || gardenPlot.y == y && Math.abs(gardenPlot.x - x) == 1;
        }

        public GardenPlot getAdjacentGardenPlot(HashMap<String, GardenPlot> gardenPlots, Direction direction) {
            int x = this.x + (direction.equals(Direction.LEFT) ? -1 : direction.equals(Direction.RIGHT) ? 1 : 0);
            int y = this.y + (direction.equals(Direction.UP) ? -1 : direction.equals(Direction.DOWN) ? 1 : 0);
            return gardenPlots.get(x + "-" + y);
        }
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
