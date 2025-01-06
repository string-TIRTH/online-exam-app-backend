/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.DTO.ProSubmissionDTO;
import com.oea.online_exam_app.Models.ProgrammingSubmission;

/**
 *
 * @author tirth
 */
public interface IProgrammingSubmissionService {
    public int createProgrammingSubmission(ProgrammingSubmission programmingSubmission);
    public int updateProgrammingSubmission(ProgrammingSubmission programmingSubmission, int programmingSubmissionId);
    public int deleteProgrammingSubmission(int programmingSubmissionId);
    public List<ProSubmissionDTO> getQuestionSubmissions(int examId,int page,int limit,String search);
    public int submitCodeReview(int programmingQuestionId,Boolean isCorrect);
}
