package ru.shchetinin.groupmanager.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Tag(name="LogInAppController",
        description="Контроллер для выдачи представлений на вход/регистрацию")
public class LogInAppController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}