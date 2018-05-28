package service.strategy;

import model.Move;
import model.Result;
import model.Turn;
import org.junit.Test;
import service.strategy.draw.StatisticDrawMoveResolver;

import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScientificMoveStrategyTest {

    private ScientificMoveStrategy scientificMoveStrategy = new ScientificMoveStrategy(new StatisticDrawMoveResolver());

    @Test
    public void playerLostOnLastTurn() {
        Turn lastTurn = new Turn(Move.PAPER, Move.ROCK);
        lastTurn.setResult(Result.LOSS);
        LinkedList<Turn> previousTurns = new LinkedList<>();
        previousTurns.add(lastTurn);
        Move move = scientificMoveStrategy.nextMove(previousTurns);
        assertThat(move, equalTo(Move.ROCK));
    }

    @Test
    public void playerWonOnLastTurn() {
        Turn lastTurn = new Turn(Move.SCISSORS, Move.ROCK);
        lastTurn.setResult(Result.WIN);
        LinkedList<Turn> previousTurns = new LinkedList<>();
        previousTurns.add(lastTurn);
        Move move = scientificMoveStrategy.nextMove(previousTurns);
        assertThat(move, equalTo(Move.PAPER));
    }
}