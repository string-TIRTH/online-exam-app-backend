/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.DTO.ProSubmissionDTO;
import com.oea.online_exam_app.IServices.IProgrammingSubmissionService;
import com.oea.online_exam_app.Models.ProgrammingSubmission;
import com.oea.online_exam_app.Repo.ProgrammingSubmissionRepo;

/**
 *
 * @author tirth
 */
@Service
public class ProgrammingSubmissionService implements IProgrammingSubmissionService{

     @Autowired
    private ProgrammingSubmissionRepo programmingSubmissionRepo;

    @Override
    public int createProgrammingSubmission(ProgrammingSubmission programmingSubmission) {
        try {
            programmingSubmissionRepo.save(programmingSubmission);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateProgrammingSubmission(ProgrammingSubmission programmingSubmission, int programmingSubmissionId) {
        try {
            ProgrammingSubmission existingProgrammingSubmission = programmingSubmissionRepo.findById(programmingSubmissionId).orElseThrow(() ->new IllegalArgumentException("Invalid programmingSubmissionId"));
            if (existingProgrammingSubmission != null) {
                existingProgrammingSubmission.setSubmittedCode(programmingSubmission.getSubmittedCode());
                return existingProgrammingSubmission.getProgrammingSubmissionId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteProgrammingSubmission(int programmingSubmissionId) {
        try {
            if (programmingSubmissionRepo.existsById(programmingSubmissionId)) {
                programmingSubmissionRepo.deleteById(programmingSubmissionId);
                return programmingSubmissionId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public List<ProSubmissionDTO> getQuestionSubmissions(int examId,int page,int limit,String search) {
        try {
            List<ProSubmissionDTO> questionSubmissionDTOs = new ArrayList<>();
            List<ProgrammingSubmission> programmingSubmissions;
            int offset = (page - 1) * limit;
            System.out.println("Search: "+search+"ExamId: "+examId);
            if(search.trim().isBlank()){
                programmingSubmissions = programmingSubmissionRepo.getProgrammingSubmissionList(examId, limit, offset);

            }else{
                programmingSubmissions = programmingSubmissionRepo.getProgrammingSubmissionListWithSearch(examId,limit,offset,search);
            }
            for (ProgrammingSubmission proSub : programmingSubmissions) {
                questionSubmissionDTOs.add(new ProSubmissionDTO(proSub.getProgrammingSubmissionId(),proSub.getQuestion().getQuestionText(),proSub.getSubmittedCode(),proSub.getIsCorrect()==null?-1:proSub.getIsCorrect()==true?1:0));   
            }
            System.out.println(questionSubmissionDTOs);
            return questionSubmissionDTOs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public int submitCodeReview(int programmingQuestionId,Boolean isCorrect) {
        try {
            System.out.println(
                "ProgrammingQuestionId: "+programmingQuestionId+
                "isCorrect: "+isCorrect
            );
            ProgrammingSubmission existingProgrammingSubmission = programmingSubmissionRepo.findById(programmingQuestionId).orElseThrow(() ->new IllegalArgumentException("Invalid programmingSubmissionId"));
            if (existingProgrammingSubmission != null) {
                existingProgrammingSubmission.setIsCorrect(isCorrect);
                programmingSubmissionRepo.save(existingProgrammingSubmission);
                return existingProgrammingSubmission.getProgrammingSubmissionId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }
}
