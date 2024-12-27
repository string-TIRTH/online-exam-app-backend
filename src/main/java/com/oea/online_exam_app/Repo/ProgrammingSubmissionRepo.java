/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "SELECT COUNT(*) FROM programming_submission WHERE exam_submission_id= :examSubmissionId AND is_correct = 1", nativeQuery = true)
    long getCorrectSubmissionCount(int examSubmissionId);

    @Query(value = "SELECT * FROM programming_submission WHERE exam_submission_id= :examSubmissionId ORDER BY programming_submission_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<ProgrammingSubmission> getProgrammingSubmissionList(int examSubmissionId, int limit, int skip);

    @Query(value = "SELECT * FROM programming_submission WHERE exam_submission_id= :examSubmissionId AND programming_submission_id LIKE CONCAT(:search, '%') ORDER BY programming_submission_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<ProgrammingSubmission> getProgrammingSubmissionListWithSearch(int examSubmissionId, int limit, int skip, String search);

    @Query(value = "SELECT COUNT(*) FROM programming_submission WHERE exam_submission_id= :examSubmissionId AND programming_submission_id LIKE CONCAT(:search, '%')", nativeQuery = true)
    long getProgrammingSubmissionCountWithSearch(int examSubmissionId, String search);
}
