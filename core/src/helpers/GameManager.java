package helpers;

// Singleton (singleton pattern)
public class GameManager {

    private static GameManager ourInstance = new GameManager();

    public boolean gameStartedFromMainMenu;
    public boolean isPaused = true;
    public int lifeScore, coinScore, score;

    private GameManager(){

    }

    public static GameManager getInstance(){
        return ourInstance;
    }
} // game manager
