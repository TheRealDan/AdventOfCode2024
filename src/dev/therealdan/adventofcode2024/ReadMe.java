package dev.therealdan.adventofcode2024;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ReadMe {

    public void generate() throws IOException {
        Scanner scanner = new Scanner(new File("leaderboard.self"));

        List<Entry> entries = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.contains("Part") || line.startsWith("Day")) continue;
            while (line.contains("  ")) line = line.replace("  ", " ");
            entries.add(new Entry(line.split(" ")[1], line.split(" ")[2], line.split(" ")[3], line.split(" ")[4], line.split(" ")[5], line.split(" ")[6], line.split(" ")[7]));
        }
        Collections.reverse(entries);

        List<String> lines = new ArrayList<>();
        scanner = new Scanner(new File("intro.md"));
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            lines.add(line);
        }

        lines.add("");
        lines.add("| Day | Title | Solution | Part 1 Time | Part 1 Rank | Part 2 Time | Part 2 Rank |");
        lines.add("| --- | ----- | -------- | ----------- | ----------- | ----------- | ----------- |");
        for (Entry entry : entries) {
            lines.add("| " + entry.day + " | [" + entry.title + "](" + entry.challengeUrl + ") | [" + entry.solutionName + "](" + entry.solution + ") | " + entry.part1Time + " | " + entry.part1Rank + " | " + entry.part2Time + " | " + entry.part2Rank + " |");
        }

        Files.write(Paths.get("readme.md"), lines, StandardCharsets.UTF_8);
    }

    public String getHTML(String url) {
        StringBuilder result = new StringBuilder();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public class Entry {
        public String day;
        public String dayPadded;
        public String part1Time;
        public String part1Rank;
        public String part1Score;
        public String part2Time;
        public String part2Rank;
        public String part2Score;
        public String challengeUrl;
        public String title;
        public String solutionName;
        public String solution;

        public Entry(String day, String part1Time, String part1Rank, String part1Score, String part2Time, String part2Rank, String part2Score) {
            this.day = day;
            this.dayPadded = (Integer.parseInt(this.day) > 9 ? this.day : "0" + this.day);

            this.part1Time = part1Time;
            this.part1Rank = part1Rank;
            this.part1Score = part1Score;
            this.part2Time = part2Time;
            this.part2Rank = part2Rank;
            this.part2Score = part2Score;

            this.challengeUrl = "https://adventofcode.com/2024/day/" + this.day;
            this.title = getHTML(this.challengeUrl).split("h2")[1].split(": ")[1].split(" ---")[0];

            this.solutionName = "Day" + this.dayPadded + ".java";
            this.solution = "src/dev/therealdan/adventofcode2024/days/Day" + this.dayPadded + ".java";
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}