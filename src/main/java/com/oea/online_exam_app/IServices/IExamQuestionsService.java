/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Models.Question;

/**
 *
 * @author tirth
 */
public interface IExamQuestionsService {
    public int createExamQuestion(Question question,Exam exam);
    public int createExamQuestions(List<Question> questions,Exam exam);
    public int updateExamQuestions(int examQuestionId, Question question);
    public int deleteExam(int examQuestionId);
}
