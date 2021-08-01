package com.ingtechproject.userauthentication.repositories;

import com.ingtechproject.userauthentication.entities.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Integer> {

    InvalidToken findByToken(String token);
}
