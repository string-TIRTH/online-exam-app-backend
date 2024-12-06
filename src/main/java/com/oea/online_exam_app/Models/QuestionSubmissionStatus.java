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
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author tirth
 */
@Entity
@Table(name = "question_submission_status_master")
@Data
@NoArgsConstructor
public class QuestionSubmissionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_submission_status_id")
    private int questionSubmissionStatusId;

    @Column(name = "question_submission_status_text", nullable = false)
    private String questionSubmissionStatusText;

    public QuestionSubmissionStatus(String questionSubmissionStatusText) {
        this.questionSubmissionStatusText = questionSubmissionStatusText;
    }
}
