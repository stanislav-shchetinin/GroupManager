package ru.shchetinin.groupmanager.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.shchetinin.groupmanager.exceptions.GroupAlreadyExistException;
import ru.shchetinin.groupmanager.exceptions.NotFoundGroupDeleteException;
import ru.shchetinin.groupmanager.exceptions.NotRealCreatorException;
import ru.shchetinin.groupmanager.responses.Response;

@ControllerAdvice
public class HomeExceptionHandler {
    @ExceptionHandler({NotRealCreatorException.class})
    public ResponseEntity<Response> handleNotRealCreatorException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.FORBIDDEN.value(), "Permission denied. Is not real admin."),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({GroupAlreadyExistException.class})
    public ResponseEntity<Response> handleGroupAlreadyExistException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.CONFLICT.value(), "Group with this id is already exist."),
                HttpStatus.CONFLICT);
    }
    @ExceptionHandler({NotFoundGroupDeleteException.class})
    public ResponseEntity<Response> handleNotFoundGroupDeleteException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.NO_CONTENT.value(), "Group isn't founded."),
                HttpStatus.NO_CONTENT);
    }
}
