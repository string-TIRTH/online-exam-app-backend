/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
    private Integer optionId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "question_id", referencedColumnName = "questionId", nullable = false)
    private Question question; 

    private String optionText;
    
    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "is_image")
    private Boolean isImage;

    public QuestionOption(){}

    public QuestionOption(String optionText,Boolean isCorrect,Question question,Boolean isImage) {
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        this.question = question;
        this.isImage = isImage;
    }
}
