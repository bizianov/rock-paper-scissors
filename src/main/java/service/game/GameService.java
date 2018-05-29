package service.game;

import model.Move;
import model.Result;
import model.Score;

/**
 * After each round service is returning just a current result {@link model.Result} -
 * (WIN/DRAW/LOSS), while after stopGame() it's returning the score {@link model.Score}
 * of the whole game.
 */
public interface GameService {

    void startGame();

    Result nextTurn(Move playerMove);

    Score stopGame();
}