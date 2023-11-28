package ru.shchetinin.groupmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shchetinin.groupmanager.dao.UserRepository;
import ru.shchetinin.groupmanager.dto.JwtRequest;
import ru.shchetinin.groupmanager.dto.JwtResponse;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.responses.Response;
import ru.shchetinin.groupmanager.services.UserService;
import ru.shchetinin.groupmanager.utils.JwtTokenUtils;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){

        User user = userRepository.findByUsername(authRequest.getUsername());
        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            throw new UsernameNotFoundException("Uncorrected username or password");
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

}
