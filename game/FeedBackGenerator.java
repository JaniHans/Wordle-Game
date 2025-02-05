package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FeedBackGenerator {

    public String generateFeedback(String guess, String secretWord) {
        // Convert both to uppercase for consistent feedback
        guess = guess.toUpperCase();
        secretWord = secretWord.toUpperCase();

        char[] guessChars = guess.toCharArray();
        char[] secretChars = secretWord.toCharArray();
        int length = guessChars.length;

        String[] feedback = new String[length];
        // Frequency map of letters in the secret
        Map<Character, Integer> freqMap = new HashMap<>();

        // Fill frequency map
        for (char c : secretChars) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // 1) Mark GREEN (correct positions)
        for (int i = 0; i < length; i++) {
            if (guessChars[i] == secretChars[i]) {
                feedback[i] = "\u001B[32m" + guessChars[i] + "\u001B[0m"; // GREEN
                // Reduce frequency by 1
                freqMap.put(guessChars[i], freqMap.get(guessChars[i]) - 1);
            }
        }

        // 2) Mark YELLOW (correct letter, wrong position) or WHITE
        for (int i = 0; i < length; i++) {
            // If not green
            if (feedback[i] == null) {
                char gc = guessChars[i];
                // Check if letter still exists in freqMap
                if (freqMap.containsKey(gc) && freqMap.get(gc) > 0) {
                    feedback[i] = "\u001B[33m" + gc + "\u001B[0m"; // YELLOW
                    freqMap.put(gc, freqMap.get(gc) - 1);
                } else {
                    feedback[i] = "\u001B[37m" + gc + "\u001B[0m"; // WHITE
                }
            }
        }

        // Combine feedback tokens into a single string (no spaces)
        return String.join("", feedback);
    }

    /**
     * Format method remains the same or can be omitted if not needed.
     */
    public String formatOutput(String feedback) {
        return feedback;
    }

    /**
     * Removes letters from A-Z ONLY if they have been marked white
     * in previous guesses.
     *
     * Approach:
     *   - For each guess, generate feedback.
     *   - Check each letter's color code.
     *   - If white, remove that letter from 'remainingLetters'.
     */
    public String getRemainingLetters(Set<String> allGuesses, String secretWord) {
        // Start with full alphabet
        Set<Character> remainingLetters = new HashSet<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            remainingLetters.add(c);
        }

        // For each guess, generate feedback and remove white letters
        for (String guess : allGuesses) {
            // Generate feedback for the guess vs. secret
            // (Keep it uppercase for consistency)
            String feedback = generateFeedback(guess, secretWord);

            // feedback is a sequence of colored letters, e.g.:
            // "\u001B[32mB\u001B[0m\u001B[37mA\u001B[0m..."
            // We check the color code to see if it's [37m (white).
            // The pattern is \u001B[xxmLetter\u001B[0m
            // We'll parse it in pairs:

            // index step: each letter + ANSI prefix. Let's do a simple approach:
            // - feedback length is (guess.length * (ansi codes length + 1 letter + reset code length)).
            // - safer approach: rebuild the feedback in parallel or store the color info while generating.

            // For simplicity, here we do a quick parse:
            char[] arr = feedback.toCharArray();
            // Each chunk looks like: \u001B[XXm + letter + \u001B[0m
            // That's at least 9-10 chars, but can vary if codes differ. We'll do a manual approach:

            // Better approach: re-generate feedback using the method below that returns
            // color codes separately or store them in a data structure. For brevity,
            // let's just match the color code for each letter:

            int idx = 0;
            while (idx < arr.length) {
                // Look for the letter itself by skipping until 'm'
                // Then read next character as the letter
                // Then skip until next 'm' after [0
                // This is somewhat hacky but short:

                // 1) Find the substring "\u001B["
                // 2) Then read next color code
                // 3) The next character after 'm' is the letter
                // This approach assumes a consistent pattern.

                int colorStart = feedback.indexOf("\u001B[", idx);
                if (colorStart < 0) break; // No more color code
                int colorEnd = feedback.indexOf("m", colorStart);
                // The letter is feedback[colorEnd+1]
                char letter = feedback.charAt(colorEnd + 1);

                // Identify the color sequence (e.g. "[37m" or "[33m" etc.)
                String colorSeq = feedback.substring(colorStart, colorEnd + 1);

                // If white ([37m), remove from remainingLetters
                if (colorSeq.contains("[37m")) {
                    remainingLetters.remove(letter);
                }

                // Advance idx to after the reset code
                int resetEnd = feedback.indexOf("m", colorEnd + 1);
                if (resetEnd < 0) break;
                idx = resetEnd + 1;
            }
        }

        // Build output
        StringBuilder sb = new StringBuilder();
        for (char c : remainingLetters) {
            sb.append(c).append(" ");
        }

        return sb.toString().trim();
    }
}
