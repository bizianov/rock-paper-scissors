package service.game;

import model.Move;
import model.Result;
import model.Score;
import model.Turn;
import service.history.HistoryHolder;
import service.result.ResultDeterminant;
import service.strategy.MoveStrategy;
import service.strategy.StrategySwitcher;

public class RockPaperScissorsGameService implements GameService {

    private final StrategySwitcher strategySwitcher;
    private final HistoryHolder historyHolder;
    private final ResultDeterminant resultDeterminant;
    private Score score;

    public RockPaperScissorsGameService(StrategySwitcher strategySwitcher,
                                        HistoryHolder historyHolder,
                                        ResultDeterminant resultDeterminant) {
        this.strategySwitcher = strategySwitcher;
        this.historyHolder = historyHolder;
        this.resultDeterminant = resultDeterminant;
    }

    @Override
    public void startGame() {
        this.score = new Score();
    }

    @Override
    public Result nextTurn(Move playerMove) {
        MoveStrategy moveStrategy = strategySwitcher.getCurrentMoveStrategy(historyHolder.getPreviousTurns());
        Move serverMove = moveStrategy.nextMove(historyHolder.getPreviousTurns());
        Turn turn = new Turn(serverMove, playerMove);
        return processTurn(turn);
    }

    private Result processTurn(Turn turn) {
        Result result = resultDeterminant.determine(turn);
        turn.setResult(result);
        if (result == Result.WIN) {
            score.playerScored();
        }
        if (result == Result.LOSS) {
            score.serverScored();
        }
        historyHolder.addTurn(turn);
        return result;
    }

    @Override
    public Score stopGame() {
        return score;
    }
}