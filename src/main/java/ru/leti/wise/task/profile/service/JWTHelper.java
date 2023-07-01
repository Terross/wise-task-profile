package ru.leti.wise.task.profile.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.model.ProfileEntity;

import java.util.Base64;

@Component
public class JWTHelper {

    @Value("${security.secret}")
    String secret;

    private static final String ROLE = "role";

    public String generateToken(ProfileEntity profile) {
        Claims claims = Jwts.claims().setSubject(profile.getEmail());
        claims.put(ROLE, profile.getProfileRole());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secret.getBytes()))
                .compact();
    }
}
