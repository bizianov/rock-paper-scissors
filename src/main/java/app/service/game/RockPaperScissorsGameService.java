package app.service.game;

import app.model.Move;
import app.model.Result;
import app.model.Score;
import app.model.Turn;
import app.repository.TurnRepository;
import app.service.result.ResultDeterminant;
import app.service.strategy.MoveStrategy;
import app.service.strategy.StrategySwitcher;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.UUID;

@Service
public class RockPaperScissorsGameService implements GameService {

    private final TurnRepository turnRepository;
    private final StrategySwitcher strategySwitcher;
    private final ResultDeterminant resultDeterminant;
    private Score score;

    @Autowired
    public RockPaperScissorsGameService(TurnRepository turnRepository,
                                        StrategySwitcher strategySwitcher,
                                        ResultDeterminant resultDeterminant) {
        this.turnRepository = turnRepository;
        this.strategySwitcher = strategySwitcher;
        this.resultDeterminant = resultDeterminant;
    }

    @Override
    public Score startGame() {
        this.score = new Score();
        return score;
    }

    @Override
    public Result nextTurn(Move playerMove) {
        LinkedList<Turn> previousTurns = Lists.newLinkedList(turnRepository.findAll());
        MoveStrategy moveStrategy = strategySwitcher.getCurrentMoveStrategy(previousTurns);
        Move serverMove = moveStrategy.nextMove(previousTurns);
        Turn turn = new Turn(UUID.randomUUID().toString(), serverMove, playerMove);
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
        turnRepository.save(turn);
        return result;
    }

    @Override
    public Score stopGame() {
        return score;
    }
}