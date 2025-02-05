package io;

import java.io.*;

public class StatsManager {
    private final String statsFile = "stats.csv";

    public void writeStats(String username, String secretWord, int attempts, boolean win) {
        try (FileWriter fw = new FileWriter(statsFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            String result = win ? "win" : "loss";
            out.println(username + "," + secretWord + "," + attempts + "," + result);
        } catch (IOException e) {
            System.out.println("Error writing stats: " + e.getMessage());
        }
    }

    public void readStats() {
        File file = new File(statsFile);
        if (!file.exists()) {
            System.out.println("No stats available.");
            return;
        }
        System.out.println("\nPast Game Stats:");
        int totalAttempts = 0;
        int count = 0;
        int wins = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                // Each line is expected to have: username,secretWord,attempts,win/loss
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    try {
                        totalAttempts += Integer.parseInt(parts[2]);
                        count++;
                    } catch (NumberFormatException e) {
                        // Skip invalid attempt values.
                    }
                    if ("win".equalsIgnoreCase(parts[3].trim())) {
                        wins++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading stats file: " + e.getMessage());
        }
        
        System.out.println("Games played: %d" + count);
        
        if (count > 0) {
            double average = (double) totalAttempts / count;
            System.out.println("Average attempts per game: " + average);
        }
        System.out.println("Games won: " + wins);
    }
}
