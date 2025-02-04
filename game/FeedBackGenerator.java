package game;

import java.util.HashMap;
import java.util.Map;

public class FeedBackGenerator {
    public String generateFeedback(String guess, String secretWord) {
        char[] secretChars = secretWord.toCharArray();
        char[] guessChars = guess.toCharArray();
        String[] feedback = new String[5];
        boolean[] secretUsed = new boolean[5];
        Map<Character, Integer> letterCount = new HashMap<>();

        // Count occurrences of each letter in the secret word
        for (char c : secretChars) {
            letterCount.put(c, letterCount.getOrDefault(c, 0) + 1);
        }

        // First pass: Assign green (correct position)
        for (int i = 0; i < 5; i++) {
            if (guessChars[i] == secretChars[i]) {
                feedback[i] = "\u001B[32m" + guessChars[i] + "\u001B[0m";
                secretUsed[i] = true;
                letterCount.put(guessChars[i], letterCount.get(guessChars[i]) - 1);
            }
        }

        // Second pass: Assign yellow (wrong position but correct letter) or white (incorrect letter)
        for (int i = 0; i < 5; i++) {
            if (feedback[i] == null) { // If not already marked green
                char currentChar = guessChars[i];
                boolean found = false;
                
                for (int j = 0; j < 5; j++) {
                    if (!secretUsed[j] && secretChars[j] == currentChar && letterCount.get(currentChar) > 0) {
                        found = true;
                        secretUsed[j] = true;
                        letterCount.put(currentChar, letterCount.get(currentChar) - 1);
                        break;
                    }
                }
                
                if (found) {
                    feedback[i] = "\u001B[33m" + guessChars[i] + "\u001B[0m"; // Yellow
                } else {
                    feedback[i] = "\u001B[37m" + guessChars[i] + "\u001B[0m"; // White
                }
            }
        }

        // Combine feedback tokens
        return String.join(" ", feedback);
    }
}
