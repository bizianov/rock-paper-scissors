package app.service.strategy;

import app.model.Move;
import app.model.Result;
import app.model.Turn;
import org.junit.Before;
import org.junit.Test;
import app.service.strategy.draw.DrawMoveResolver;
import app.service.strategy.draw.StatisticDrawMoveResolver;

import java.util.LinkedList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StrategySwitcherTest {

    private StrategySwitcher strategySwitcher;

    @Before
    public void init() {
        DrawMoveResolver drawMoveResolver = new StatisticDrawMoveResolver();
        strategySwitcher = new StrategySwitcher(new ScientificMoveStrategy(drawMoveResolver),
                new ScientificRevertedMoveStrategy(drawMoveResolver));
        strategySwitcher.setNumberOfTurnsToAnalyze(20);
    }

    @Test
    public void scientificMoveStrategyAfterLoss() {
        LinkedList<Turn> previousTurns = new LinkedList<>();
        previousTurns.add(new Turn("ID-12345678", Move.PAPER, Move.ROCK, Result.LOSS));
        previousTurns.add(new Turn("ID-12345679", Move.ROCK, Move.SCISSORS, Result.LOSS));
        MoveStrategy moveStrategy = strategySwitcher.getCurrentMoveStrategy(previousTurns);
        assertThat(moveStrategy.getClass(), equalTo(ScientificMoveStrategy.class));
    }

    @Test
    public void scientificMoveStrategyAfterWin() {
        LinkedList<Turn> previousTurns = new LinkedList<>();
        previousTurns.add(new Turn("ID-12345678", Move.ROCK, Move.PAPER, Result.WIN));
        previousTurns.add(new Turn("ID-12345679", Move.SCISSORS, Move.PAPER, Result.LOSS));
        MoveStrategy moveStrategy = strategySwitcher.getCurrentMoveStrategy(previousTurns);
        assertThat(moveStrategy.getClass(), equalTo(ScientificMoveStrategy.class));
    }

    @Test
    public void scientificRevertedMoveStrategyAfterLoss() {
        LinkedList<Turn> previousTurns = new LinkedList<>();
        previousTurns.add(new Turn("ID-12345678", Move.PAPER, Move.ROCK, Result.LOSS));
        previousTurns.add(new Turn("ID-12345679", Move.PAPER, Move.PAPER, Result.DRAW));
        MoveStrategy moveStrategy = strategySwitcher.getCurrentMoveStrategy(previousTurns);
        assertThat(moveStrategy.getClass(), equalTo(ScientificRevertedMoveStrategy.class));
    }

    @Test
    public void scientificRevertedMoveStrategyAfterWin() {
        LinkedList<Turn> previousTurns = new LinkedList<>();
        previousTurns.add(new Turn("ID-12345678", Move.ROCK, Move.PAPER, Result.WIN));
        previousTurns.add(new Turn("ID-12345679", Move.ROCK, Move.ROCK, Result.DRAW));
        MoveStrategy moveStrategy = strategySwitcher.getCurrentMoveStrategy(previousTurns);
        assertThat(moveStrategy.getClass(), equalTo(ScientificRevertedMoveStrategy.class));
    }
}