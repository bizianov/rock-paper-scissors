package service.strategy;

import model.Result;
import model.Turn;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static service.result.ResultDeterminant.winCombinations;

/**
 * The brain of the game, this class is responsible for analyzing previous turns and making decision which strategy
 * to activate to play most efficiently with an opponent.
 */
public class StrategySwitcher {

    private static final Integer NUMBER_OF_TURNS_TO_ANALYZE = 20;

    private final ScientificMoveStrategy scientificMoveStrategy;
    private final ScientificRevertedMoveStrategy scientificRevertedMoveStrategy;

    private MoveStrategy currentMoveStrategy;

    public StrategySwitcher(ScientificMoveStrategy scientificMoveStrategy,
                            ScientificRevertedMoveStrategy scientificRevertedMoveStrategy) {
        this.scientificMoveStrategy = scientificMoveStrategy;
        this.scientificRevertedMoveStrategy = scientificRevertedMoveStrategy;
        this.currentMoveStrategy = scientificMoveStrategy;
    }

    private void switchStrategy(MoveStrategy moveStrategy) {
        this.currentMoveStrategy = moveStrategy;
    }

    public MoveStrategy getCurrentMoveStrategy(LinkedList<Turn> previousTurns) {
        if (previousTurns.size() > 1) {
            Map<Class, Integer> resultMap = new HashMap<>();
            LinkedList<Turn> turnsToAnalyze = lastTopTurns(previousTurns);

            for (int i = 0; i < turnsToAnalyze.size() - 1; i++) {
                if (isScientificStrategy(turnsToAnalyze.get(i), turnsToAnalyze.get(i+1))) {
                    if (resultMap.computeIfPresent(ScientificMoveStrategy.class, (k, v) -> v + 1) == null) {
                        resultMap.put(ScientificMoveStrategy.class, 1);
                    }
                }
                if (isScientificRevertedStrategy(turnsToAnalyze.get(i), turnsToAnalyze.get(i+1))) {
                    if (resultMap.computeIfPresent(ScientificRevertedMoveStrategy.class, (k, v) -> v + 1) == null) {
                        resultMap.put(ScientificRevertedMoveStrategy.class, 1);
                    }
                }
            }

            if (resultMap.getOrDefault(ScientificMoveStrategy.class, 0) >=
                    resultMap.getOrDefault(ScientificRevertedMoveStrategy.class, 0)) {
                switchStrategy(scientificMoveStrategy);
            } else {
                switchStrategy(scientificRevertedMoveStrategy);
            }
        }
        return currentMoveStrategy;
    }

    private LinkedList<Turn> lastTopTurns(LinkedList<Turn> previousTurns) {
        if (previousTurns.size() < NUMBER_OF_TURNS_TO_ANALYZE) {
            return previousTurns;
        }
        return new LinkedList<>(
                previousTurns.stream()
                        .skip(previousTurns.size() - NUMBER_OF_TURNS_TO_ANALYZE)
                        .collect(toList()));
    }

    private boolean isScientificStrategy(Turn turn1, Turn turn2) {
        return (turn1.getResult() == Result.WIN && turn1.getPlayerMove() == turn2.getPlayerMove()) ||
                (turn1.getResult() == Result.LOSS && turn2.getPlayerMove() == winCombinations.get(turn1.getServerMove()));
    }

    private boolean isScientificRevertedStrategy(Turn turn1, Turn turn2) {
        return (turn1.getResult() == Result.WIN && turn1.getPlayerMove() != turn2.getPlayerMove()) ||
                (turn1.getResult() == Result.LOSS && turn2.getPlayerMove() ==
                        winCombinations.get(winCombinations.get(winCombinations.get(turn1.getServerMove()))));
    }
}