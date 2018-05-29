package service.game;

import model.Move;
import model.Result;
import model.Score;

/**
 * After each round service is returning just a current result - (WIN/DRAW/LOSS), while
 * after stopGame() it's returning the Score of the whole game.
 */
public interface GameService {

    void startGame();

    Result nextTurn(Move playerMove);

    Score stopGame();
}