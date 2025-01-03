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

    @ManyToOne
    @JoinColumn(name = "exam_session_id", nullable = false)
    private ExamSession examSession;

    private LocalDateTime examStartTime;

    @Column(name = "exam_end_time", nullable = true)
    private LocalDateTime examEndTime;


    private int scoredMarks;

    @ManyToOne
    @JoinColumn(name = "exam_status_id", nullable = false)
    private ExamStatus examStatus;

    public ExamSubmission(Exam exam, User user, ExamSession examSession,LocalDateTime examStartTime, LocalDateTime examEndTime, int scoredMarks, ExamStatus examStatus) {
        this.exam = exam;
        this.user = user;
        this.examSession = examSession;
        this.examStartTime = examStartTime;
        this.examEndTime = examEndTime;
        this.scoredMarks = scoredMarks;
        this.examStatus = examStatus;
    }
}
