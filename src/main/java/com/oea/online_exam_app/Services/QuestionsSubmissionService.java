/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.DTO.MCQSubmissionDTO;
import com.oea.online_exam_app.IServices.IQuestionSubmissionService;
import com.oea.online_exam_app.Models.QuestionSubmission;
import com.oea.online_exam_app.Repo.QuestionSubmissionRepo;

/**
 *
 * @author tirth
 */
@Service
public class QuestionsSubmissionService implements IQuestionSubmissionService{

     @Autowired
    private QuestionSubmissionRepo questionSubmissionRepo;

    @Override
    public int createQuestionSubmission(QuestionSubmission questionSubmission) {
        try {
            questionSubmissionRepo.save(questionSubmission);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateQuestionSubmission(QuestionSubmission questionSubmission, int questionSubmissionId) {
        try {
            QuestionSubmission existingQuestionSubmission = questionSubmissionRepo.findById(questionSubmissionId).orElseThrow(() ->new IllegalArgumentException("Invalid questionSubmissionId"));
            if (existingQuestionSubmission != null) {
                existingQuestionSubmission.setSelectedOption(questionSubmission.getSelectedOption());
                return existingQuestionSubmission.getQuestionSubmissionId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteQuestionSubmission(int questionSubmissionId) {
        try {
            if (questionSubmissionRepo.existsById(questionSubmissionId)) {
                questionSubmissionRepo.deleteById(questionSubmissionId);
                return questionSubmissionId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }
    @Override
        public List<MCQSubmissionDTO> getQuestionSubmissions(int examId,int page,int limit,String search) {
            try {
                List<MCQSubmissionDTO> questionSubmissionDTOs = new ArrayList<>();
                List<QuestionSubmission> questionSubmissions;
                int offset = (page - 1) * limit;
                if(search.trim().isBlank()){
                    questionSubmissions = questionSubmissionRepo.getQuestionSubmissionList(examId, limit, offset);

                }else{
                    questionSubmissions = questionSubmissionRepo.getQuestionSubmissionListWithSearch(examId,limit,offset,search);
                }
                for (QuestionSubmission questionSub : questionSubmissions) {
        
                    questionSubmissionDTOs.add(new MCQSubmissionDTO(questionSub.getQuestionSubmissionId(),questionSub.getQuestion().getQuestionText(),questionSub.getSelectedOption().getOptionText(),questionSub.getIsCorrect()));
                }
                System.out.println(questionSubmissionDTOs);
                return questionSubmissionDTOs;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }
    
}
