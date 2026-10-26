package com.example.gamestore;

import com.example.gamestore.controller.GameController;
import com.example.gamestore.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    StoreService storeService;

    @Test
    void addNewGame_whenTitleIsEmpty_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"title":"","price":10,"genre":"RPG"}
                            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateGame_whenPriceIsMissing_returnsBadRequest() throws Exception {
        mockMvc.perform(put("/games/Elden Ring")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Elden Ring\",\"price\":null,\"genre\":\"RPG\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("price")));
    }
}
