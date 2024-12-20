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

import com.oea.online_exam_app.Models.ExamSubmission;
import com.oea.online_exam_app.Models.QuestionSubmission;
import com.oea.online_exam_app.Models.User;

import jakarta.transaction.Transactional;


/**
 *
 * @author tirth
 */
@Repository
public interface QuestionSubmissionRepo extends JpaRepository<QuestionSubmission, Integer> {

    Optional<QuestionSubmission> findById(int id);
    List<QuestionSubmission> findByExamSubmissionAndUser(ExamSubmission examSubmission,User user);

    @Query(value = "SELECT qs.* from question_submission qs JOIN question_options qo on qs.selected_option_id = qo.option_id WHERE qo.is_correct = 1  AND qs.exam_submission_id= :examSubmissionId", nativeQuery = true)
    public List<QuestionSubmission> getCorrectSubmissions(int examSubmissionId); 
    

    @Modifying
    @Transactional
    @Query(value = "UPDATE question_submission SET selected_option_id = :optionId, question_submission_status_id = :statusId WHERE question_submission_id = :questionSubmissionId", nativeQuery = true)
    public int updateOptionAndStatus(int questionSubmissionId,Integer optionId,int statusId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE programming_submission SET submitted_code = :code WHERE programming_submission_id = :programmingSubmissionId", nativeQuery = true)
    public int updateProgrammingCode(int programmingSubmissionId,String code);
}
   