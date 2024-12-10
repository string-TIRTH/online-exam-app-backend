/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Utils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oea.online_exam_app.Config.JwtConfig;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *
 * @author tirth
 */
@Component
public class JwtUtil {

    @Autowired
    JwtConfig jwtConfig;

    // private final String jwtSecret = "stupidSHA256stupidSHA256stupidSHA256stupidSHA256stupidSHA256stupidSHA256";

    // private final long jwtExpiration = 3600000;

    private Key getSigningKey() {
    byte[] keyBytes = Base64.getEncoder().encode(jwtConfig.getJwtSecret().getBytes());
    return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
}


    public String generateToken(String email,String role) {
        try {
            return Jwts.builder()
                    .setSubject(email)
                    .claim("role",role)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getJwtExpiration()))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();
        } catch (JwtException e) {
            System.out.println("Error generating token: " + e.getMessage());
            return null; 
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
