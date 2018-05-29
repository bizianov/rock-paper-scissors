package app;

import app.controller.GameController;
import app.model.Move;
import app.model.Result;
import app.service.game.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Before
    public void init() {
        when(gameService.nextTurn(Move.PAPER)).thenReturn(Result.DRAW);
    }

    @Test
    public void nextTurn() throws Exception {
        mockMvc.perform(get("/game/turn/paper"))
                .andExpect(status().isOk());
    }
}