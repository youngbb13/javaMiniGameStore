package com.example.gamestore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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

    @MockitoBean
    StoreService storeService;

    @Test
    void buyGame_whenGameAndUserExist_returnsOk() throws Exception {
        when(userService.findUserByNickname("kenzii"))
                .thenReturn(Optional.of(new User("kenzii", new BigDecimal("500"))));
        when(storeService.findGameByTitle("Elden Ring"))
                .thenReturn(Optional.of(new DigitalGame("Elden Ring", new BigDecimal("60"), Genre.RPG)));

        mockMvc.perform(post("/buy")
                        .param("nickname", "kenzii")
                        .param("gameTitle", "Elden Ring"))
                .andExpect(status().isOk());
    }

    @Test
    void buyGame_whenNotEnoughMoney_returnsBadRequest() throws Exception {
        when(userService.findUserByNickname("kenzii"))
                .thenReturn(Optional.of(new User("kenzii", new BigDecimal("500"))));
        when(storeService.findGameByTitle("Elden Ring"))
                .thenReturn(Optional.of(new DigitalGame("Elden Ring", new BigDecimal("60"), Genre.RPG)));
        doThrow(new NotEnoughMoneyException("Not enough money!"))
                .when(userService).tryBuy(any(), any());

        mockMvc.perform(post("/buy")
                        .param("nickname", "kenzii")
                        .param("gameTitle", "Elden Ring"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not enough money!"));
    }
}
