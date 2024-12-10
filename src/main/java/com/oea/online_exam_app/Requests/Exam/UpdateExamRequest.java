/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Requests.Exam;

import java.util.List;

import com.oea.online_exam_app.Models.Exam;

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
public class UpdateExamRequest {
    private Exam exam;
    private List<QuestionsUpdateRequest> questions;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionsUpdateRequest {
        private int newQuestionId;
        private int oldQuestionsId;
    }
}
