/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IExamService;
import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Repo.ExamRepo;

/**
 *
 * @author tirth
 */
@Service
public class ExamService implements IExamService{

     @Autowired
    private ExamRepo examRepo;

    @Override
    public int createExam(Exam exam) {
        try {
            examRepo.save(exam);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateExam(Exam exam, int examId) {
        try {
            Exam existingExam = examRepo.findById(examId).orElseThrow(() ->new IllegalArgumentException("Invalid examId"));
            if (existingExam != null) {
                existingExam.setExamStartTime(exam.getExamStartTime());
                existingExam.setExamEndTime(exam.getExamEndTime());
                existingExam.setExamDurationInMinutes(exam.getExamDurationInMinutes());
                existingExam.setPassingCriteria(exam.getPassingCriteria());
                existingExam.setPassingValue(exam.getPassingValue());
                existingExam.setExamDate(exam.getExamDate());
                examRepo.save(existingExam);
                return existingExam.getExamId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteExam(int examId) {
        try {
            if (examRepo.existsById(examId)) {
                examRepo.deleteById(examId);
                return examId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

   
}
