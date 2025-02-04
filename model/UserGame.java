package model;

public class UserGame {
    private String username;
    private String secretWord;
    private int attempts;
    private boolean win;



    public UserGame(String username, String secretWord, int attemps, boolean win) {
        // Constructor to initialize user game data


        this.username = username;
        this.secretWord = secretWord;
        this.attempts = attempts;
        this.win = win;
    }
    
    public String toCSV() {
        // Convert object to CSV row
        String result = win ? "win" : "loss";
        return username + "," + secretWord + "," + attempts + "," + result;
    }
    
}

