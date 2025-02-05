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

    public void readStats(String username) {
        File file = new File(statsFile);
        if (!file.exists()) {
            System.out.println("No stats available.");
            return;
        }

        int totalAttempts = 0;
        int gamesPlayed = 0;
        int gamesWon = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(username)) {
                    gamesPlayed++;
                    totalAttempts += Integer.parseInt(parts[2]);
                    if (parts[3].equals("win")) {
                        gamesWon++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading stats: " + e.getMessage());
            return;
        }

        if (gamesPlayed == 0) {
            System.out.println("No stats available for " + username + ".");
            return;
        }

        double averageAttempts = (double) totalAttempts / gamesPlayed;

        System.out.println("Stats for " + username + ":");
        System.out.println("Games played: " + gamesPlayed);
        System.out.println("Games won: " + gamesWon);
        System.out.printf("Average attempts per game: %.1f", averageAttempts);
        System.out.println("Press Enter to exit...");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
