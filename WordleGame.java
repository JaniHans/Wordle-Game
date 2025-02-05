import game.GameLogic;
import io.StatsManager;
import io.WordLoader;
import java.util.Scanner;


public class WordleGame {
    public static void main(String[] args) {
        // Single scanner for lifetime of the program
        Scanner scanner = new Scanner(System.in);

        // get the player's username
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();
        System.out.println("Welcome to Wordle! Guess the 5-letter word.");

        // load the secret word using the provided word index from the CLI
        WordLoader loader = new WordLoader();

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
            System.out.println("Please provide a number as command line argument.");
            validInput = false;
        }

        if (validInput) {
            String secretWord = loader.getWord(wordIndex);

            // Start the game
            GameLogic gameLogic = new GameLogic(username, secretWord, scanner);
            gameLogic.startGame();
    
            // option to view past stats
            System.out.println("Do you want to see your stats? (yes/no): ");
            String response = scanner.nextLine().trim();
            if (response.equals("yes")) {
                StatsManager statsManager = new StatsManager();
                statsManager.readStats(username);
            }
        }



        // Prompt the user to press Enter to exit.
        System.out.print("Press Enter to exit...");
        scanner.nextLine();  // Wait for Enter key press.
        scanner.close();
    }
}