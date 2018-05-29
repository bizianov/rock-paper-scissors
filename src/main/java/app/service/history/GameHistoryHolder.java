package app.service.history;

import app.model.Turn;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class GameHistoryHolder implements HistoryHolder {

    private final List<Turn> gameTurns;

    public GameHistoryHolder() {
        this.gameTurns = new LinkedList<>();
    }

    @Override
    public void addTurn(Turn turn) {
        gameTurns.add(turn);
    }

    @Override
    public LinkedList<Turn> getPreviousTurns() {
        return new LinkedList<>(gameTurns);
    }

}