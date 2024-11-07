/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IAuthService;
import com.oea.online_exam_app.Models.User;
import com.oea.online_exam_app.Repo.UserRepo;

/**
 *
 * @author tirth
 */
@Service
public class AuthService implements IAuthService{

    @Autowired
    UserRepo repo;

    @Override
    public int loginUser(String email, String password) {
        System.out.println(email);
        User u = repo.findByEmailAndPassword(email,password);
        if(u == null)
            return -1;
        return u.getUserId();
    }

}
