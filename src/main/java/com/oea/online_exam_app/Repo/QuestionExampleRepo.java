/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oea.online_exam_app.Models.QuestionExample;

/**
 *
 * @author tirth
 */
@Repository
public interface QuestionExampleRepo extends JpaRepository<QuestionExample, Integer> {
    QuestionExample findById(int exampleId);
}

