package app.service.strategy;

import app.model.Move;
import app.model.Turn;

import java.util.LinkedList;

/**
 * Multiple implementations of MoveStrategy are available.
 * According to scientific research while playing a person is making decision which move to use
 * (PAPER|ROCK|SCISSORS) based on the result of previous turn. If there was success usually
 * people don't change the move. In contrary if there was a fail player tries to change the move
 * in order it would be a win choice for the current opponent move.
 */
public interface MoveStrategy {

    Move nextMove(LinkedList<Turn> previousTurns);
}