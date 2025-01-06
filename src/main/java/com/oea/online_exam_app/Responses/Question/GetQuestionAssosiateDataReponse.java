/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package com.oea.online_exam_app.Responses.Question;

 import java.util.List;

import com.oea.online_exam_app.Models.Category;
import com.oea.online_exam_app.Models.Difficulty;
import com.oea.online_exam_app.Models.QuestionType;

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
 public class GetQuestionAssosiateDataReponse {
     private String status;
     private String message; 
     private List<Category> categories; 
     private List<Difficulty> difficulties; 
     private List<QuestionType> questionTypes; 
}
 