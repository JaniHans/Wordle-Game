package io;

import java.io.*;
import java.util.*;

public class WordLoader {
    public List<String> loadWords(String filePath) {
        List<String> words = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Word list file not found: " + filePath);
            return words;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String word = line.trim();
                if (word.length() == 5) {
                    // Keep the word in its original (lowercase) form
                    words.add(word);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading word list: " + e.getMessage());
        }
        return words;
    }
    public String getWord(int index) {
        // Retrieve word at specified index
        List<String> words = loadWords("wordle-words.txt");
        if (words.isEmpty()) {
            System.out.println("No words loaded. Using default word APPLE.");
            return "APPLE";
        }
        if (index < 0 || index >= words.size()) {
            System.out.println("Invalid index. Using first word.");
            return words.get(0);
        }
        return words.get(index);
        
    }
}
