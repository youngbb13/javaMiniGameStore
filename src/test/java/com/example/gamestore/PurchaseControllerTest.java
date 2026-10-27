package com.example.gamestore;

import com.example.gamestore.controller.PurchaseController;
import com.example.gamestore.entity.DigitalGame;
import com.example.gamestore.entity.Genre;
import com.example.gamestore.entity.User;
import com.example.gamestore.exception.GameAlreadyOwnedException;
import com.example.gamestore.exception.NotEnoughMoneyException;
import com.example.gamestore.exception.NotFoundException;
import com.example.gamestore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    @Test
    void buyGame_whenGameAndUserExist_returnsOk() throws Exception {
        when(userService.buyGame("kenzii", "Elden Ring"))
                .thenReturn(new User("kenzii", new BigDecimal("500")));

        mockMvc.perform(post("/buy")
                        .param("nickname", "kenzii")
                        .param("gameTitle", "Elden Ring"))
                .andExpect(status().isOk());
    }

    @Test
    void buyGame_whenNotEnoughMoney_returnsBadRequest() throws Exception {
        when(userService.buyGame("kenzii", "Elden Ring"))
                .thenThrow(new NotEnoughMoneyException("Not enough money!"));

        mockMvc.perform(post("/buy")
                        .param("nickname", "kenzii")
                        .param("gameTitle", "Elden Ring"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not enough money!"));
    }

    @Test
    void buyGame_whenGameNotFound_returns404() throws Exception {
        when(userService.buyGame("kenzii", "Elden Ring"))
                .thenThrow(new NotFoundException("Game not found!"));

        mockMvc.perform(post("/buy")
                        .param("nickname", "kenzii")
                        .param("gameTitle", "Elden Ring"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Game not found!"));
    }

    @Test
    void buyGame_whenGameAlreadyOwned_returnsBadRequest() throws Exception {
        when(userService.buyGame("kenzii", "Elden Ring"))
                .thenThrow(new GameAlreadyOwnedException("Game already owned!"));

        mockMvc.perform(post("/buy")
                        .param("nickname", "kenzii")
                        .param("gameTitle", "Elden Ring"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Game already owned!"));
    }
}
