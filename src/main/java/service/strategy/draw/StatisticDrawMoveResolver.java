package service.strategy.draw;

import model.Move;
import model.Result;
import model.Turn;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static service.result.ResultDeterminant.winCombinations;

/**
 * Takes decision based on statistic of previous player's moves whether player was changing the
 * move or used the same one in case of DRAW.
 */
public class StatisticDrawMoveResolver implements DrawMoveResolver {

    @Override
    public Move moveAfterDraw(LinkedList<Turn> previousTurns) {
        if (previousTurns.size() > 1) {
            Map<Result, Integer> resultMap = new HashMap<>();
            for (int i = 0; i < previousTurns.size() - 1; i++) {
                if (previousTurns.get(i).getResult() != Result.DRAW) {
                    i++;
                } else {
                    processTurns(resultMap, previousTurns.get(i), previousTurns.get(i + 1));
                }
            }
            Move expectedPlayerMove = expectedMoveByPreviousMoveAndResult(previousTurns.getLast().getPlayerMove(),
                    extractTopMove(resultMap));
            return winCombinations.get(expectedPlayerMove);
        }
        return Move.PAPER;
    }

    /**
     * When this method will be called for every turn, the resultMap will be built showing what was the most popular
     * player's move after the DRAW:
     * DRAW - player tried to use the same move
     * WIN - player tried to use the move which is WIN to one used in a DRAW round
     * LOSS - player tried to use the move which is LOSS to one used in a DRAW round
     */
    private void processTurns(Map<Result, Integer> resultMap, Turn turn1, Turn turn2) {
        if (turn1.getPlayerMove() == turn2.getPlayerMove()) {
            if (resultMap.computeIfPresent(Result.DRAW, (k, v) -> v + 1) == null) {
                resultMap.put(Result.DRAW, 1);
            }
        } else if (turn2.getPlayerMove() == winCombinations.get(turn1.getPlayerMove())) {
            if (resultMap.computeIfPresent(Result.WIN, (k, v) -> v + 1) == null) {
                resultMap.put(Result.WIN, 1);
            }
        } else {
            if (resultMap.computeIfPresent(Result.LOSS, (k, v) -> v + 1) == null) {
                resultMap.put(Result.LOSS, 1);
            }
        }
    }

    private Result extractTopMove(Map<Result, Integer> resultMap) {
        return Collections.max(resultMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }


    private Move expectedMoveByPreviousMoveAndResult(Move move, Result result) {
        if (result == Result.DRAW) {
            return move;
        } else if (result == Result.WIN) {
            return winCombinations.get(move);
        } else {
            return winCombinations.get(winCombinations.get(move));
        }
    }
}