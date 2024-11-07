/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oea.online_exam_app.Models.User;
/**
 *
 * @author tirth
 */
@Service
public interface IUserService {
    public User getUserById(int id);
    public List<User> getAllUsers();
    public List<User> getUserByCol(String col);
}
