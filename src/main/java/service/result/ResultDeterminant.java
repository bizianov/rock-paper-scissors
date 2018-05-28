package service.result;

import model.Move;
import model.Result;
import model.Turn;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResultDeterminant {

    private final Map<Move, Move> winCombinations;

    public ResultDeterminant() {
        Map<Move, Move> combinationsMap = new HashMap<>();
        combinationsMap.put(Move.PAPER, Move.ROCK);
        combinationsMap.put(Move.ROCK, Move.SCISSORS);
        combinationsMap.put(Move.SCISSORS, Move.PAPER);
        this.winCombinations = Collections.unmodifiableMap(combinationsMap);
    }

    public Result determine(Turn turn) {
        if (turn.getServerMove().equals(turn.getUserMove())) {
            return Result.DRAW;
        }
        return winCombinations.get(turn.getServerMove()).equals(turn.getUserMove()) ? Result.LOSS : Result.WIN;
    }
}