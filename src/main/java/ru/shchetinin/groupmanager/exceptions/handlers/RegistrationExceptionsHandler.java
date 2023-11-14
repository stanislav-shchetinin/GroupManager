package ru.shchetinin.groupmanager.exceptions.handlers;

import ru.shchetinin.groupmanager.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.shchetinin.groupmanager.responses.Response;

@ControllerAdvice
public class RegistrationExceptionsHandler {
    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<Response> handleException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.CONFLICT.value(), "User with this username already exists"),
                HttpStatus.CONFLICT);
    }
}