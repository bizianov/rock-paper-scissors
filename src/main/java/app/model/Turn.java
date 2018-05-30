package app.model;

import app.converter.MoveConverter;
import app.converter.ResultConverter;

import javax.persistence.*;

/**
 * Describes one round of play which consists of player move, server move and result.
 * Move is described in {@link app.model.Move}
 * Result is described in {@link app.model.Result}
 */
@Entity
@Table(name = "turns")
public class Turn {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(40)")
    private String id;

    @Column(name = "server_move", columnDefinition = "VARCHAR(10)")
    @Convert(converter = MoveConverter.class)
    private Move serverMove;

    @Column(name = "player_move", columnDefinition = "VARCHAR(10)")
    @Convert(converter = MoveConverter.class)
    private Move playerMove;

    @Column(name = "result", columnDefinition = "VARCHAR(10)")
    @Convert(converter = ResultConverter.class)
    private Result result;

    public Turn() {
    }

    public Turn(String id, Move serverMove, Move playerMove) {
        this.id = id;
        this.serverMove = serverMove;
        this.playerMove = playerMove;
    }

    public Turn(String id, Move serverMove, Move playerMove, Result result) {
        this.id = id;
        this.serverMove = serverMove;
        this.playerMove = playerMove;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public Move getServerMove() {
        return serverMove;
    }

    public Move getPlayerMove() {
        return playerMove;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}