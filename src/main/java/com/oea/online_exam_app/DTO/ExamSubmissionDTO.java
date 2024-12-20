/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.DTO;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author tirth
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSubmissionDTO {
    private int examSubmissionId;
    private String studentName;
    private String studentEmail;
    private int scoredMarks;
    private int completedInMinutes;
}