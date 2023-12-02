package ru.shchetinin.groupmanager.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.shchetinin.groupmanager.dao.UserRepository;
import ru.shchetinin.groupmanager.dto.JwtRequest;
import ru.shchetinin.groupmanager.dto.JwtResponse;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.exceptions.UserIsNotActiveException;
import ru.shchetinin.groupmanager.utils.JwtTokenUtils;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.lifetime}")
    private Duration tokenLifeTime;
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest,
                                                       HttpServletResponse response){

        User user = userRepository.findByUsername(authRequest.getUsername());
        if (user == null ||
                !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            throw new UsernameNotFoundException("Uncorrected username or password");
        }
        if (!user.isEnabled()){
            throw new UserIsNotActiveException("User's email is not active");
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }
}