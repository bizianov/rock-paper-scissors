package app.converter;

import app.model.Result;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public class ResultConverter implements AttributeConverter<Result, String> {

    public String convertToDatabaseColumn(Result result) {
        return result.name();
    }

    public Result convertToEntityAttribute(String resultName) {
        return Result.valueOf(resultName);
    }
}