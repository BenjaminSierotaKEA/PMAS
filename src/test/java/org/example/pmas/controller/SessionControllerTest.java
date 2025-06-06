package org.example.pmas.controller;

import org.example.pmas.model.Role;
import org.example.pmas.model.User;
import org.example.pmas.service.UserService;
import org.example.pmas.util.SessionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SessionHandler sessionHandler;

    @MockitoBean
    private UserService userService;


    @Test
    void getLogInPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-login"));
    }

    @Test
    void login_successfulRedirect() throws Exception {
        User mockUser = new User();
        Role role = new Role();
        role.setName("Employee");
        mockUser.setUserID(1);
        mockUser.setEmail("user@example.com");
        mockUser.setRole(role);

        when(sessionHandler.logIn("Rebecca@example.com", "password123")).thenReturn(true);
        when(sessionHandler.getCurrentUser()).thenReturn(mockUser);

        mockMvc.perform(post("/login")
                        .param("email", "Rebecca@example.com")
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/1/user"));
    }


    @Test
    void logout_shouldRedirectToHomepage() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(sessionHandler, times(1)).logOut();

    }
}