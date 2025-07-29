package com.codewithmosh.store;

import com.codewithmosh.users.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.apache.coyote.http11.Constants.a;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userService = new UserService(userRepository, userMapper, mock(PasswordEncoder.class));
    }

    @Test
    void returnSingleUser_ShouldReturnUserDto() {
        // Arrange
        var user1 = new User();
        user1.setId(1L);user1.setName("Alice"); user1.setEmail("alice@email.com"); user1.setPassword("123456");
        var user2 = new User();
        user2.setId(2L);user2.setName("Bob"); user2.setEmail("bob@email.com"); user2.setPassword("654321");
        var userDto = new UserDto(1L, "Alice", "alice@email.com", "123456");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user1));
        when(userMapper.toDto(user1)).thenReturn(userDto);

        // Act
        var result = userService.getSingleUser(1L);
        // Assert
        assertEquals(userDto.getName(), result.getName());
        System.out.println(result.getName() + " " + userDto.getName());
    }
}