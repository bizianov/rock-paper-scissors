package service.strategy;

import model.Move;
import model.Result;
import model.Turn;
import org.junit.Test;
import service.strategy.draw.StatisticDrawMoveResolver;

import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScientificRevertedMoveStrategyTest {

    private MoveStrategy moveStrategy = new ScientificRevertedMoveStrategy(new StatisticDrawMoveResolver());

    /**
     * Server - PAPER, Player - ROCK.
     * Player should change the move - SCISSORS, so server will generate ROCK.
     * But if player DOES KNOW about this strategy he will generate PAPER.
     * So, server should generate SCISSORS.
     */
    @Test
    public void playerLostOnLastTurn() {
        Turn lastTurn = new Turn(Move.PAPER, Move.ROCK);
        lastTurn.setResult(Result.LOSS);
        LinkedList<Turn> previousTurns = new LinkedList<>();
        previousTurns.add(lastTurn);
        Move move = moveStrategy.nextMove(previousTurns);
        assertThat(move, equalTo(Move.SCISSORS));
    }

    /**
     * Server - SCISSORS, Player - ROCK.
     * Player shouldn't change the move, so server will generate PAPER.
     * But if player DOES KNOW about this strategy he will generate SCISSORS.
     * So, server should generate ROCK.
     */
    @Test
    public void playerWonOnLastTurn() {
        Turn lastTurn = new Turn(Move.SCISSORS, Move.ROCK);
        lastTurn.setResult(Result.WIN);
        LinkedList<Turn> previousTurns = new LinkedList<>();
        previousTurns.add(lastTurn);
        Move move = moveStrategy.nextMove(previousTurns);
        assertThat(move, equalTo(Move.ROCK));
    }
}