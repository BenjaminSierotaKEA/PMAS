package org.example.pmas.controller;

import org.example.pmas.model.User;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private List<User> users;
    private User user;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        users = List.of(new User(1,
                "Jacob","email@email.com","password", 1, "jacob.jpg"));
        user = new User(1,
                "Jacob","email@email.com","password", 1, "jacob.jpg");

    }

    @Test
    void allUsers() throws Exception {
        when(userService.getAll()).thenReturn(users);

        mvc.perform(get("/user-overview"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-page"))
                .andExpect(model().attributeExists("users"));

        verify(userService).getAll();
    }

    @Test
    void userByID() throws Exception {
        when(userService.getUser(anyInt())).thenReturn(user);

        mvc.perform(get("/{id}/user", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("user-page"))
                .andExpect(model().attributeExists("user"));

        verify(userService).getUser(anyInt());
    }
}
