package app.model;

/**
 * Class is responsible for keeping score/statistic for a current game.
 * Every time new game is created - new score associated to it will be created as well.
 */
public class Score {

    private int serverScore;
    private int playerScore;

    public Score() {
    }

    public void serverScored() {
        this.serverScore++;
    }

    public void playerScored() {
        this.playerScore++;
    }

    public int getServerScore() {
        return serverScore;
    }

    public int getPlayerScore() {
        return playerScore;
    }
}