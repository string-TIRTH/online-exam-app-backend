/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.Models.QuestionType;

/**
 *
 * @author tirth
 */

public interface IQuestionTypeService {
    public int createQuestionType(QuestionType questionType);
    public int createQuestionTypes(List<QuestionType> questionTypes);
    public int updateQuestionType(QuestionType questionType,int questionId);
    public int deleteQuestionType(int questionId);
    public List<QuestionType> getAllQuestionTypes();
    public List<QuestionType> getQuestionTypes(int page,int limit,String search);
}
