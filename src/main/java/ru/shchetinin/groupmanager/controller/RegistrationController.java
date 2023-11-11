package ru.shchetinin.groupmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.responses.Response;
import ru.shchetinin.groupmanager.services.RegistrationService;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@Tag(name="RegistrationController",
        description="Контроллер для регистрации новых пользователей")
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(
            summary = "Метод регистрации"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно добавлен в базу"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Пользователь с таким username уже существует"
            )
    })
    @PostMapping
    public Response registration(@RequestBody User user){
        return registrationService.addNewUser(user);
    }
}