/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.DTO;

import java.util.List;

import com.oea.online_exam_app.Models.QuestionSubmissionStatus;

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
public class QuestionsDTO {
    private List<MCQQuestion> questionsMCQ;
    private List<ProgrammingQuestion> questionsPro;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class MCQQuestion {
        private int questionSubmissionId;
        private String questionText;
        private QuestionSubmissionStatus questionSubmissionStatus;
        private List<QuestionOption> options;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static public class QuestionOption {
            private int optionId;
            private String optionText;
            private boolean isSelected;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class ProgrammingQuestion {
        private int programmingSubmissionId;
        private String questionText;
        private String submittedCode;
        private List<ProgrammingExample> examples;
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static public class ProgrammingExample {
            private int exampleId;
            private String inputText;
            private String outputText;
        }
    }
}