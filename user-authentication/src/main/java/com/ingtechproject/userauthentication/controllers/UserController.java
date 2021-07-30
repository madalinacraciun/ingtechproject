package com.ingtechproject.userauthentication.controllers;

import com.ingtechproject.userauthentication.dtos.AuthenticationDTO;
import com.ingtechproject.userauthentication.entities.User;
import com.ingtechproject.userauthentication.repositories.UserRepository;
import com.ingtechproject.userauthentication.security.JwtSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtSecurityConfig jwtSecurityConfig;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User addedUser ) {

        if (addedUser.getUsername().isEmpty() || addedUser.getPassword().isEmpty()) {
            LOGGER.warn("Username or password not provided!");
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        User u = userRepository.findUserByUsername(addedUser.getUsername());
        if (u != null) {
            LOGGER.warn("User with username " + addedUser.getUsername() + " already exists!");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = bCryptPasswordEncoder.encode(addedUser.getPassword());
        addedUser.setPassword(hashedPassword);

        try {
            userRepository.save(addedUser);
            return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Cannot save user with username " + addedUser.getUsername(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthenticationDTO auth) {
        if (auth.getUsername().isEmpty() || auth.getPassword().isEmpty()) {
            return "Username or password not provided!";
        }

        User user = userRepository.findUserByUsername(auth.getUsername());
        if (user == null) {
            return "User not found!";
        }

        if (bCryptPasswordEncoder.matches(auth.getPassword(), user.getPassword())) {
            LOGGER.info("User with username " + auth.getUsername() + " was successfully authenticated at " + new Date().toString());
            return jwtSecurityConfig.generateToken(auth.getUsername());
        } else {
            return "Passwords do not match!";
        }
    }

    @GetMapping("/user-details")
    public ResponseEntity<User> getUserDetails() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
