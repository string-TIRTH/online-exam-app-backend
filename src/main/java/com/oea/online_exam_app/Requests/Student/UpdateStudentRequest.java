/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Requests.Student;

import com.oea.online_exam_app.Models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author tirth
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentRequest {
    private User student;
}
