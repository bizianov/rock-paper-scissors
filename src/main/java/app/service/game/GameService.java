package app.service.game;

import app.model.Move;
import app.model.Result;
import app.model.Score;

/**
 * After each round app.service is returning just a current result {@link app.model.Result} -
 * (WIN/DRAW/LOSS), while after stopGame() it's returning the score {@link app.model.Score}
 * of the whole game.
 */
public interface GameService {

    Score startGame();

    Result nextTurn(Move playerMove);

    Score stopGame();
}