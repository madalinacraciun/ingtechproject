package com.ingtechproject.userauthentication.user;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(path="/app")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Autowired
    UserRepository userRepository;

    @PostMapping("/user/signup")
    public String signup(@RequestBody User addedUser ) {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            if (user.equals(addedUser)) {
                LOGGER.warning("User with username " + addedUser.getUsername() + " already exists!");
                return "User with username " + addedUser.getUsername() + " already exists!";
            }
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(addedUser.getPassword());
        addedUser.setPassword(hashedPassword);

        userRepository.save(addedUser);
        return "Signup was successfully!";
    }
}
