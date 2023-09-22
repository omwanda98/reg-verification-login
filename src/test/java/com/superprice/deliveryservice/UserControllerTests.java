package com.superprice.deliveryservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superprice.deliveryservice.users.DTO.UserRegistrationDto;
import com.superprice.deliveryservice.users.controller.UserController;
import com.superprice.deliveryservice.users.model.User;
import com.superprice.deliveryservice.users.repository.UserRepository;
import com.superprice.deliveryservice.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private MockHttpSession mockHttpSession;

    @BeforeEach
    public void setup() {
        mockHttpSession = new MockHttpSession();
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User("testuser", "password", "test@example.com");
        when(userService.createUser(any(User.class))).thenReturn(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Add more assertions as needed
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User("testuser", "password", "test@example.com");
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users/1")
                .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Add more assertions as needed
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        User user1 = new User("testuser1", "password1", "test1@example.com");
        User user2 = new User("testuser2", "password2", "test2@example.com");
        userList.add(user1);
        userList.add(user2);
        when(userService.getAllUsers()).thenReturn(userList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users")
                .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Add more assertions as needed
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User("testuser", "password", "test@example.com");
        when(userService.updateUser(any(User.class))).thenReturn(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Add more assertions as needed
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/users/1")
                .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testRegisterUser() throws Exception {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setUsername("testuser");
        registrationDto.setPassword("password");
        registrationDto.setEmail("test@example.com");

        when(userService.existsByUsername("testuser")).thenReturn(false);
        when(userService.existsByEmail("test@example.com")).thenReturn(false);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDto))
                .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Add more assertions as needed
    }
}
