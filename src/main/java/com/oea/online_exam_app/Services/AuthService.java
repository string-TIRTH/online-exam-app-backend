/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IAuthService;
import com.oea.online_exam_app.Models.User;
import com.oea.online_exam_app.Repo.UserRepo;
import com.oea.online_exam_app.Utils.JwtUtil;

/**
 *
 * @author tirth
 */
@Service
public class AuthService implements IAuthService{

    @Autowired
    UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil
    ;
    @Override
    public String loginUser(String email, String password) {
        User user = userRepo.findByEmail(email).orElseThrow(()-> new InvalidParameterException("Invalid Email"));
        String role = user.getRole().getRole();
        System.out.println(password);
        System.out.println(user.getPassword());
        if (password.equals(user.getPassword())) {
            System.out.println(true);
            String token = jwtUtil.generateToken(email,role);
            System.out.println(token);
            return token;
        }
        throw new InvalidParameterException("Invalid credentials");
    }

}
