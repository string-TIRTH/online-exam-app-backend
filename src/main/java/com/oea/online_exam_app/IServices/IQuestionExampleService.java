/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.Models.QuestionExample;

/**
 *
 * @author tirth
 */

public interface IQuestionExampleService {
    public int createQuestionExample(QuestionExample questionExample);
    public int createQuestionExamples(List<QuestionExample> questionExamples);
    public int updateQuestionExample(QuestionExample questionExample,int exampleId);
    public int deleteQuestionExample(int exampleId);
}
