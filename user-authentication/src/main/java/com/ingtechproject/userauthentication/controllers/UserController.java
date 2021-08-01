package com.ingtechproject.userauthentication.controllers;

import com.ingtechproject.userauthentication.dtos.AuthenticationDTO;
import com.ingtechproject.userauthentication.entities.InvalidToken;
import com.ingtechproject.userauthentication.entities.User;
import com.ingtechproject.userauthentication.repositories.InvalidTokenRepository;
import com.ingtechproject.userauthentication.security.JwtSecurityConfig;
import com.ingtechproject.userauthentication.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Objects;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserService userService;

    @Autowired
    InvalidTokenRepository invalidTokenRepository;

    @Autowired
    JwtSecurityConfig jwtSecurityConfig;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user ) {

        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            LOGGER.warn("Username or password not provided!");
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        User u = userService.getUserByUsername(user.getUsername());
        if (u != null) {
            LOGGER.warn("User with username " + user.getUsername() + " already exists!");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        try {
            User addedUser = userService.saveUser(user);
            return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Cannot save user with username " + user.getUsername(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationDTO auth) {
        if (auth.getUsername().isEmpty() || auth.getPassword().isEmpty()) {
            return new ResponseEntity<>("Username or password not provided!", HttpStatus.NO_CONTENT);
        }

        User user = userService.getUserByUsername(auth.getUsername());
        if (user == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.UNAUTHORIZED);
        }

        if (bCryptPasswordEncoder.matches(auth.getPassword(), user.getPassword())) {
            LOGGER.info("User with username " + auth.getUsername() + " was successfully authenticated at " + new Date().toString());
            return new ResponseEntity<>(jwtSecurityConfig.generateToken(auth.getUsername()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad credentials!", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/user-details")
    public ResponseEntity<User> getUserDetails() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword("********");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/log-out")
    public void logout() {
        String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getHeader("Authorization").substring(7);
        InvalidToken invalidToken = new InvalidToken();
        invalidToken.setToken(token);
        invalidTokenRepository.save(invalidToken);
    }
}
