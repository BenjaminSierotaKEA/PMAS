package org.example.pmas.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(DatabaseException.class)
    public String handleDatabaseException(DatabaseException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errorpage";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handeIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errorpage";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleProjectNotFoundException(NotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errorpage";
    }
}
