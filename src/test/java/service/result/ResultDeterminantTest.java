package service.result;

import model.Move;
import model.Result;
import model.Turn;
import org.junit.Test;
import service.result.ResultDeterminant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResultDeterminantTest {

    private ResultDeterminant resultDeterminant = new ResultDeterminant();

    @Test
    public void resultIsDrawIfMovesAreSame() {
        Turn turn = new Turn(Move.PAPER, Move.PAPER);
        Result result = resultDeterminant.determine(turn);
        assertThat(result, equalTo(Result.DRAW));
    }

    @Test
    public void userWinIfHasBetterMove() {
        Turn turn = new Turn(Move.PAPER, Move.SCISSORS);
        Result result = resultDeterminant.determine(turn);
        assertThat(result, equalTo(Result.WIN));
    }

    @Test
    public void userLossIfServerHasBetterMove() {
        Turn turn = new Turn(Move.ROCK, Move.SCISSORS);
        Result result = resultDeterminant.determine(turn);
        assertThat(result, equalTo(Result.LOSS));
    }
}