package ru.shchetinin.groupmanager.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.shchetinin.groupmanager.exceptions.ActivationCodeNotFoundException;
import ru.shchetinin.groupmanager.responses.Response;

@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Response> handleActivationCodeNotFoundException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.UNAUTHORIZED.value(), e.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }
}
