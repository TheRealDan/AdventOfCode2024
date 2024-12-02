package days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day01 {

    public Day01() throws FileNotFoundException {
        File file = new File("data/day01.txt");
        int part1 = part1(file);
        int part2 = part2(file);
        System.out.println("Day 1: Part 1: " + part1 + " Part 2: " + part2);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        List<Integer> listA = new ArrayList<>();
        List<Integer> listB = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            listA.add(Integer.parseInt(line.split("   ")[0]));
            listB.add(Integer.parseInt(line.split("   ")[1]));
        }
        Collections.sort(listA);
        Collections.sort(listB);

        int totalDistance = 0;
        for (int i = 0; i < listA.size(); i++) {
            int a = listA.get(i);
            int b = listB.get(i);
            int distance = Math.abs(a - b);
            totalDistance += distance;
        }

        return totalDistance;
    }

    public int part2(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        List<Integer> listA = new ArrayList<>();
        List<Integer> listB = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            listA.add(Integer.parseInt(line.split("   ")[0]));
            listB.add(Integer.parseInt(line.split("   ")[1]));
        }

        int totalSimilarity = 0;
        for (int a : listA) {
            long appearances = listB.stream().filter(b-> b == a).count();
            totalSimilarity += a * appearances;
        }

        return totalSimilarity;
    }
}