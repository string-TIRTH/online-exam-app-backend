/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Models;

import java.time.LocalDateTime;

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
@Table(name = "exam_submission")
@Data
@NoArgsConstructor
public class ExamSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_submission_id")
    private int examSubmissionId;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime examStartTime;

    private LocalDateTime examEndTime;

    private Integer totalMarks;

    private Integer scoredMarks;

    @ManyToOne
    @JoinColumn(name = "exam_status_id", nullable = false)
    private ExamStatus examStatus;

    public ExamSubmission(Exam exam, User user, LocalDateTime examStartTime, LocalDateTime examEndTime, Integer totalMarks, Integer scoredMarks, ExamStatus examStatus) {
        this.exam = exam;
        this.user = user;
        this.examStartTime = examStartTime;
        this.examEndTime = examEndTime;
        this.totalMarks = totalMarks;
        this.scoredMarks = scoredMarks;
        this.examStatus = examStatus;
    }
}
