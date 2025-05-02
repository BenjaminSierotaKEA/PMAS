package org.example.pmas.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ConnectionException.class)
    public String handleDatabaseException(ConnectionException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errorpage";
    }
}
