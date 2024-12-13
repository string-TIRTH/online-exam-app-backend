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

import com.oea.online_exam_app.Models.Category;


/**
 *
 * @author tirth
 */
@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Optional<Category> findById(int id);
    Optional<Category> findByCategoryText(String categoryText);
    @Query(value = "SELECT * FROM category_master ORDER BY category_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Category> getCategoryList(int limit,int skip);

    @Query(value = "SELECT * FROM category_master WHERE category_id LIKE CONCAT(:search,'%') OR category_text LIKE CONCAT('%',:search,'%') ORDER BY category_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Category> getCategoryListWithSearch(int limit,int skip,String search);

    @Query(value = "SELECT COUNT(*) FROM category_master WHERE category_id LIKE CONCAT(:search,'%') OR category_text LIKE CONCAT('%',:search,'%')", nativeQuery = true)
    long getCategoryCountWithSearch(String search);
}
