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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oea.online_exam_app.Models.User;
import com.oea.online_exam_app.Services.StudentService;

/**
 *
 * @author tirth
 */
@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/register/single")
    public ResponseEntity<String> registerStudent(@RequestBody User student) {
        int result = studentService.registerStudent(student);
        if (result == 1) {
            return ResponseEntity.ok("Student registered successfully");
        } else {
            return ResponseEntity.status(500).body("Failed to register student");
        }
    }

    @SuppressWarnings("deprecation")
    @PostMapping("/register/csv")
    public ResponseEntity<String> registerStudents(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
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

    @PostMapping("/update/{userId}")
    public ResponseEntity<String> updateStudent(@PathVariable("userId") int userId, @RequestBody User updatedStudent) {
        try {
            int result = studentService.updateStudent(updatedStudent,userId);
            if (result > 1) {
                return ResponseEntity.ok("Student updated successfully");
            } else {
                return ResponseEntity.status(404).body("Student not found or update failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error"+e.toString());
        }
        
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("userId") int userId){
        try {
            int result = studentService.deleteStudent(userId);
            if (result > 1) {
                return ResponseEntity.ok("Student deleted successfully");
            } else {
                return ResponseEntity.status(404).body("Student not found or delete failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error"+e.toString());
        }
    }

}
