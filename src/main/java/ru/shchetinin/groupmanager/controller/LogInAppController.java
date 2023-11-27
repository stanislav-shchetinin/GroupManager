package ru.shchetinin.groupmanager.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.groupmanager.dao.UserRepository;
import ru.shchetinin.groupmanager.entities.User;

@Controller
@Tag(name="LogInAppController",
        description="Контроллер для выдачи представлений на вход/регистрацию")
@RequiredArgsConstructor
public class LogInAppController {

    /*private final UserRepository userRepository;
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        String username = user.getUsername();
        User userInBase = userRepository.findByUsername(username);

        if (userInBase != null && userInBase.getPassword().equals(user.getPassword())){
            return
        }

        return "login";
    }*/

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}