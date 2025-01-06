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

import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Models.ExamSubmission;
import com.oea.online_exam_app.Models.User;


/**
 *
 * @author tirth
 */
@Repository
public interface ExamSubmissionRepo extends JpaRepository<ExamSubmission, Integer> {
    Optional<ExamSubmission> findById(int id);
    List<ExamSubmission> findByExam(Exam exam);
    ExamSubmission findByUserAndExam(User user, Exam exam);

    @Query(value = "SELECT * FROM exam_submission WHERE exam_id= :examId ORDER BY exam_submission_id DESC, scored_marks OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<ExamSubmission> getExamSubmissionList(int examId, int limit, int skip);

    @Query(value = "SELECT * FROM exam_submission WHERE exam_id= :examId AND exam_submission_id LIKE CONCAT(:search, '%') ORDER BY exam_submission_id DESC, scored_marks OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<ExamSubmission> getExamSubmissionListWithSearch(int examId, int limit, int skip, String search);

    @Query(value = "SELECT COUNT(*) FROM exam_submission WHERE exam_id= :examId AND exam_submission_id LIKE CONCAT(:search, '%')", nativeQuery = true)
    long getExamSubmissionCountWithSearch(int examId, String search);


}
