package com.example.gamestore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

// Тестуємо лише UserController, не весь додаток і не базу
@WebMvcTest(UserController.class)
public class UserControllerTest {

    // Spring сам створить MockMvc і покладе сюди
    @Autowired
    MockMvc mockMvc;

    // Замість справжнього UserService буде підробка
    @MockitoBean
    UserService userService;

    // Сценарій: юзера ще немає → очікуємо 200 і JSON
    @Test
    void addNewUser_whenUserDoesNotExist_returnsOk() throws Exception {
        // given: контролер спитає "чи є valentyn?" — кажемо "немає"
        when(userService.findUserByNickname("valentyn"))
                .thenReturn(Optional.empty());

        // when: відправляємо POST /users?nickname=valentyn&balance=300
        mockMvc.perform(post("/users")
                        .param("nickname", "valentyn") // @RequestParam nickname
                        .param("balance", "300"))      // @RequestParam balance
                // then: статус 200
                .andExpect(status().isOk())
                // then: у JSON поле nickname = valentyn
                .andExpect(jsonPath("$.nickname").value("valentyn"))
                // then: у JSON поле balance = 300
                .andExpect(jsonPath("$.balance").value(300));
    }

    // Сценарій: юзер уже є → очікуємо 400
    @Test
    void addNewUser_whenUserExists_returnsBadRequest() throws Exception {
        // given: контролер спитає "чи є valentyn?" — кажемо "так, ось він"
        when(userService.findUserByNickname("valentyn"))
                .thenReturn(Optional.of(new User("valentyn", new BigDecimal("300"))));

        // when: той самий POST
        mockMvc.perform(post("/users")
                        .param("nickname", "valentyn")
                        .param("balance", "300"))
                // then: статус 400, бо гілка "User already exists"
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserBalance_whenUserExists_returnsBalance() throws Exception {
        // given: сервіс знаходить юзера з балансом 500
        when(userService.findUserByNickname("kenzii"))
                .thenReturn(Optional.of(new User("kenzii", new BigDecimal("500"))));

        // when: GET /users/kenzii/balance
        mockMvc.perform(get("/users/kenzii/balance"))
                // then
                .andExpect(status().isOk())
                .andExpect(content().string("500"));
    }

    @Test
    void getUserBalance_whenUserDoesNotExist_returnsNotFound() throws Exception {
        // given: такого юзера немає
        when(userService.findUserByNickname("unknown"))
                .thenReturn(Optional.empty());

        // when
        mockMvc.perform(get("/users/unknown/balance"))
                // then: 404
                .andExpect(status().isNotFound());
    }

    @Test
    void addFunds_whenUserExists_returnsBalance() throws Exception {
        when(userService.addFunds("kenzii", new BigDecimal("100")))
                .thenReturn(Optional.of(new User("kenzii", new BigDecimal("600"))));

        mockMvc.perform(post("/users/kenzii/funds")
                .param("amount", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value("kenzii"))
                .andExpect(jsonPath("$.balance").value(600));
    }

    @Test
    void addFunds_whenUserDoesNotExists_returnsNotFound() throws Exception {
        when(userService.addFunds("unknown", new BigDecimal("100")))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/users/unknown/funds")
                .param("amount", "100"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addFunds_whenAmountInvalid_returnBadRequest() throws Exception {
        when(userService.addFunds("kenzii", BigDecimal.ZERO))
                .thenThrow(new InvalidAmountException("Amount must be greater than 0"));

        mockMvc.perform(post("/users/kenzii/funds")
                .param("amount", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Amount must be greater than 0"))
                .andExpect(jsonPath("$.status").value(400));
    }
}