/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author tirth
 */
@Entity
@Table(name="question_type_master")
@Data
public class QuestionType {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int questionTypeId;
    private String questionTypeText;

    public QuestionType(){}

    public QuestionType(String questionTypeText){
        this.questionTypeText = questionTypeText;
    }
}
