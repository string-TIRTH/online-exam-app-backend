/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.Models.QuestionOption;

/**
 *
 * @author tirth
 */

public interface IQuestionOptionService {
    public int createQuestionOption(QuestionOption questionOption);
    public int createQuestionOptions(List<QuestionOption> questionOptions);
    public int updateQuestionOption(QuestionOption questionOption,int optionId);
    public int deleteQuestionOption(int optionId);
}
