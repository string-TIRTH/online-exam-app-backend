/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oea.online_exam_app.Models.User;

/**
 *
 * @author tirth
 */
@Repository
public interface UserRepo extends JpaRepository<User,Integer>{
    User findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    Optional<User> findById(int userId);
}
