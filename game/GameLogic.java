package game;

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
                
                System.out.print("Enter your guess: ");
                String guess = scanner.nextLine().trim();

                if (guess.length() != 5) {
                    System.out.println("Your guess must be exactly 5 letters long");
                    continue;
                }

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

                if (!validateGuess(guess)) {
                    System.out.println("Invalid guess.");
                    continue;
                }

                processGuess(guess);
                attempts++;

                if (guess.equals(secretWord)) {
                    System.out.println("Congratulations! You've guessed the word correctly.");
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
            System.out.println("Attempts remaining: " + (maxAttempts - attempts));

        }

 
     
        public void endGame() {
            if (!win) {
                System.out.println("Game over. The correct word was: " + secretWord);
            }
            // Optionally, display game stats to the user.
            
            
            // Write the game stats to stats.csv regardless of win or loss.
            io.StatsManager statsManager = new io.StatsManager();
            statsManager.writeStats(username, secretWord, attempts, win);
        }

    }
