/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IQuestionOptionService;
import com.oea.online_exam_app.Models.QuestionOption;
import com.oea.online_exam_app.Repo.QuestionOptionRepo;

/**
 *
 * @author tirth
 */
@Service
public class QuestionOptionService implements IQuestionOptionService{

     @Autowired
    private QuestionOptionRepo questionOptionRepo;
    @Override
    public int createQuestionOption(QuestionOption questionOption) {
        try {
            questionOptionRepo.save(questionOption);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int createQuestionOptions(List<QuestionOption> questionOptions) {
        try {
            questionOptionRepo.saveAll(questionOptions);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int updateQuestionOption(QuestionOption questionOption,int optionId) {
       try {
            QuestionOption existingQuestionOption = questionOptionRepo.findById(optionId);
            if (existingQuestionOption != null) {
                existingQuestionOption.setOptionText(questionOption.getOptionText());  
                existingQuestionOption.setIsCorrect(questionOption.getIsCorrect());  
                questionOptionRepo.save(existingQuestionOption);
                return existingQuestionOption.getOptionId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteQuestionOption(int optionId) {
        try {
            if (questionOptionRepo.existsById(optionId)) {
                questionOptionRepo.deleteById(optionId);
                return optionId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

   
}
