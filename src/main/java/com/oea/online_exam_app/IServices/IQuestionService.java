/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.Models.Question;

/**
 *
 * @author tirth
 */

public interface IQuestionService {
    public int createQuestion(Question question);
    public int createQuestions(List<Question> questions);
    public int updateQuestion(Question question, int questionId);
    public int deleteQuestion(int questionId);
}
