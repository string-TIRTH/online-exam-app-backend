/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Requests.Exam;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private LocalTime examStartTime;
    private LocalTime examEndTime;
    private int examDurationInMinutes;
    private String passingCriteria;
    private int passingValue;
    private int totalMarks;
    
    private List<QuestionsMCQ> mcqQuestions;
    private List<QuestionsPro> proQuestions;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class QuestionsMCQ{
        private Integer categoryId;
        private int noOfQuestions;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class QuestionsPro{
        private Integer difficultyId;
        private int noOfQuestions;
    }
}
