/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import com.oea.online_exam_app.Models.ExamStatus;

/**
 *
 * @author tirth
 */
public interface IExamStatusService {
    public int createExamStatus(ExamStatus examStatus);
    public int updateExamStatus(ExamStatus examStatus, int examStatusId);
    public int deleteExamStatus(int examStatusId);
}
