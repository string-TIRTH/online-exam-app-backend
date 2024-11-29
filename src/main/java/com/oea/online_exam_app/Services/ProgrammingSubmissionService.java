/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
