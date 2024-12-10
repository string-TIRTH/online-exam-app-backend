/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IExamStatusService;
import com.oea.online_exam_app.Models.ExamStatus;
import com.oea.online_exam_app.Repo.ExamStatusRepo;

/**
 *
 * @author tirth
 */
@Service
public class ExamStatusService implements IExamStatusService{

     @Autowired
    private ExamStatusRepo examStatusRepo;

    @Override
    public int createExamStatus(ExamStatus examStatus) {
        try {
            examStatusRepo.save(examStatus);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateExamStatus(ExamStatus examStatus, int examStatusId) {
        try {
            ExamStatus existingExamStatus = examStatusRepo.findById(examStatusId).orElseThrow(() ->new IllegalArgumentException("Invalid examStatusId"));
            if (existingExamStatus != null) {
                existingExamStatus.setExamStatusText(examStatus.getExamStatusText());  
                examStatusRepo.save(existingExamStatus);
                return existingExamStatus.getExamStatusId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteExamStatus(int examStatusId) {
        try {
            if (examStatusRepo.existsById(examStatusId)) {
                examStatusRepo.deleteById(examStatusId);
                return examStatusId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

   
}
