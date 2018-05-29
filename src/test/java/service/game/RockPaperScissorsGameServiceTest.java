package service.game;

import model.Move;
import model.Result;
import model.Score;
import org.junit.Before;
import org.junit.Test;
import service.history.GameHistoryHolder;
import service.result.ResultDeterminant;
import service.strategy.ScientificMoveStrategy;
import service.strategy.ScientificRevertedMoveStrategy;
import service.strategy.StrategySwitcher;
import service.strategy.draw.DrawMoveResolver;
import service.strategy.draw.StatisticDrawMoveResolver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RockPaperScissorsGameServiceTest {

    private RockPaperScissorsGameService gameService;

    @Before
    public void init() {
        DrawMoveResolver drawMoveResolver = new StatisticDrawMoveResolver();
        StrategySwitcher strategySwitcher = new StrategySwitcher(new ScientificMoveStrategy(drawMoveResolver),
                new ScientificRevertedMoveStrategy(drawMoveResolver));
        gameService = new RockPaperScissorsGameService(strategySwitcher,
                new GameHistoryHolder(), new ResultDeterminant());
    }

    @Test
    public void startGame() {
        gameService.startGame();
        Score score = gameService.stopGame();
        assertThat(score.getServerScore(), equalTo(0));
        assertThat(score.getPlayerScore(), equalTo(0));
    }

    @Test
    public void firstMoveIsPaper() {
        gameService.startGame();
        Result result = gameService.nextTurn(Move.SCISSORS);
        Score score = gameService.stopGame();
        assertThat(result, equalTo(Result.WIN));
        assertThat(score.getServerScore(), equalTo(0));
        assertThat(score.getPlayerScore(), equalTo(1));
    }

    /**
     * Player doesn't know anything about strategy research and plays
     * according to {@link service.strategy.ScientificMoveStrategy}
     */
    @Test
    public void basicPlayer() {
        gameService.startGame();
        gameService.nextTurn(Move.SCISSORS);
        gameService.nextTurn(Move.SCISSORS);
        gameService.nextTurn(Move.PAPER);
        gameService.nextTurn(Move.ROCK);
        Score score = gameService.stopGame();
        assertThat(score.getServerScore(), equalTo(3));
        assertThat(score.getPlayerScore(), equalTo(1));
    }

    /**
     * Player does know about strategy research and plays
     * according to {@link service.strategy.ScientificRevertedMoveStrategy}
     */
    @Test
    public void advancedPlayer() {
        gameService.startGame();
        gameService.nextTurn(Move.ROCK);
        gameService.nextTurn(Move.PAPER);
        gameService.nextTurn(Move.ROCK);
        Score score = gameService.stopGame();
        assertThat(score.getServerScore(), equalTo(2));
        assertThat(score.getPlayerScore(), equalTo(1));
    }
}