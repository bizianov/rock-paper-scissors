package service.result;

import model.Move;
import model.Result;
import model.Turn;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class is containing a pre-defined map with LOSS-WIN combination: for each key move value is win move.
 * The only determine method is evaluating the result of current round (turn).
 */
public class ResultDeterminant {

    static {
        Map<Move, Move> combinationsMap = new HashMap<>();
        combinationsMap.put(Move.SCISSORS, Move.ROCK);
        combinationsMap.put(Move.ROCK, Move.PAPER);
        combinationsMap.put(Move.PAPER, Move.SCISSORS);
        winCombinations = Collections.unmodifiableMap(combinationsMap);
    }

    public static final Map<Move, Move> winCombinations;

    public ResultDeterminant() {

    }

    public Result determine(Turn turn) {
        if (turn.getServerMove().equals(turn.getPlayerMove())) {
            return Result.DRAW;
        }
        return winCombinations.get(turn.getServerMove()).equals(turn.getPlayerMove()) ? Result.WIN : Result.LOSS;
    }
}