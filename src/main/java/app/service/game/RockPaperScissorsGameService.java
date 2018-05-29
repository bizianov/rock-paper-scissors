package app.service.game;

import app.model.Move;
import app.model.Result;
import app.model.Score;
import app.model.Turn;
import app.service.history.HistoryHolder;
import app.service.result.ResultDeterminant;
import app.service.strategy.MoveStrategy;
import app.service.strategy.StrategySwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RockPaperScissorsGameService implements GameService {

    private final StrategySwitcher strategySwitcher;
    private final HistoryHolder historyHolder;
    private final ResultDeterminant resultDeterminant;
    private Score score;

    @Autowired
    public RockPaperScissorsGameService(StrategySwitcher strategySwitcher,
                                        HistoryHolder historyHolder,
                                        ResultDeterminant resultDeterminant) {
        this.strategySwitcher = strategySwitcher;
        this.historyHolder = historyHolder;
        this.resultDeterminant = resultDeterminant;
    }

    @Override
    public Score startGame() {
        this.score = new Score();
        return score;
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