package model;

public class Turn {

    private Move serverMove;
    private Move playerMove;
    private Result result;

    public Turn(Move serverMove, Move playerMove) {
        this.serverMove = serverMove;
        this.playerMove = playerMove;
    }

    public Turn(Move serverMove, Move playerMove, Result result) {
        this.serverMove = serverMove;
        this.playerMove = playerMove;
        this.result = result;
    }

    public Move getServerMove() {
        return serverMove;
    }

    public Move getPlayerMove() {
        return playerMove;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}