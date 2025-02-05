package game;

public class FeedBackGenerator {
    public String generateFeedback(String guess, String secretWord) {
        char[] guessChars = guess.toCharArray();
        char[] secretChars = secretWord.toCharArray();
        String[] feedback = new String[guess.length()];
        boolean[] secretUsed = new boolean[secretWord.length()];

        // First pass: check for correct positions (green)
        for (int i = 0; i < guessChars.length; i++) {
            if (guessChars[i] == secretChars[i]) {
                feedback[i] = "\u001B[32m" + guessChars[i] + "\u001B[0m"; // Green
                secretUsed[i] = true;
            }
        }

        // Second pass: check for correct letters in wrong positions (yellow) and incorrect letters (white)
        for (int i = 0; i < guessChars.length; i++) {
            if (feedback[i] == null) { // If not already marked as green
                boolean found = false;
                for (int j = 0; j < secretChars.length; j++) {
                    if (!secretUsed[j] && guessChars[i] == secretChars[j]) {
                        found = true;
                        secretUsed[j] = true;
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

    public String formatOutput(String feedback) {
        return feedback;
    }
}
