/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    
    @Override
    public int registerStudent(User student) {
        try {
            Role role = roleRepo.findByRole("Student");
            String hashedPassword = passwordEncoder.encode(student.getPassword());
            student.setRole(role);
            student.setPassword(hashedPassword);
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
    public int updateStudent(User user,int userId) {
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

    @Override
    public List<User> getStudents(int page,int limit,String search,int roleId) {
        try {
            int offset = (page - 1) * limit;
            List<User> students;
            if(search.trim().isBlank()){
                students = userRepo.getStudentList(limit,offset,roleId);

            }else{
                students = userRepo.getStudentListWithSearch(limit,offset,search,roleId);
            }
            return students;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
    
}
