package dev.therealdan.adventofcode2024.days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day05 {

    public Day05(String path) throws FileNotFoundException {
        File file = new File(path);
        int part1 = part1(file);
        int part2 = part2(file);
        System.out.println("Day 5: Part 1: " + part1 + " Part 2: " + part2);
    }

    public int part1(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        List<Rule> rules = new ArrayList<>();
        int result = 0;
        nextUpdate:
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.contains("|")) {
                rules.add(new Rule(line.split("\\|")[0], line.split("\\|")[1]));
            } else if (line.contains(",")) {
                List<String> update = new ArrayList<>(Arrays.asList(line.split(",")));

                for (Rule rule : rules) {
                    if (line.contains(rule.first) && line.contains(rule.second)) {
                        if (line.indexOf(rule.first) > line.indexOf(rule.second)) {
                            continue nextUpdate;
                        }
                    }
                }

                result += Integer.parseInt(update.get(update.size() / 2));
            }
        }

        return result;
    }

    public int part2(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        List<Rule> rules = new ArrayList<>();
        int result = 0;
        nextUpdate:
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.contains("|")) {
                rules.add(new Rule(line.split("\\|")[0], line.split("\\|")[1]));
            } else if (line.contains(",")) {
                List<String> update = new ArrayList<>(Arrays.asList(line.split(",")));

                for (Rule rule : rules) {
                    if (line.contains(rule.first) && line.contains(rule.second)) {
                        if (line.indexOf(rule.first) > line.indexOf(rule.second)) {
                            sort(update, rules);
                            result += Integer.parseInt(update.get(update.size() / 2));
                            continue nextUpdate;
                        }
                    }
                }
            }
        }

        return result;
    }

    public List<String> sort(List<String> update, List<Rule> rules) {
        update.sort((a, b) -> {
            for (Rule sRule : rules) {
                if (sRule.first.equals(a) && sRule.second.equals(b)) {
                    return 1;
                } else if (sRule.first.equals(b) && sRule.second.equals(a)) {
                    return -1;
                }
            }
            return 0;
        });
        return update;
    }

    public class Rule {
        public String first;
        public String second;

        public Rule(String first, String second) {
            this.first = first;
            this.second = second;
        }
    }
}
