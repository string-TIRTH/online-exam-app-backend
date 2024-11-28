/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oea.online_exam_app.Models.QuestionSubmission;


/**
 *
 * @author tirth
 */
@Repository
public interface QuestionSubmissionRepo extends JpaRepository<QuestionSubmission, Integer> {
    Optional<QuestionSubmission> findById(int id);
}
