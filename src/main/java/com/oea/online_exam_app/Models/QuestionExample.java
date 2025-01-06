/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="question_example")
@Data
public class QuestionExample {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int exampleId;


    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "questionId", nullable = false)
    @JsonIgnore
    private Question question;  

    private String inputText;
    private String outputText;

    public QuestionExample(){}

    public QuestionExample(String inputText,String outputText, Question question){
        this.inputText = inputText;
        this.outputText = outputText;
        this.question = question;
    }
}
