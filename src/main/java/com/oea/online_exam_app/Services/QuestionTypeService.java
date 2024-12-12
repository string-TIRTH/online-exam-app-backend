/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IQuestionTypeService;
import com.oea.online_exam_app.Models.QuestionType;
import com.oea.online_exam_app.Repo.QuestionTypeRepo;

/**
 *
 * @author tirth
 */
@Service
public class QuestionTypeService implements IQuestionTypeService{

    @Autowired
    private QuestionTypeRepo questionTypeRepo;
    @Override
    public int createQuestionType(QuestionType questionType) {
        try {
            questionTypeRepo.save(questionType);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int createQuestionTypes(List<QuestionType> questionTypes) {
        try {
            questionTypeRepo.saveAll(questionTypes);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int updateQuestionType(QuestionType questionType,int questionId) {
       try {
            QuestionType existingQuestionType = questionTypeRepo.findById(questionId).orElseThrow(() ->new IllegalArgumentException("Invalid quesitionId"));
            if (existingQuestionType != null) {
                existingQuestionType.setQuestionTypeText(questionType.getQuestionTypeText());  
                questionTypeRepo.save(existingQuestionType);
                return existingQuestionType.getQuestionTypeId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteQuestionType(int questionId) {
        try {
            if (questionTypeRepo.existsById(questionId)) {
                questionTypeRepo.deleteById(questionId);
                return questionId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public List<QuestionType> getAllQuestionTypes() {
        try {
            List<QuestionType> questionTypes = questionTypeRepo.findAll(Sort.by(Sort.Direction.ASC,"questionTypeId"));
            return questionTypes;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }

    

}
