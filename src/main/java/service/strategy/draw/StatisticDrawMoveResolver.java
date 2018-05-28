package service.strategy.draw;

import model.Move;
import model.Result;
import model.Turn;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Takes decision based on statistic of previous player's moves whether player was changing the
 * move or used the same one.
 */
public class StatisticDrawMoveResolver implements DrawMoveResolver {

    @Override
    public Move moveAfterDraw(LinkedList<Turn> previousTurns) {
        List<Turn> drawTurns = previousTurns.stream()
                .filter(turn -> turn.getResult().equals(Result.DRAW))
                .collect(toList());
        return null;
    }
}