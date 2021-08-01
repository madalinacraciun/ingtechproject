package com.ingtechproject.userauthentication.services;

import com.ingtechproject.userauthentication.entities.InvalidToken;
import com.ingtechproject.userauthentication.repositories.InvalidTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvalidTokenService {

    @Autowired
    private InvalidTokenRepository invalidTokenRepository;

    public InvalidToken getToken(String token) {
        return invalidTokenRepository.findByToken(token);
    }
}
