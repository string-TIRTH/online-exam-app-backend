/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.DTO.ExamSubmissionDTO;
import com.oea.online_exam_app.Models.ExamSubmission;

/**
 *
 * @author tirth
 */
public interface IExamSubmissionService {
    public int createExamSubmission(ExamSubmission examSubmission);
    public int updateExamSubmission(ExamSubmission examSubmission, int examSubmissionId);
    public int deleteExamSubmission(int examSubmissionId);
    public List<ExamSubmissionDTO> getExamSubmissions(int examId,int page,int limit,String search);
}
