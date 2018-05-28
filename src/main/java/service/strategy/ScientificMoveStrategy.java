package service.strategy;

import model.Move;
import model.Result;
import model.Turn;
import service.strategy.draw.DrawMoveResolver;

import java.util.LinkedList;

import static service.result.ResultDeterminant.winCombinations;

/**
 * This strategy assumes that player doesn't know anything about the result of scientific research
 * described in the based interface. This means that every time players losses the server
 * assumes that player will change to move on the next turn so that it would be a win combination
 * with the same server move. For example, if player lost with combination (PAPER, ROCK)
 * on the next turn it will choose SCISSORS to beat the PAPER.
 */
public class ScientificMoveStrategy implements MoveStrategy {

    private final DrawMoveResolver drawMoveResolver;

    public ScientificMoveStrategy(DrawMoveResolver drawMoveResolver) {
        this.drawMoveResolver = drawMoveResolver;
    }

    /**
     * According to scientific research ROCK is the most popular move as a first one in the game.
     */
    @Override
    public Move nextMove(LinkedList<Turn> previousTurns) {
        if (previousTurns.isEmpty()) {
            return Move.PAPER;
        }

        Turn lastTurn = previousTurns.getLast();
        if (lastTurn.getResult().equals(Result.DRAW)) {
            return drawMoveResolver.moveAfterDraw(previousTurns);
        } else if (lastTurn.getResult().equals(Result.LOSS)) {
            Move expectedNextPlayerMove = winCombinations.get(lastTurn.getServerMove());
            return winCombinations.get(expectedNextPlayerMove);
        } else {
            Move expectedNextPlayerMove = lastTurn.getPlayerMove();
            return winCombinations.get(expectedNextPlayerMove);
        }
    }
}