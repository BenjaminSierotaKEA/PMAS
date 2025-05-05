package org.example.pmas.controller;

import org.example.pmas.model.User;
import org.example.pmas.util.SessionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SessionHandler sessionHandler;

    @Test
    void getLogInPage() throws Exception {
        mockMvc.perform(get("/session/user-login"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-login"));
    }

    @Test
    void login_successfulRedirect() throws Exception {
        User mockUser = new User();
        mockUser.setUserID(1);
        mockUser.setEmail("user@example.com");

        when(sessionHandler.logIn("Rebecca@example.com", "password123")).thenReturn(true);
        when(sessionHandler.getCurrentUser()).thenReturn(mockUser);

        mockMvc.perform(post("/session/login")
                        .param("email", "Rebecca@example.com")
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/session/1/user"));
    }

    @Test
    void userByID_valid() throws Exception {
        User mockUser = new User();
        mockUser.setUserID(1);
        mockUser.setName("Test User");
        mockUser.setEmail("test@example.com");

        when(sessionHandler.getCurrentUser()).thenReturn(mockUser);

        mockMvc.perform(get("/session/1/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-page"))
                .andExpect(model().attributeExists("user"));
    }
}