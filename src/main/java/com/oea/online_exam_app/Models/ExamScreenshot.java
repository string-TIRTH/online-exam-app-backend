/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author tirth
 */
@Entity
@Table(name="exam_screenshot")
@Data
public class ExamScreenshot {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int screenshotId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "exam_submission_id", nullable = false)
    private ExamSubmission examSubmission;

    private String imageUrl;

    private LocalDateTime captureTimeStamp;
    public ExamScreenshot(){}

    public ExamScreenshot(User user, ExamSubmission examSubmission, String imageUrl, LocalDateTime captureTimeStamp) {
        this.user = user;
        this.examSubmission = examSubmission;
        this.imageUrl = imageUrl;
        this.captureTimeStamp = captureTimeStamp;
    }
}
