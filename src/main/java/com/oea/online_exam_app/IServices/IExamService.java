/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.DTO.QuestionsDTO;
import com.oea.online_exam_app.Models.Exam;

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
     public QuestionsDTO getExamQuestions(int examId,int userId);
}
