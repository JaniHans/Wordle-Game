package io;

import java.io.*;

public class StatsManager {

    private final String statsFile = "stats.csv";


    public void writeStats(String username, String secretWord, int attempts, boolean win) {
        // Append game results to stats.csv


        try(FileWriter fw = new FileWriter(statsFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            String result = win ? "win" : "loss";
            out.println(username + "," + secretWord + "," + attempts + "," + result);
        } catch (IOException e) {
                System.out.println("Error writing stats: " + e.getMessage());
            }
    }

    public void readStats() {
        // Read and display past game stats


        File file = new File(statsFile);
        if (!file.exists()) {
            System.out.println("No stats available.");
            return;
        }
        System.out.println("\nPast Game Stats: ");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading stats file: " + e.getMessage());
        }
    }
}
