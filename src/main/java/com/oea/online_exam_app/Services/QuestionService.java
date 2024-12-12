/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.Enums.QuestionTypeEnum;
import com.oea.online_exam_app.IServices.IQuestionService;
import com.oea.online_exam_app.Models.Question;
import com.oea.online_exam_app.Repo.QuestionExampleRepo;
import com.oea.online_exam_app.Repo.QuestionOptionRepo;
import com.oea.online_exam_app.Repo.QuestionRepo;

import jakarta.transaction.Transactional;

/**
 *
 * @author tirth
 */
@Service
public class QuestionService implements IQuestionService{

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private QuestionOptionRepo questionOptionRepo;

    @Autowired
    private QuestionExampleRepo questionExampleRepo;

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
    public int createQuestions(List<Question> questions) {
        try {
            questionRepo.saveAll(questions);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int updateQuestion(Question question,int questionId) {
       try {
            Question existingQuestion = questionRepo.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Invalid questionId"));
            if (existingQuestion != null) {
                existingQuestion.setQuestionText(question.getQuestionText());  
                existingQuestion.setCategory(question.getCategory());  
                existingQuestion.setDifficulty(question.getDifficulty());  
                existingQuestion.setQuestionType(question.getQuestionType());  
                questionRepo.save(existingQuestion);
                System.out.println(existingQuestion.getCategory());
                return existingQuestion.getQuestionId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    @Transactional
    public int deleteQuestion(int questionId) {
        try {
            Question question = questionRepo.findById(questionId).orElseThrow(()-> new IllegalArgumentException("Invalid questionId"));

            if (question.getQuestionType().getQuestionTypeText().equals(QuestionTypeEnum.MCQ.name())) {
                questionOptionRepo.deleteByQuestion(question);
                questionRepo.deleteById(questionId);
                return questionId;
            }else if (question.getQuestionType().getQuestionTypeText().equals(QuestionTypeEnum.Programming.name())) {
                questionExampleRepo.deleteByQuestion(question);
                questionRepo.deleteById(questionId);
                return questionId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public List<Question> getQuestions(int page,int limit,String search) {
        try {
            int offset = (page - 1) * limit;
            System.out.println(offset);
            List<Question> questions;
            if(search.trim().isBlank()){
                questions = questionRepo.getQuestionList(limit,offset);

            }else{
                questions = questionRepo.getQuestionListWithSearch(limit,offset,search);
            }
            return questions;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
}
