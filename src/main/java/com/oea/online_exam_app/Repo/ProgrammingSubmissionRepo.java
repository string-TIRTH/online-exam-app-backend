/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oea.online_exam_app.Models.ExamSubmission;
import com.oea.online_exam_app.Models.ProgrammingSubmission;
import com.oea.online_exam_app.Models.User;


/**
 *
 * @author tirth
 */
@Repository
public interface ProgrammingSubmissionRepo extends JpaRepository<ProgrammingSubmission, Integer> {
    Optional<ProgrammingSubmission> findById(int id);
    List<ProgrammingSubmission> findByExamSubmission(ExamSubmission examSubmission);
    List<ProgrammingSubmission> findByExamSubmissionAndUser(ExamSubmission examSubmission,User user);
}
