/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IExaminerService;
import com.oea.online_exam_app.Models.Role;
import com.oea.online_exam_app.Models.User;
import com.oea.online_exam_app.Repo.RoleRepo;
import com.oea.online_exam_app.Repo.UserRepo;
/**
 *
 * @author tirth
 */
@Service
public class ExaminerService implements IExaminerService{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    
    @Override
    public int registerExaminer(User examiner) {
        try {
            Role role = roleRepo.findByRole("Examiner");
            String hashedPassword = passwordEncoder.encode(examiner.getPassword());
            examiner.setRole(role);
            examiner.setPassword(hashedPassword);
            userRepo.save(examiner);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int registerExaminers(List<User> examiners) {
        try {
            Role role = roleRepo.findByRole("Examiner");
            examiners.forEach(examiner -> examiner.setRole(role));
            userRepo.saveAll(examiners);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

     @Override
    public int updateExaminer(User user,int userId) {
       try {
            User existingUser =  userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
            if (existingUser != null) {
                existingUser.setFullName(user.getFullName());  
                existingUser.setEmail(user.getEmail());
                existingUser.setMobileNumber(user.getMobileNumber());
                userRepo.save(existingUser);
                return existingUser.getUserId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }
    @Override
    public int deleteExaminer(int userId) {
        try {
            if (userRepo.existsById(userId)) {
                userRepo.deleteById(userId);
                return userId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public List<User> getExaminers(int page,int limit,String search,int roleId) {
        try {
            int offset = (page - 1) * limit;
            List<User> examiners;
            if(search.trim().isBlank()){
                examiners = userRepo.getExaminerList(limit,offset,roleId);

            }else{
                examiners = userRepo.getExaminerListWithSearch(limit,offset,search,roleId);
            }
            return examiners;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
    
}
