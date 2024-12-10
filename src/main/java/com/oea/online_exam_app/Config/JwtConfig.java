/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author tirth
 */
@Component
public class JwtConfig {

    @Value("${jwt.secret}")
    private final String jwtSecret = "stupidSHA256stupidSHA256stupidSHA256stupidSHA256stupidSHA256stupidSHA256";
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String getJwtSecret(){
        return jwtSecret;
    }

    public Long getJwtExpiration(){
        return jwtExpiration;
    }

}
