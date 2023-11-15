package ru.shchetinin.groupmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RegistrationService {
    private final UserRepository userRepo;
    private final AuthorityRepository authorityRepo;
    private final PasswordEncoder encoder;

    public Response addNewUser(User user) throws UserAlreadyExistsException {
        String login = user.getUsername();
        boolean isUserAlreadyExist = userRepo.findByUsername(login) != null;
        log.debug("login: {}, isUserAlreadyExist: {}", login, isUserAlreadyExist);
        if (isUserAlreadyExist){
            throw new UserAlreadyExistsException();
        } else {
            user.setEnabled(true);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
            log.debug("USER. enabled: {}, password: {}", user.isEnabled(), user.getPassword());
            Authority authority = new Authority(user.getUsername(), RoleAdd.ROLE_USER.name());
            authorityRepo.save(authority);
            log.debug("AUTHORITY. username: {}, role: {}", authority.getUsername(), authority.getAuthority());
            return new Response(HttpStatus.OK.value(), "User successfully added");
        }
    }

}