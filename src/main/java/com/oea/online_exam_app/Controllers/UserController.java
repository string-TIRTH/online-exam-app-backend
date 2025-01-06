/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Controllers;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oea.online_exam_app.Enums.RoleEnum;
import com.oea.online_exam_app.Models.Role;
import com.oea.online_exam_app.Models.User;
import com.oea.online_exam_app.Repo.RoleRepo;
import com.oea.online_exam_app.Repo.UserRepo;
import com.oea.online_exam_app.Requests.Base.GetListWithPagingSearchRequest;
import com.oea.online_exam_app.Requests.User.CreateUserRequest;
import com.oea.online_exam_app.Requests.User.DeleteUserRequest;
import com.oea.online_exam_app.Requests.User.UpdateUserRequest;
import com.oea.online_exam_app.Responses.Base.GetListWithPagingSearchResponse;
import com.oea.online_exam_app.Responses.BaseResponse;
import com.oea.online_exam_app.Services.ExaminerService;
import com.oea.online_exam_app.Services.StudentService;

/**
 *
 * @author tirth
 */
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    StudentService studentService;

    @Autowired
    ExaminerService examinerService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @PostMapping("student/register/single")
    public ResponseEntity<String> registerStudent(@RequestBody CreateUserRequest request) {
        User newStudent = new User(
            request.getFullName(),
            request.getMobileNumber(),
            request.getEmail(),
            request.getPassword()
        );
        int result = studentService.registerStudent(newStudent);
        if (result == 1) {
            return ResponseEntity.ok("Student registered successfully");
        } else {
            return ResponseEntity.status(500).body("Failed to register student");
        }
    }

    @SuppressWarnings("deprecation")
    @PostMapping("student/register/csv")
    public ResponseEntity<String> registerStudents(MultipartFile file) {
        if (file==null || file.isEmpty()) {
            System.out.println("Please upload a CSV file.");
            return ResponseEntity.badRequest().body("Please upload a CSV file.");
        }
        List<User> students = new ArrayList<>();
        try (CSVParser csvParser = new CSVParser(new InputStreamReader(file.getInputStream()),
                CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                User student = new User();
                student.setFullName(record.get("fullName"));
                student.setMobileNumber(record.get("mobileNumber"));
                student.setEmail(record.get("email"));
                student.setPassword(record.get("password"));
                student.setRole(null); // You can set the role as needed

                students.add(student);
            }

            int result = studentService.registerStudents(students);
            if (result == 1) {
                return ResponseEntity.ok("All students registered successfully.");
            } else {
                return ResponseEntity.status(500).body("Failed to register students.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing the file: " + e.getMessage());
        }
    }

    @PostMapping("student/update")
    public ResponseEntity<BaseResponse> updateStudent(@RequestBody UpdateUserRequest request) {
        try {
            User user = request.getUser();
            if(studentService.updateStudent(user, user.getUserId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","question updated successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to update question"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }

    @PostMapping("student/delete")
    public ResponseEntity<BaseResponse> deleteStudent(@RequestBody DeleteUserRequest request) {
        try {
            if(studentService.deleteStudent(request.getUserId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","student deleted successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to delete student"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }

    @PostMapping("/getStudentList")
    public ResponseEntity<GetListWithPagingSearchResponse> getStudentList(@RequestBody GetListWithPagingSearchRequest request) {
        try {
            Role role = roleRepo.findByRole(RoleEnum.Student.name());
            List<User> students = studentService.getStudents(request.getPage(),request.getLimit(),request.getSearch(),role.getRoleId());
            long studentCount = userRepo.getStudentCountWithSearch(request.getSearch(),role.getRoleId());
            if(students!= null && !students.isEmpty()){
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "success","ok",students,studentCount
                ));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed","students not found",null,0
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
               "failed","Internal server error",null,0
            ));
        }


        
    }

    @PostMapping("examiner/register/single")
    public ResponseEntity<String> registerExaminer(@RequestBody CreateUserRequest request) {
        User newExaminer = new User(
            request.getFullName(),
            request.getMobileNumber(),
            request.getEmail(),
            request.getPassword()
        );
        int result = examinerService.registerExaminer(newExaminer);
        if (result == 1) {
            return ResponseEntity.ok("Examiner registered successfully");
        } else {
            return ResponseEntity.status(500).body("Failed to register examiner");
        }
    }

    
    @PostMapping("examiner/update")
    public ResponseEntity<BaseResponse> updateExaminer(@RequestBody UpdateUserRequest request) {
        try {
            User user = request.getUser();
            if(examinerService.updateExaminer(user, user.getUserId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","question updated successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to update question"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }

    @PostMapping("examiner/delete")
    public ResponseEntity<BaseResponse> deleteExaminer(@RequestBody DeleteUserRequest request) {
        try {
            if(examinerService.deleteExaminer(request.getUserId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","examiner deleted successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to delete examiner"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }

    @PostMapping("/getExaminerList")
    public ResponseEntity<GetListWithPagingSearchResponse> getExaminerList(@RequestBody GetListWithPagingSearchRequest request) {
        try {
            Role role = roleRepo.findByRole(RoleEnum.Examiner.name());
            List<User> examiners = examinerService.getExaminers(request.getPage(),request.getLimit(),request.getSearch(),role.getRoleId());
            long examinerCount = userRepo.getExaminerCountWithSearch(request.getSearch(),role.getRoleId());
            if(examiners!= null && !examiners.isEmpty()){
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "success","ok",examiners,examinerCount
                ));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed","examiners not found",null,0
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
               "failed","Internal server error",null,0
            ));
        }

    }

}
