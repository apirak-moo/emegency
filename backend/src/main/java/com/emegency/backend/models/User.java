package com.emegency.backend.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "emegency_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "emegency_user_id")
    private UUID id;
    
    @Column(name = "emegency_user_email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "emegency_user_password", length = 100, nullable = false)
    private String password;

}
