
import game.GameLogic;
import io.StatsManager;
import io.WordLoader;
import java.util.Scanner;

public class WordleGame {
    public static void main(String[] args) {
        // Single scanner for lifetime of the program
        Scanner scanner = new Scanner(System.in);

        // get the players username
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();


        // load the secret word using the provided word index from the CLI
        WordLoader loader = new WordLoader();

        int wordIndex = 0;

        if (args.length > 0) {
            try {
                wordIndex = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument. Using default index 0.");

            }
        } 
        else {
            System.out.println("No word index provided. Using default index 0");
        }
        String secretWord = loader.getWord(wordIndex);

        // Start the game

        GameLogic gameLogic = new GameLogic(username, secretWord, scanner);
        gameLogic.startGame();

        // option to view past stats
        System.out.println("View past stats? (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();
        if (response.equals("Y")) {
            StatsManager statsManager = new StatsManager();
            statsManager.readStats();
        }


        // Let the program end naturally.
        scanner.close();
        
    }
}
