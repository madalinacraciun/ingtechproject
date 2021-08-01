package com.ingtechproject.userauthentication.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "invalid_tokens")
public class InvalidToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invalid_token", unique = true, nullable = false)
    private Integer id;

    @Column(name = "token", unique = true, nullable = false)
    private String token;
}
