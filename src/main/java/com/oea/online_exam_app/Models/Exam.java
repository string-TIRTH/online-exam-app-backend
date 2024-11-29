/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "exam_master")
@Data
@NoArgsConstructor
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int examId;

    private String examCode;

    private LocalDate examDate;

    private LocalDateTime examStartTime;

    private LocalDateTime examEndTime;

    private int examDurationInMinutes;

    private int passingCriteria;

    private int passingValue;

    private int totalMarks;
    public Exam(String examCode, LocalDate examDate, LocalDateTime examStartTime, LocalDateTime examEndTime, int examDurationInMinutes, int passingCriteria, int passingValue,int totalMarks) {
        this.examCode = examCode;
        this.examDate = examDate;
        this.examStartTime = examStartTime;
        this.examEndTime = examEndTime;
        this.examDurationInMinutes = examDurationInMinutes;
        this.passingCriteria = passingCriteria;
        this.passingValue = passingValue;
        this.totalMarks = totalMarks;
    }
}
