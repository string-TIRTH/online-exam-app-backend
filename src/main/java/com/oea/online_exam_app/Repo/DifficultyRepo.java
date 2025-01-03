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

import com.oea.online_exam_app.Models.Difficulty;

/**
 *
 * @author tirth
 */
@Repository
public interface DifficultyRepo extends JpaRepository<Difficulty, Integer> {
    Optional<Difficulty> findById(Integer difficutlyId);
    Optional<Difficulty> findByDifficultyText(String difficultyText);
    @Query(value = "SELECT * FROM difficulty_master ORDER BY difficulty_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Difficulty> getDifficultyList(int limit,int skip);

    @Query(value = "SELECT * FROM difficulty_master WHERE difficulty_id LIKE CONCAT(:search,'%') OR difficulty_text LIKE CONCAT('%',:search,'%') ORDER BY difficulty_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Difficulty> getDifficultyListWithSearch(int limit,int skip,String search);

    @Query(value = "SELECT COUNT(*) FROM difficulty_master WHERE difficulty_id LIKE CONCAT(:search,'%') OR difficulty_text LIKE CONCAT('%',:search,'%')", nativeQuery = true)
    long getDifficultyCountWithSearch(String search);
}
