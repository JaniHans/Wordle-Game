import game.GameLogic;
import io.StatsManager;
import io.WordLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class WordleGame {
    public static void main(String[] args) {
        // Single scanner for lifetime of the program
        Scanner scanner = new Scanner(System.in);

        // Get the player's username
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();
        System.out.println("Welcome to Wordle! Guess the 5-letter word.");

        // Create a WordLoader to handle file loading
        WordLoader loader = new WordLoader();

        // Parse command line args for word index
        int wordIndex = 0;
        boolean validInput = true;



        if (args.length > 0) {
            try {
                wordIndex = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid command-line argument. Please launch with a valid number.");
                validInput = false;
            }
        } else {
            validInput = false;
        }

        // Either load by index or default to "apple"
        String secretWord;
        if (validInput) {
            secretWord = loader.getWord(wordIndex);
        } else {
            secretWord = "apple"; // Fallback secret word
        }

        // Load valid words
        List<String> validWordsList = loader.loadWords("wordle-words.txt");
        Set<String> validWordsSet = new HashSet<>(validWordsList);
        // (Ensure "wordle-words.txt" is in the correct location/resource path)

        // Start the game
        GameLogic gameLogic = new GameLogic(username, secretWord, scanner, validWordsSet);
        gameLogic.startGame();

        // Option to view past stats
        System.out.println("Do you want to see your stats? (yes/no): ");
        String response = scanner.nextLine().trim();
        if (response.equalsIgnoreCase("yes")) {
            StatsManager statsManager = new StatsManager();
            statsManager.readStats(username);
        }

        // Prompt the user to press Enter to exit.
        System.out.print("Press Enter to exit...");
        scanner.nextLine(); 
        scanner.close();
    }
}
