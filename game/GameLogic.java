package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GameLogic {

    // Initialize main variables
    private String secretWord;
    private String username;
    private int attempts;
    private final int maxAttempts = 6;
    private Scanner scanner;
    private FeedBackGenerator feedbackGenerator;
    private LetterTracker letterTracker;
    private boolean win;
    private Set<String> validWords; // Set to store valid words

    public GameLogic(String username, String secretWord, Scanner scanner) {
        this.username = username;
        this.secretWord = secretWord;
        this.scanner = scanner;
        this.attempts = 0;
        this.feedbackGenerator = new FeedBackGenerator();
        this.letterTracker = new LetterTracker(secretWord);
        this.win = false;
        this.validWords = loadValidWords("wordle-words.txt"); // Initialize the set of val
    }
    private Set<String> loadValidWords(String filePath) {
        Set<String> words = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading valid words: " + e.getMessage());
        }
        return words;
    }
    public void startGame() {
        while (attempts < maxAttempts) {
            System.out.print("Enter your guess: ");
            String guess = scanner.nextLine().trim();

            // Validate guess length
            if (guess.length() != 5) {
                System.out.println("Your guess must be exactly 5 letters long.");
                continue;
            }

            // Validate guess characters
            boolean validGuess = true;
            for (int i = 0; i < guess.length(); i++) {
                char c = guess.charAt(i);
                if (!Character.isLowerCase(c) || Character.isDigit(c)) {
                    System.out.println("Your guess must only contain lowercase letters.");
                    validGuess = false;
                    break;
                }
            }

            if (!validGuess) {
                continue;
            }

            // Validate if the word is in the valid word list
            if (!validateGuess(guess)) {
                System.out.println("Word not in list. Please enter a valid word.");
                continue;
            }

            // Process the guess
            processGuess(guess);
            attempts++;

            // Check if the guess is correct
            if (guess.equals(secretWord)) {
                System.out.println("Congratulations! You've guessed the word correctly.");
                win = true;
                break;
            }
        }
        endGame();
    }

    public boolean validateGuess(String guess) {
        // Check if the guess is in the valid word list
        return validWords.contains(guess);
    }

    public void processGuess(String guess) {
        String feedback = feedbackGenerator.generateFeedback(guess, secretWord);
        System.out.println("Feedback: " + feedbackGenerator.formatOutput(feedback));
        letterTracker.updateTracker(guess);
        System.out.println("Remaining letters: " + letterTracker.getRemainingLetters());
        System.out.println("Attempts remaining: " + (maxAttempts - attempts));
    }

    public void endGame() {
        if (!win) {
            System.out.println("Game over. The correct word was: " + secretWord);
        }

        // Write the game stats to stats.csv
        io.StatsManager statsManager = new io.StatsManager();
        statsManager.writeStats(username, secretWord, attempts, win);
    }
}