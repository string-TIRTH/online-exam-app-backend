/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.DTO.MCQSubmissionDTO;
import com.oea.online_exam_app.Models.QuestionSubmission;

/**
 *
 * @author tirth
 */
public interface IQuestionSubmissionService {
    public int createQuestionSubmission(QuestionSubmission questionSubmission);
    public int updateQuestionSubmission(QuestionSubmission questionSubmission, int questionSubmissionId);
    public int deleteQuestionSubmission(int questionSubmissionId);
    public List<MCQSubmissionDTO> getQuestionSubmissions(int examId,int page,int limit,String search);
}
