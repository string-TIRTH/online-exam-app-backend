/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Repo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oea.online_exam_app.Models.Category;
import com.oea.online_exam_app.Models.Question;

/**
 *
 * @author tirth
 */
@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer>{
    Optional<Question>  findById(int questionId);
    int countByCategory(Category category); 

    @Query(value = "SELECT TOP (:limit) * FROM questions WHERE category_id = :categoryId AND question_type_id =:questionTypeId ORDER BY NEWID()", nativeQuery = true)
    List<Question> findRandomMCQQuestionsByCategory(Integer categoryId,int limit, Integer questionTypeId);

    @Query(value = "SELECT TOP (:limit) * FROM questions WHERE difficulty_id = :difficultyId AND question_type_id =:questionTypeId ORDER BY NEWID()", nativeQuery = true)
    List<Question> findRandomProQuestionsByDifficulty(Integer difficultyId,int limit, Integer questionTypeId);

    @Query(value = "SELECT * FROM questions ORDER BY question_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Question> getQuestionList(int limit,int skip);

    @Query(value = "SELECT * FROM questions WHERE question_id LIKE CONCAT(:search,'%') OR question_text LIKE CONCAT('%',:search,'%') ORDER BY question_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Question> getQuestionListWithSearch(int limit,int skip,String search);

    @Query(value = "SELECT COUNT(*) FROM questions WHERE question_id LIKE CONCAT(:search,'%') OR question_text LIKE CONCAT('%',:search,'%')", nativeQuery = true)
    long getQuestionCountWithSearch(String search);
}
