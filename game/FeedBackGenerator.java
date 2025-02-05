package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map; 
import java.util.Set;

public class FeedBackGenerator {

    // Simple enum to track color for each letter
    private enum ColorType { GREEN, YELLOW, WHITE }

    /**
     * Determines GREEN/YELLOW/WHITE for each character in the guess.
     * Returns a list of ColorType (same length as guess).
     */
    private List<ColorType> getColorTypes(String guess, String secretWord) {
        // Convert to uppercase for consistency
        guess = guess.toUpperCase();
        secretWord = secretWord.toUpperCase();

        char[] guessChars = guess.toCharArray();
        char[] secretChars = secretWord.toCharArray();
        int length = guessChars.length;

        List<ColorType> colorTypes = new ArrayList<>(length);

        // Frequency map for letters in secret
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : secretChars) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // Initialize array with null placeholders
        for (int i = 0; i < length; i++) {
            colorTypes.add(null);
        }

        // 1) Mark GREEN first
        for (int i = 0; i < length; i++) {
            if (guessChars[i] == secretChars[i]) {
                colorTypes.set(i, ColorType.GREEN);
                freqMap.put(guessChars[i], freqMap.get(guessChars[i]) - 1);
            }
        }

        // 2) Mark YELLOW or WHITE
        for (int i = 0; i < length; i++) {
            if (colorTypes.get(i) == null) { // not green
                char g = guessChars[i];
                if (freqMap.getOrDefault(g, 0) > 0) {
                    // It's in the secret, but wrong position
                    colorTypes.set(i, ColorType.YELLOW);
                    freqMap.put(g, freqMap.get(g) - 1);
                } else {
                    // Not in secret at all
                    colorTypes.set(i, ColorType.WHITE);
                }
            }
        }

        return colorTypes;
    }

    /**
     * Generates the ANSI-colored feedback string using the ColorType list.
     * Letters are capitalized.
     */
    public String generateFeedback(String guess, String secretWord) {
        List<ColorType> colorTypes = getColorTypes(guess, secretWord);
        StringBuilder sb = new StringBuilder();

        guess = guess.toUpperCase(); // Ensure uppercase output
        for (int i = 0; i < guess.length(); i++) {
            char letter = guess.charAt(i);
            switch (colorTypes.get(i)) {
                case GREEN:
                    sb.append("\u001B[32m").append(letter).append("\u001B[0m");
                    break;
                case YELLOW:
                    sb.append("\u001B[33m").append(letter).append("\u001B[0m");
                    break;
                default: // WHITE
                    sb.append("\u001B[37m").append(letter).append("\u001B[0m");
                    break;
            }
        }

        return sb.toString(); // No extra spacing
    }

    /**
     * Removes letters ONLY if they are definitely not in the secret (i.e. WHITE).
     * This method uses getColorTypes(...) to avoid parsing the raw ANSI string.
     */
    public String getRemainingLetters(Set<String> allGuesses, String secretWord) {
        // Start with A..Z
        Set<Character> remainingLetters = new HashSet<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            remainingLetters.add(c);
        }

        // For every guess, identify which positions are WHITE
        for (String guess : allGuesses) {
            List<ColorType> colorTypes = getColorTypes(guess, secretWord);

            guess = guess.toUpperCase();
            for (int i = 0; i < guess.length(); i++) {
                if (colorTypes.get(i) == ColorType.WHITE) {
                    // Remove it from the set because we now know it is not in the secret
                    remainingLetters.remove(guess.charAt(i));
                }
            }
        }

        // Build the final string
        StringBuilder sb = new StringBuilder();
        for (char c : remainingLetters) {
            sb.append(c).append(" ");
        }

        return sb.toString().trim();
    }

    // Example usage
}
