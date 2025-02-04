package game;

import io.StatsManager;
import java.util.Scanner;

public class GameLogic {
   
        // initialize main variables

        private String secretWord;
        private String username;
        private int attempts;
        private final int maxAttempts = 6;
        private Scanner scanner;
        private FeedBackGenerator feedbackGenerator;
        private LetterTracker letterTracker;
        private boolean win;
    
        public GameLogic(String username, String secretWord, Scanner scanner) {

            this.username = username;
            this.secretWord = secretWord;
            this.scanner = scanner;
            this.attempts = 0;
            this.feedbackGenerator = new FeedBackGenerator();
            // lettertracker receives secretword so it can decide which letter
            this.letterTracker = new LetterTracker(secretWord);
            this.win = false;
        }
    
        public void startGame() {
            while (attempts < maxAttempts) {
                System.out.println("\nAttempt " + (attempts + 1) + " of " + maxAttempts);
                System.out.print("Enter your guess (5-letter word): ");
                String guess = scanner.nextLine().trim().toUpperCase();

                if (!validateGuess(guess)) {
                    System.out.println("Invalid guess. Please enter a 5-letter word.");
                    continue;

                }

                attempts++;
                processGuess(guess);
                if (guess.equals(secretWord)) {
                    System.out.println("Congratulations " + username + "! You guessed the word.");
                    win = true;
                    break;
                }
            }
            endGame();
     
        }

        public boolean validateGuess(String guess) {
            return guess != null && guess.length() == 5;
        }

        public void processGuess(String guess) {
            String feedback = feedbackGenerator.generateFeedback(guess, secretWord);
            System.out.println("Feedback: " + feedbackGenerator.formatOutput(feedback));
            letterTracker.updateTracker(guess);
            System.out.println("Remaining letters: " + letterTracker.getRemainingLetters());

        }

        public void displayStats() {
            System.out.println("\nGame stats:");
            System.out.println("Username: " + username);
            System.out.println("Secret Word: " + secretWord);
            System.out.println("Attempts: " + attempts);
            System.out.println("Result: " + (win ? "win" : "loss"));
        }
     
        public void endGame() {
            if (!win) {
                System.out.println("Sorry, you've used all attempts. The secret word was: " + secretWord);

            }

            displayStats();
            StatsManager statsManager = new StatsManager();
            statsManager.writeStats(username, secretWord, attempts, win);
        }

    } 
