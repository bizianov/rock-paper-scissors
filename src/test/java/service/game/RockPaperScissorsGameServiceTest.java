package service.game;

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
}