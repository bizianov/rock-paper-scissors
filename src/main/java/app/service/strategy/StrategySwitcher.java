package app.service.strategy;

import app.model.Result;
import app.model.Turn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static app.service.result.ResultDeterminant.winCombinations;

/**
 * The brain of the game, this class is responsible for analyzing previous turns and making decision which strategy
 * to activate to play most efficiently with an opponent.
 */
@Component
@ConfigurationProperties(prefix = "game.strategy")
public class StrategySwitcher {

    private Integer numberOfTurnsToAnalyze;

    private final ScientificMoveStrategy scientificMoveStrategy;
    private final ScientificRevertedMoveStrategy scientificRevertedMoveStrategy;
    private MoveStrategy currentMoveStrategy;

    @Autowired
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

            if (!resultMap.isEmpty()) {
                Class clazz = Collections.max(resultMap.entrySet(), Map.Entry.comparingByValue()).getKey();
                if (clazz == ScientificMoveStrategy.class) {
                    switchStrategy(scientificMoveStrategy);
                } else {
                    switchStrategy(scientificRevertedMoveStrategy);
                }
            }
        }
        return currentMoveStrategy;
    }

    private LinkedList<Turn> lastTopTurns(LinkedList<Turn> previousTurns) {
        if (previousTurns.size() < numberOfTurnsToAnalyze) {
            return previousTurns;
        }
        return new LinkedList<>(
                previousTurns.stream()
                        .skip(previousTurns.size() - numberOfTurnsToAnalyze)
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

    public void setNumberOfTurnsToAnalyze(Integer numberOfTurnsToAnalyze) {
        this.numberOfTurnsToAnalyze = numberOfTurnsToAnalyze;
    }
}