/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IQuestionExampleService;
import com.oea.online_exam_app.Models.QuestionExample;
import com.oea.online_exam_app.Repo.QuestionExampleRepo;

/**
 *
 * @author tirth
 */
@Service
public class QuestionExampleService implements IQuestionExampleService{

    @Autowired
    private QuestionExampleRepo questionExampleRepo;
    @Override
    public int createQuestionExample(QuestionExample questionExample) {
        try {
            questionExampleRepo.save(questionExample);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int createQuestionExamples(List<QuestionExample> questionExamples) {
        try {
            questionExampleRepo.saveAll(questionExamples);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int updateQuestionExample(QuestionExample questionExample,int exampleId) {
       try {
            QuestionExample existingQuestionExample = questionExampleRepo.findById(exampleId);
            if (existingQuestionExample != null) {
                existingQuestionExample.setInputText(questionExample.getInputText());  
                existingQuestionExample.setOutputText(questionExample.getOutputText());  
                questionExampleRepo.save(existingQuestionExample);
                return existingQuestionExample.getExampleId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteQuestionExample(int exampleId) {
        try {
            if (questionExampleRepo.existsById(exampleId)) {
                questionExampleRepo.deleteById(exampleId);
                return exampleId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    
}
