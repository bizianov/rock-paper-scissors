package app.service.strategy.draw;

import app.model.Move;
import app.model.Result;
import app.model.Turn;
import org.junit.Test;

import java.util.LinkedList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StatisticDrawMoveResolverTest {

    private StatisticDrawMoveResolver statisticDrawMoveResolver = new StatisticDrawMoveResolver();

    @Test
    public void playerUsesTheSameMoveAfterDraw() {
        LinkedList<Turn> previousTurns = new LinkedList<>();
        previousTurns.add(new Turn("ID-12345678", Move.PAPER, Move.PAPER, Result.DRAW));
        previousTurns.add(new Turn("ID-12345679", Move.ROCK, Move.PAPER, Result.WIN));
        previousTurns.add(new Turn("ID-12345670", Move.SCISSORS, Move.SCISSORS, Result.DRAW));
        Move move = statisticDrawMoveResolver.moveAfterDraw(previousTurns);
        assertThat(move, equalTo(Move.ROCK));
    }

    @Test
    public void playerUsesWinMoveToOneUsedInDrawRound() {
        LinkedList<Turn> previousTurns = new LinkedList<>();
        previousTurns.add(new Turn("ID-12345678", Move.PAPER, Move.PAPER, Result.DRAW));
        previousTurns.add(new Turn("ID-12345679", Move.ROCK, Move.SCISSORS, Result.LOSS));
        previousTurns.add(new Turn("ID-12345670", Move.ROCK, Move.ROCK, Result.DRAW));
        Move move = statisticDrawMoveResolver.moveAfterDraw(previousTurns);
        assertThat(move, equalTo(Move.SCISSORS));
    }

    @Test
    public void playerUsesLossMoveToOneUsedInDrawRound() {
        LinkedList<Turn> previousTurns = new LinkedList<>();
        previousTurns.add(new Turn("ID-12345678", Move.PAPER, Move.PAPER, Result.DRAW));
        previousTurns.add(new Turn("ID-12345679", Move.SCISSORS, Move.ROCK, Result.WIN));
        previousTurns.add(new Turn("ID-12345670", Move.PAPER, Move.PAPER, Result.DRAW));
        Move move = statisticDrawMoveResolver.moveAfterDraw(previousTurns);
        assertThat(move, equalTo(Move.PAPER));
    }
}