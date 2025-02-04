package game;

public class LetterTracker {

    private boolean[] eliminated;
    private String secretWord;


    public LetterTracker(String secretWord) {
        this.secretWord = secretWord;
        eliminated = new boolean[26];
    }

    public void updateTracker(String guess) {

        // for each letter in guess , eliminate it if it is not in the secret word.
        for (int i = 0; i < guess.length(); i++) {
            char letter = guess.charAt(i);
            if (secretWord.indexOf(letter) == -1) {
                int index = letter - 'A';
                if (index >= 0 && index < 26) {
                    eliminated[index] = true;
                }
            }
        }
    }

    public String getRemainingLetters() {
        StringBuilder sb = new StringBuilder();
        for (char c = 'A'; c <= 'Z'; c++) {
            if (!eliminated[c - 'A']) {
                sb.append(c).append(" ");
            }
        }
        return sb.toString().trim();
    }

}
