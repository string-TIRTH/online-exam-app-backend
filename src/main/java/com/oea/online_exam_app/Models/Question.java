/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.oea.online_exam_app.Views.View;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
     
    @JsonView(View.Public.class)
    private String questionText;

    @ManyToOne
    @JsonView(View.Admin.class)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", nullable = false) 
    private Category category;  


    @ManyToOne
    @JsonView(View.Admin.class)
    @JoinColumn(name = "question_type_id", referencedColumnName = "questionTypeId", nullable = false) 
    private QuestionType questionType;  


    @ManyToOne
    @JsonView(View.Admin.class)
    @JoinColumn(name = "difficulty_id", referencedColumnName = "difficultyId", nullable = false) 
    private Difficulty difficulty;  

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE})  
    private List<QuestionOption> options;
    
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE})  
    private List<QuestionExample> examples;
}
