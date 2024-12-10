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
@Table(name = "programming_submission")
@Data
@NoArgsConstructor
public class ProgrammingSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "programming_submission_id")
    private int programmingSubmissionId;

    @ManyToOne
    @JoinColumn(name = "exam_submission_id", nullable = false)
    private ExamSubmission examSubmission;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "submitted_code", nullable = false, columnDefinition = "TEXT")
    private String submittedCode;

    public ProgrammingSubmission(ExamSubmission examSubmission, User user, Question question, String submittedCode) {
        this.examSubmission = examSubmission;
        this.user = user;
        this.question = question;
        this.submittedCode = submittedCode;
    }

}

