/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Requests.Question;

import java.util.List;

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
public class CreateQuestionRequest {
    private String questionText;
    private String category;
    private String questionType;
    private String difficulty;
    private List<QuestionOptionRequest> questionOptions;
    private List<QuestionExampleRequest> questionExamples;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionOptionRequest {
        private String optionText; 
        private Boolean isCorrect; 
    }

    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionExampleRequest {
        private String inputText;  
        private String outputText;
    }
}
