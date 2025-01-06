/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IExamQuestionsService;
import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Models.ExamQuestions;
import com.oea.online_exam_app.Models.Question;
import com.oea.online_exam_app.Repo.ExamQuestionsRepo;
import com.oea.online_exam_app.Repo.ExamRepo;
import com.oea.online_exam_app.Repo.QuestionRepo;

/**
 *
 * @author tirth
 */
@Service
public class ExamQuestionsService implements IExamQuestionsService{

     @Autowired
    private ExamQuestionsRepo examQuestionsRepo;

    @Autowired
    private QuestionRepo questionsRepo;
    
    @Autowired
    private ExamRepo examRepo;


    @Override
    public int createExamQuestion(Exam exam,Question question) {
        try {
            examQuestionsRepo.save(new ExamQuestions(exam,question));
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int createExamQuestions(Exam exam,List<Question> examQuestions) {
        try {
            List<ExamQuestions> examQuestionList = new ArrayList<>();
            examQuestions.forEach(question -> {
                ExamQuestions examQuestion = new ExamQuestions();
                examQuestion.setExam(exam);
                examQuestion.setQuestion(question);
                examQuestionList.add(examQuestion);
            });
            examQuestionsRepo.saveAll(examQuestionList);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int updateExamQuestion(int examQuestionId, ExamQuestions examQuestion) {
        try {
            ExamQuestions existingExamQuestions = examQuestionsRepo.findById(examQuestionId).orElseThrow(() ->new IllegalArgumentException("Invalid examId"));
            if (existingExamQuestions != null) {
                existingExamQuestions.setQuestion(examQuestion.getQuestion());
                
                return existingExamQuestions.getExamQuestionId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteExam(int examQuestionId) {
        try {
            if (examQuestionsRepo.existsById(examQuestionId)) {
                examQuestionsRepo.deleteById(examQuestionId);
                return examQuestionId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int updateExamQuestionByIds(int examId, int oldExamQuestionId, int newQuestionId) {
        try {   
            questionsRepo.findById(newQuestionId).orElseThrow(() -> new IllegalArgumentException("Invalid questionsId"));
            examQuestionsRepo.setNewQuestionId(examId, oldExamQuestionId, newQuestionId);
            return 1;
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }
    }

    @Override
    public List<ExamQuestions> getExamQuestionByExamId(int examId) {
        try {   
            Exam exam = examRepo.findById(examId).orElseThrow(()-> new IllegalArgumentException("Invalid Exam Id"));
            List<ExamQuestions> examQuestion = examQuestionsRepo.findByExam(exam);
            
            return examQuestion;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }

    @Override
    public List<Question> getReplacementQuestions(int categoryId,int questionTypeId,int examId) {
        try {   
            Exam exam = examRepo.findById(examId).orElseThrow(()-> new IllegalArgumentException("Invalid Exam Id"));
            List<Question> replacementQuestions = examQuestionsRepo.findReplacementQuestions(categoryId,questionTypeId,examId);
            
            return replacementQuestions;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
   
}
