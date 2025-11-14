package ru.leti.wise.task.profile.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "password_recovery")
public class PasswordRecoveryEntity {

    @Id
    @Column(name = "recovery_token")
    UUID recoveryToken;

    @Column(name = "email")
    String email;

    @Column(name = "created_at")
    Timestamp createdAt;

    @Column(name = "expires_at")
    Timestamp expiresAt;
}
