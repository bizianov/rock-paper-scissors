package app;

import app.model.Move;
import app.model.Result;
import app.model.Turn;
import app.repository.TurnRepository;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JPATest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TurnRepository turnRepository;

    @Before
    public void init() {
        Turn turn1 = new Turn("ID-1234567890", Move.SCISSORS, Move.SCISSORS, Result.DRAW);
        Turn turn2 = new Turn("ID-123456789", Move.SCISSORS, Move.SCISSORS, Result.DRAW);
        testEntityManager.persist(turn1);
        testEntityManager.persist(turn2);
        testEntityManager.flush();
    }

    @Test
    public void persistenceTest() {
        ArrayList<Turn> turns = Lists.newArrayList(turnRepository.findAll());
        assertThat(turns, hasSize(2));
    }
}