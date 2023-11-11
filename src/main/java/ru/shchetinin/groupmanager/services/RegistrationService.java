package ru.shchetinin.groupmanager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shchetinin.groupmanager.dao.AuthorityRepository;
import ru.shchetinin.groupmanager.dao.UserRepository;
import ru.shchetinin.groupmanager.entities.Authority;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.enums.roles.RoleAdd;
import ru.shchetinin.groupmanager.exceptions.UserAlreadyExistsException;
import ru.shchetinin.groupmanager.responses.Response;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepo;
    private final AuthorityRepository authorityRepo;
    private final PasswordEncoder encoder;

    public Response addNewUser(User user) throws UserAlreadyExistsException {
        String login = user.getUsername();
        if (userRepo.findByUsername(login) != null){
            throw new UserAlreadyExistsException();
        } else {
            user.setEnabled(true);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
            authorityRepo.save(new Authority(user.getUsername(), RoleAdd.ROLE_USER.name()));
            return new Response(HttpStatus.OK.value(), "User successfully added");
        }
    }

}