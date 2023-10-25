package ru.shchetinin.groupmanager.contollers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shchetinin.groupmanager.models.User;
import ru.shchetinin.groupmanager.repositories.UserRepository;

@RestController("/auth")
@RequiredArgsConstructor
public class UserController {

    private UserRepository userRepository;
    @PostMapping ("/registration")
    public String registration(@RequestBody User user){
        String email = user.getEmail();

        if (userRepository.findByEmail(email) == null){
            String password = user.getPassword();
            Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(16, 16, 1, 1024, 2);
            String aCryptedPassword = argon2PasswordEncoder.encode(password);
            user.setPassword(aCryptedPassword);
            return "ok";
        } else {
            return "user with this email is exist";
        }
    }
}
