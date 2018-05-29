package app.service.history;

import app.model.Turn;

import java.util.LinkedList;

/**
 * Implementation of this interface is responsible for storing all the turns and returning
 * them by demand.
 */
public interface HistoryHolder {

    void addTurn(Turn turn);

    LinkedList<Turn> getPreviousTurns();
}