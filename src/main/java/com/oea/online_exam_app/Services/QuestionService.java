/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IQuestionService;
import com.oea.online_exam_app.Models.Question;
import com.oea.online_exam_app.Repo.QuestionRepo;

/**
 *
 * @author tirth
 */
@Service
public class QuestionService implements IQuestionService{

    @Autowired
    private QuestionRepo questionRepo;
    @Override
    public int createQuestion(Question question) {
        try {
            questionRepo.save(question);
            return question.getQuestionId();
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
        
    }

    @Override
    public int createQuestions(List<Question> questionTypes) {
        try {
            questionRepo.saveAll(questionTypes);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int updateQuestion(Question questionType,int questionId) {
       try {
            Question existingQuestion = questionRepo.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Invalid questionId"));
            if (existingQuestion != null) {
                existingQuestion.setQuestionText(questionType.getQuestionText());  
                questionRepo.save(existingQuestion);
                return existingQuestion.getQuestionId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteQuestion(int questionId) {
        try {
            if (questionRepo.existsById(questionId)) {
                questionRepo.deleteById(questionId);
                return questionId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }
}
