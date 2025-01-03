/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.DTO.ExamResultDetailDTO;
import com.oea.online_exam_app.DTO.QuestionsDTO;
import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Models.User;

/**
 *
 * @author tirth
 */
public interface IExamService {
    public int createExam(Exam exam);
    public int updateExam(Exam exam, int examId);
    public int deleteExam(int examId);
    public Exam getExamById(int examId);
    public List<Exam> getAllExams(int page,int limit);
    public QuestionsDTO getExamQuestions(Exam exam, User user,String ipAddress,String macAddress);
    public int submitCode(int programmingSubmissionId,String code);
    public int submitExam(int examSubmissionId);
    public List<Exam> getExams(int page,int limit,String search);
    public int updateSelectedOption(int questionSubmissionId,int optionId, int statusId) ;
    public List<ExamResultDetailDTO> getExamResultDetails(int page,int limit,String search);
    
}
