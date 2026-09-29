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

// Тестуємо лише UserController, не весь додаток і не базу
@WebMvcTest(UserController.class)
class UserControllerTest {

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
}