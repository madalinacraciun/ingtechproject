package com.ingtechproject.userauthentication.controllers;

import com.ingtechproject.userauthentication.entities.User;
import com.ingtechproject.userauthentication.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

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
    public ResponseEntity<User> login(@RequestBody User user) {
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            LOGGER.warn("Username or password not provided!");
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        User u = userRepository.findUserByUsername(user.getUsername());
        if (u == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (bCryptPasswordEncoder.matches(user.getPassword(), u.getPassword())) {
            return new ResponseEntity<>(u, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user-details")
    public ResponseEntity<User> getUserDetails() {
        return new ResponseEntity<>(null, null);
    }
}
