/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import com.oea.online_exam_app.Models.ProgrammingSubmission;

/**
 *
 * @author tirth
 */
public interface IProgrammingSubmissionService {
    public int createProgrammingSubmission(ProgrammingSubmission programmingSubmission);
    public int updateProgrammingSubmission(ProgrammingSubmission programmingSubmission, int programmingSubmissionId);
    public int deleteProgrammingSubmission(int programmingSubmissionId);
}
