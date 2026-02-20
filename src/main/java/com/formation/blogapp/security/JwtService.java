package com.formation.blogapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMS;




    public String generateToken(String email){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        Date now = new Date();
        Date expiry = new Date(System.currentTimeMillis() + expirationMS);

        return JWT.create()
                .withSubject(email)
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .sign(algorithm);
    }

    public String extractEmail(String token){

        Algorithm algorithm = Algorithm.HMAC256(secret);


        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){

       try{
           String username = extractEmail(token);
           return username != null && username.equals(userDetails.getUsername());
       }catch (JWTVerificationException ex){
           return false;
       }

    }




}
