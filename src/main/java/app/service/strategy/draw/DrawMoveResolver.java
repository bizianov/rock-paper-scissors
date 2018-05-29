package app.service.strategy.draw;

import app.model.Move;
import app.model.Turn;

import java.util.LinkedList;

/**
 * Describes the strategy of the next move if the result of previous one was DRAW.
 */
public interface DrawMoveResolver {

    Move moveAfterDraw(LinkedList<Turn> previousTurns);
}