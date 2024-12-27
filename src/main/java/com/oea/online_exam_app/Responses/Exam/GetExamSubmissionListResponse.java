/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package com.oea.online_exam_app.Responses.Exam;

import java.util.List;

import com.oea.online_exam_app.DTO.ExamSubmissionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
 /**
  *
  * @author tirth
  */
 @Data
 @AllArgsConstructor
 @NoArgsConstructor
 public class GetExamSubmissionListResponse{
    private String status;
    private String message; 
    private List<ExamSubmissionDTO> items; 
    private long itemCount; 
    private int totalMarks;
    private int totalProgrammingQuestions;
    private int totalMcqQuestions;
}
 