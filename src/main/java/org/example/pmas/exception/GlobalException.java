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

    @ExceptionHandler(IllegalArgumentException.class)
    public String handeIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errorpage";
    }

    @ExceptionHandler(WrongInputException.class)
    public String handleWrongInputException(WrongInputException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errorpage";
    }

    @ExceptionHandler(SubProjectNotFoundException.class)
    public String handleSubProjectNotFoundException(SubProjectNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errorpage";
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public String handleProjectNotFoundException(ProjectNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errorpage";
    }
}
