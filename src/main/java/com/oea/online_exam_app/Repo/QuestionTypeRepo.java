/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oea.online_exam_app.Models.QuestionType;


/**
 *
 * @author tirth
 */
@Repository
public interface QuestionTypeRepo extends JpaRepository<QuestionType, Integer> {
    @Override
    Optional<QuestionType> findById(Integer questionTypeId);
    Optional<QuestionType> findByQuestionTypeText(String questionTypeText);
    @Query(value = "SELECT * FROM question_type_master ORDER BY question_type_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<QuestionType> getQuestionTypeList(int limit,int skip);

    @Query(value = "SELECT * FROM question_type_master WHERE question_type_id LIKE CONCAT(:search,'%') OR question_type_text LIKE CONCAT('%',:search,'%') ORDER BY question_type_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<QuestionType> getQuestionTypeListWithSearch(int limit,int skip,String search);

    @Query(value = "SELECT COUNT(*) FROM question_type_master WHERE question_type_id LIKE CONCAT(:search,'%') OR question_type_text LIKE CONCAT('%',:search,'%')", nativeQuery = true)
    long getQuestionTypeCountWithSearch(String search);
}

