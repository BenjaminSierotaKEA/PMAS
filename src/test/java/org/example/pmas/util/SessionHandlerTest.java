package org.example.pmas.util;

import org.example.pmas.model.User;
import org.example.pmas.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SessionHandlerTest {

    @MockitoBean
    private UserService userService;

    private SessionHandler sessionHandler;
    private MockHttpSession session;
    private User mockUser;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        mockUser = new User();
        mockUser.setUserID(42);
        mockUser.setName("Jane Doe");
        mockUser.setEmail("jane@example.com");


        sessionHandler = new SessionHandler(userService, session);
    }

    @Test
    void getCurrentUser_shouldReturnUser() {
        session.setAttribute("user", mockUser);

        User result = sessionHandler.getCurrentUser();

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane@example.com", result.getEmail());
    }

    @Test
    void isLoggedIn_shouldReturnTrueWhenUserIsPresent() {
        session.setAttribute("user", mockUser);

        assertTrue(sessionHandler.isLoggedIn());
    }

    @Test
    void isLoggedIn_shouldReturnFalseWhenUserIsMissing() {
        assertFalse(sessionHandler.isLoggedIn());
    }


    @Test
    void logIn_shouldStoreUserInSessionAndReturnTrue() {
        when(userService.logIn("jane@example.com", "secure")).thenReturn(mockUser);

        boolean result = sessionHandler.logIn("jane@example.com", "secure");

        assertTrue(result);
        assertEquals(mockUser, session.getAttribute("user"));
        assertEquals(1800, session.getMaxInactiveInterval());
    }



    @Test
    void logOut_shouldRemoveUserFromSession() {
        session.setAttribute("user", mockUser);
        sessionHandler.logOut();

        assertNull(session.getAttribute("user"));
    }

    @Test
    void isUserOwner_shouldReturnTrueIfIDsMatch() {
        session.setAttribute("user", mockUser);

        assertTrue(sessionHandler.isUserOwner(42));
    }

    @Test
    void isUserOwner_shouldReturnFalseIfIDsDoNotMatch() {
        session.setAttribute("user", mockUser);

        assertFalse(sessionHandler.isUserOwner(99));
    }

    @Test
    void isUserOwner_shouldReturnFalseWhenNoUser() {
        assertFalse(sessionHandler.isUserOwner(42));
    }
}
