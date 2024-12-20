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

import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Models.ExamQuestions;
import com.oea.online_exam_app.Models.Question;

import jakarta.transaction.Transactional;


/**
 *
 * @author tirth
 */
@Repository
public interface ExamQuestionsRepo extends JpaRepository<ExamQuestions, Integer> {
    public Optional<ExamQuestions> findById(int examSubmissionId);
    public List<ExamQuestions> findByExam(Exam exam);
    @Query("SELECT q FROM Question q JOIN ExamQuestions eq ON q.questionId = eq.question.questionId WHERE eq.exam.examId = :examId AND q.questionType.questionTypeId = :questionTypeId")
    public List<Question> findByExamIdAndQuestionTypeId(int examId,int questionTypeId);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE exam_questions SET question_id = :newQuestionId WHERE exam_question_id = :oldQuestionId AND exam_id = :examId", nativeQuery = true)
    public void setNewQuestionId(int examId,int oldQuestionId,int newQuestionId);
    @Query("SELECT q FROM Question q LEFT JOIN ExamQuestions eq ON q.questionId = eq.question.questionId WHERE ((eq.exam.examId IS NULL OR eq.exam.examId != :examId) AND (q.questionType.questionTypeId = :questionTypeId AND q.category.categoryId = :categoryId))")
    public List<Question> findReplacementQuestions(int categoryId,int questionTypeId,int examId);
}
