/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Models.ExamQuestions;
import com.oea.online_exam_app.Models.Question;

/**
 *
 * @author tirth
 */
public interface IExamQuestionsService {
    public int createExamQuestion(Exam exam, Question Question);
    public int createExamQuestions(Exam exam, List<Question> Question);
    public int updateExamQuestions(int examQuestionId, ExamQuestions examQuestion);
    public int deleteExam(int examQuestionId);
}
