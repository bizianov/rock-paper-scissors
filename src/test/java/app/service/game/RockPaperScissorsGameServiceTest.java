package app.service.game;

import app.model.Move;
import app.model.Result;
import app.model.Score;
import app.repository.TurnRepository;
import app.service.result.ResultDeterminant;
import app.service.strategy.ScientificMoveStrategy;
import app.service.strategy.ScientificRevertedMoveStrategy;
import app.service.strategy.StrategySwitcher;
import app.service.strategy.draw.DrawMoveResolver;
import app.service.strategy.draw.StatisticDrawMoveResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RockPaperScissorsGameServiceTest {

    @Autowired
    private GameService gameService;
    @Autowired
    private TurnRepository turnRepository;

    @Before
    public void init() {
        turnRepository.deleteAll();
        gameService.startGame();
    }

    @After
    public void shutdown() {
        gameService.stopGame();
    }

    @Test
    public void startGame() {
        Score score = gameService.stopGame();
        assertThat(score.getServerScore(), equalTo(0));
        assertThat(score.getPlayerScore(), equalTo(0));
    }

    @Test
    public void firstMoveIsPaper() {
        Result result = gameService.nextTurn(Move.SCISSORS);
        Score score = gameService.stopGame();
        assertThat(result, equalTo(Result.WIN));
        assertThat(score.getServerScore(), equalTo(0));
        assertThat(score.getPlayerScore(), equalTo(1));
    }

    /**
     * Player doesn't know anything about strategy research and plays
     * according to {@link app.service.strategy.ScientificMoveStrategy}
     */
    @Test
    public void basicPlayer() {
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
     * according to {@link app.service.strategy.ScientificRevertedMoveStrategy}
     */
    @Test
    public void advancedPlayer() {
        gameService.nextTurn(Move.ROCK);
        gameService.nextTurn(Move.PAPER);
        gameService.nextTurn(Move.ROCK);
        Score score = gameService.stopGame();
        assertThat(score.getServerScore(), equalTo(2));
        assertThat(score.getPlayerScore(), equalTo(1));
    }
}