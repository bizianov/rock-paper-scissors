package app.converter;

import app.model.Move;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public class MoveConverter implements AttributeConverter<Move, String> {

    public String convertToDatabaseColumn(Move move) {
        return move.name();
    }

    public Move convertToEntityAttribute(String moveName) {
        return Move.valueOf(moveName);
    }
}