package service.strategy.draw;

import model.Move;
import model.Turn;

import java.util.LinkedList;

/**
 * Describes the strategy of the next move if the result of previous one was DRAW.
 */
public interface DrawMoveResolver {

    Move moveAfterDraw(LinkedList<Turn> previousTurns);
}