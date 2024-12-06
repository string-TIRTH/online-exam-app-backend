/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IStudentService;
import com.oea.online_exam_app.Models.Role;
import com.oea.online_exam_app.Models.User;
import com.oea.online_exam_app.Repo.RoleRepo;
import com.oea.online_exam_app.Repo.UserRepo;

/**
 *
 * @author tirth
 */
@Service
public class StudentService implements IStudentService{

    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;

    @Override
    public int registerStudent(User student) {
        try {
            Role role = roleRepo.findByRole("Student");
            student.setRole(role);
            userRepo.save(student);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int registerStudents(List<User> students) {
        try {
            Role role = roleRepo.findByRole("Student");
            students.forEach(student -> student.setRole(role));
            userRepo.saveAll(students);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int updateStudent(User student,int userId) {
        try {
            User existingUser = userRepo.findById(userId).orElseThrow(()-> new IllegalArgumentException("Invalid userId"));
            if (existingUser != null) {
                existingUser.setFullName(student.getFullName());  
                existingUser.setEmail(student.getEmail());  
                existingUser.setMobileNumber(student.getMobileNumber());  
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
    public int deleteStudent(int userId) {
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

    
}
