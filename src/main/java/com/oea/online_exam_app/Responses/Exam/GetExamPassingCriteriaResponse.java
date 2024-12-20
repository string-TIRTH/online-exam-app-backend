/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Responses.Exam;

import java.util.List;

import com.oea.online_exam_app.Models.PassingCriteria;

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
public class GetExamPassingCriteriaResponse {
    private String status;
    private String message;
    private List<PassingCriteria> passingCriterias;
    
}
