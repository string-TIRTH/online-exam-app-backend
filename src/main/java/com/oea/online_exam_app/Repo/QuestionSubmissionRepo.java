/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oea.online_exam_app.Models.QuestionSubmission;

import jakarta.transaction.Transactional;


/**
 *
 * @author tirth
 */
@Repository
public interface QuestionSubmissionRepo extends JpaRepository<QuestionSubmission, Integer> {

    Optional<QuestionSubmission> findById(int id);

    @Query(value = "SELECT qs.* from question_submission qs JOIN question_options qo on qs.selected_option_id = qo.option_id WHERE qo.is_correct = 1  AND qs.exam_submission_id= 1", nativeQuery = true)
    public List<QuestionSubmission> getCorrectSubmissions(int examSubmissionId); 
    

    @Modifying
    @Transactional
    @Query(value = "UPDATE question_submission SET selected_option_id = :optionId, question_submission_status_id = :statusId WHERE user_id = :userId AND exam_submission_id = :examSubmissionId AND question_id = :questionId", nativeQuery = true)
    public int updateOptionAndStatus(int userId,int examSubmissionId,int questionId,Integer optionId,int statusId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE programming_submission SET submitted_code = :code WHERE user_id = :userId AND exam_submission_id = :examSubmissionId AND question_id = :questionId", nativeQuery = true)
    public int updateProgrammingCode(int userId,int examSubmissionId,int questionId,String code);
}
   