package ru.shchetinin.groupmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shchetinin.groupmanager.enums.Role;
import ru.shchetinin.groupmanager.models.User;
import ru.shchetinin.groupmanager.repositories.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean createUser(User user){
        if (userRepository.findByEmail(user.getEmail()) != null){
            return false;
        }
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        log.info("Create new User with email: {}", user.getEmail());
        return true;
    }
}
