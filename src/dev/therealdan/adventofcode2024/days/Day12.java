package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day12 {

    public Day12(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        System.out.println("Day 12: Part 1: " + part1);
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

    public class GardenPlot {
        public String plant;
        public int x;
        public int y;

        public GardenPlot(String plant, int x, int y) {
            this.plant = plant;
            this.x = x;
            this.y = y;
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
