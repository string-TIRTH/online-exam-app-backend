/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Requests.Exam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author tirth
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExamRequest {

    private String examCode;
    private LocalDate examDate;
    private LocalDateTime examStartTime;
    private LocalDateTime examEndTime;
    private int examDurationInMinutes;
    private int passingCriteria;
    private int passingValue;
    private int totalMarks;
    
    private List<CategoryQuestion> categoryWiseQuestions;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class CategoryQuestion{
        private int categoryId;
        private int noOfQuestions;
    }
}
