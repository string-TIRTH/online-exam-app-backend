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
@Table(name="logs")
@Data
public class Logs {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int logId;
    private String action;
    private String email;
    private Boolean isSuccess;

    public Logs(){}

    public Logs(String action, String email, Boolean isSuccess){
        this.action = action;
        this.email = email;
        this.isSuccess = isSuccess;
    }
}
