/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.DTO.ExamSubmissionDTO;
import com.oea.online_exam_app.IServices.IExamSubmissionService;
import com.oea.online_exam_app.Models.ExamSubmission;
import com.oea.online_exam_app.Repo.ExamSubmissionRepo;

/**
 *
 * @author tirth
 */
@Service
public class ExamSubmissionService implements IExamSubmissionService{

     @Autowired
    private ExamSubmissionRepo examSubmissionRepo;

    @Override
    public int createExamSubmission(ExamSubmission examSubmission) {
        try {
            examSubmissionRepo.save(examSubmission);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateExamSubmission(ExamSubmission examSubmission, int examSubmissionId) {
        try {
            ExamSubmission existingExamSubmission = examSubmissionRepo.findById(examSubmissionId).orElseThrow(() ->new IllegalArgumentException("Invalid examSubmissionId"));
            if (existingExamSubmission != null) {
                existingExamSubmission.setExamStartTime(examSubmission.getExamStartTime());
                existingExamSubmission.setExamEndTime(examSubmission.getExamEndTime());
                existingExamSubmission.setScoredMarks(examSubmission.getScoredMarks());
                existingExamSubmission.setExamStatus(examSubmission.getExamStatus());
                return existingExamSubmission.getExamSubmissionId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteExamSubmission(int examSubmissionId) {
        try {
            if (examSubmissionRepo.existsById(examSubmissionId)) {
                examSubmissionRepo.deleteById(examSubmissionId);
                return examSubmissionId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public List<ExamSubmissionDTO> getExamSubmissions(int examId,int page,int limit,String search) {
        try {
            List<ExamSubmissionDTO> examSubmissionDTOs = new ArrayList<>();
            List<ExamSubmission> examSubmissions;
            int offset = (page - 1) * limit;
            if(search.trim().isBlank()){
                examSubmissions = examSubmissionRepo.getExamSubmissionList(examId, limit, offset);

            }else{
                examSubmissions = examSubmissionRepo.getExamSubmissionListWithSearch(examId,limit,offset,search);
            }
            for (ExamSubmission examSub : examSubmissions) {
    
                examSubmissionDTOs.add(new ExamSubmissionDTO(examSub.getExamSubmissionId(),examSub.getUser().getFullName(),examSub.getUser().getEmail(),examSub.getScoredMarks(),(int) Duration.between(examSub.getExamStartTime(),examSub.getExamEndTime()).toMinutes()));
            }
            return examSubmissionDTOs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
