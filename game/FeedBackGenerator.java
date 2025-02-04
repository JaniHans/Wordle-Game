package game;

public class FeedBackGenerator {
    public String generateFeedback(String guess, String secretWord) {
        // Compare letters and assign green/yellow/white
        char[] secretChars = secretWord.toCharArray();
        char[] guessChars = guess.toCharArray();
        String[] feedback = new String[5];
        boolean[] secretUsed = new boolean[5];



        // First pass : mark correct positions (green);
        for (int i = 0; i < 5; i++) {
            if (guessChars[i] == secretChars[i]) {
                feedback[i] = "\u001b[32m" + guessChars[i] + "\u001b[0m";
                secretUsed[i] = true;
            }
        }

        // Second pass mark letters that exist in secret (yellow) or are incorrect (white)
        for (int i = 0; i < 5; i++) {
            if (feedback[i] == null) {
                boolean found = false;
                for (int j = 0; j < 5; j++) {
                    if (!secretUsed[j] && guessChars[i] == secretChars[j]) {
                        found = true;
                        secretUsed[j] = true;
                        break;
                    }

                }
                if (found) {
                    feedback[i] = "\u001B[33m" + guessChars[i] + "\u001b[0m";
                } else {
                    feedback[i] = "\u001b[37m" + guessChars[i] + "\u001b[0m";
                }
            }
        }

        // Combine feedback tokens
        StringBuilder sb = new StringBuilder();
        for (String s : feedback) {
            sb.append(s).append(" ");

        }

        return sb.toString().trim();
    }

    public String formatOutput(String feedback) {
        // Apply ANSI escape codes to colorize the output
        return feedback;
    }
}
