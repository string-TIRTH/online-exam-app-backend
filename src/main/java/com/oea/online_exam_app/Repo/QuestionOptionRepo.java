/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.oea.online_exam_app.Models.Question;
import com.oea.online_exam_app.Models.QuestionOption;

/**
 *
 * @author tirth
 */
@Repository
public interface QuestionOptionRepo extends JpaRepository<QuestionOption, Integer> {
    QuestionOption findById(int questionOptionId);
    @Modifying
    void deleteByQuestion(Question question);
}
