/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Models;

import java.time.LocalDateTime;

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
@Table(name="exam_session")
@Data
public class ExamSession {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int examSessionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String ipAddress;

    private String deviceFingerprint;

    private LocalDateTime lastLoggedIn;
    public ExamSession(){}

    public ExamSession(User user, String ipAddress, String deviceFingerprint, LocalDateTime lastLoggedIn) {
        this.user = user;
        this.ipAddress = ipAddress;
        this.deviceFingerprint = deviceFingerprint;
        this.lastLoggedIn = lastLoggedIn;
    }
}
