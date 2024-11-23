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
@Table(name="questions")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int questionId;
     
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", nullable = false) 
    private Category category;  


    @ManyToOne
    @JoinColumn(name = "question_type_id", referencedColumnName = "questionTypeId", nullable = false) 
    private QuestionType questionType;  


    @ManyToOne
    @JoinColumn(name = "difficulty_id", referencedColumnName = "difficultyId", nullable = false) 
    private Difficulty difficulty;  


}
