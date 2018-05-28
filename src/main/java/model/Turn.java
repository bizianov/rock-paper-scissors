package model;

public class Turn {

    private Move serverMove;
    private Move userMove;
    private Result result;

    public Turn(Move serverMove, Move userMove) {
        this.serverMove = serverMove;
        this.userMove = userMove;
    }


    public Move getServerMove() {
        return serverMove;
    }

    public Move getUserMove() {
        return userMove;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}