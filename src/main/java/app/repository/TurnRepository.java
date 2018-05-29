package app.repository;

import app.model.Turn;
import org.springframework.data.repository.CrudRepository;

public interface TurnRepository extends CrudRepository<Turn, Integer> {
}