package ru.shchetinin.groupmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.RedirectView;
import ru.shchetinin.groupmanager.dao.AuthorityRepository;
import ru.shchetinin.groupmanager.dao.UserRepository;
import ru.shchetinin.groupmanager.entities.Authority;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.enums.roles.RoleAdd;
import ru.shchetinin.groupmanager.exceptions.ActivationCodeNotFoundException;
import ru.shchetinin.groupmanager.exceptions.UserAlreadyExistsException;
import ru.shchetinin.groupmanager.responses.Response;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserRepository userRepo;
    private final AuthorityRepository authorityRepo;
    private final PasswordEncoder encoder;
    private final MailSender mainSender;

    public Response addNewUser(User user) throws UserAlreadyExistsException {
        String login = user.getUsername();
        boolean isUserAlreadyExist = userRepo.findByUsername(login) != null;
        log.debug("login: {}, isUserAlreadyExist: {}", login, isUserAlreadyExist);
        if (isUserAlreadyExist){
            throw new UserAlreadyExistsException();
        } else {
            user.setEnabled(false);
            user.setPassword(encoder.encode(user.getPassword()));
            user.setActivationCode(UUID.randomUUID().toString());
            userRepo.save(user);
            log.debug("USER. enabled: {}, password: {}", user.isEnabled(), user.getPassword());
            Authority authority = new Authority(user.getUsername(), RoleAdd.ROLE_USER.name());
            authorityRepo.save(authority);
            log.debug("AUTHORITY. username: {}, role: {}", authority.getUsername(), authority.getAuthority());
            sendEmail(user);
            return new Response(HttpStatus.OK.value(), "User successfully added");
        }
    }

    private void sendEmail(User user){
        String email = user.getEmail();
        if (StringUtils.hasText(email)){
            String link = String.format("http://localhost:8080/activation/%s", user.getActivationCode());
            String message = String.format(
                    "Hello, %s!\n Welcome to GroupManager. Please, visit next link:%s", user.getUsername(), link
            );
            mainSender.send(email, "Activation Code", message);
        }
    }

    public RedirectView activation(String activationCode) {
        User user = userRepo.findByActivationCode(activationCode);

        log.debug(String.valueOf(user == null));

        if (user == null){
            throw new ActivationCodeNotFoundException();
        }
        user.setEnabled(true);
        user.setActivationCode(null);
        userRepo.save(user);
        return new RedirectView("/login");
    }
}