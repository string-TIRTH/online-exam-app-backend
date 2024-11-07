/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Models;


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
@Table(name="question_options")
@Data
public class QuestionOption {
     @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int optionId;


    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "questionId", nullable = false) 
    private Question question;  

    private String option;
    private Boolean isCorrect;

    public QuestionOption(){}

    public QuestionOption(String option,Boolean isCorrect,Question question){
        this.option = option;
        this.isCorrect = isCorrect;
        this.question = question;
    }
}
