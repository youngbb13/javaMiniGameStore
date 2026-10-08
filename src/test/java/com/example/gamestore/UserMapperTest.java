package com.example.gamestore;

import com.example.gamestore.dto.UserDto;
import com.example.gamestore.dto.UserMapper;
import com.example.gamestore.entity.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    @Test
    void testCopy() {
        User user = new User("kenzii", new BigDecimal("500"));

        UserDto dto = UserMapper.toDto(user);

        assertEquals("kenzii", dto.getNickname());
        assertEquals(new BigDecimal("500"), dto.getBalance());
    }
}
