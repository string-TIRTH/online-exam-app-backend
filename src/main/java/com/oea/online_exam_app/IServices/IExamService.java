/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import com.oea.online_exam_app.Models.Exam;

/**
 *
 * @author tirth
 */
public interface IExamService {
    public int createExam(Exam exam);
    public int updateExam(Exam exam, int examId);
    public int deleteExam(int examId);
}
