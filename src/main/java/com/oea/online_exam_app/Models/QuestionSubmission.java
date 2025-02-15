/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author tirth
 */
@Entity
@Table(name = "question_submission")
@Data
@NoArgsConstructor
public class QuestionSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_submission_id")
    private int questionSubmissionId;

    private Boolean isCorrect = false;

    @ManyToOne
    @JoinColumn(name = "exam_submission_id", nullable = false)
    private ExamSubmission examSubmission;
   

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "selected_option_id", nullable = true)
    private QuestionOption selectedOption;

    @ManyToOne
    @JoinColumn(name = "question_submission_status_id", nullable = true)
    private QuestionSubmissionStatus questionSubmissionStatus;

    public QuestionSubmission(Question question, User user, ExamSubmission examSubmission, QuestionOption selectedOption,QuestionSubmissionStatus questionSubmissionStatus) {
        this.question = question;
        this.user = user;
        this.examSubmission = examSubmission;
        this.selectedOption = selectedOption;
        this.questionSubmissionStatus = questionSubmissionStatus;
    }

}
