/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Models;

import jakarta.persistence.Column;
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
@Table(name="difficulty_master")
@Data
public class Difficulty {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(columnDefinition= "TINYINT")
    private Integer difficultyId;
    private String difficultyText;
    private int difficultyWeight;

    public Difficulty(){}

    public Difficulty(String difficultyText,int difficultyWeight){
        this.difficultyText = difficultyText;
        this.difficultyWeight = difficultyWeight;
    }
}
