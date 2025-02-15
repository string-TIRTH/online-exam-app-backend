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
@Table(name="role_master")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(columnDefinition= "TINYINT")
    private Integer roleId;

    private String role;

    public Role() {}

    public Role(String role) {
        this.role = role;
    }
}
