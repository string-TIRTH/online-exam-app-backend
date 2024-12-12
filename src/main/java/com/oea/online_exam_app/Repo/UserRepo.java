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

import com.oea.online_exam_app.Models.User;

/**
 *
 * @author tirth
 */
@Repository
public interface UserRepo extends JpaRepository<User,Integer>{
    User findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    Optional<User> findById(int userId);

    @Query(value = "SELECT * FROM users WHERE role_id = :student ORDER BY user_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<User> getStudentList(int limit,int skip,int student);

    @Query(value = "SELECT * FROM users WHERE role_id = :student AND (user_id LIKE CONCAT(:search,'%') OR full_name LIKE CONCAT('%',:search,'%')) ORDER BY user_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<User> getStudentListWithSearch(int limit,int skip,String search,int student);

    @Query(value = "SELECT COUNT(*) FROM users WHERE role_id = :student AND (user_id LIKE CONCAT(:search,'%') OR full_name LIKE CONCAT('%',:search,'%'))", nativeQuery = true)
    long getQuestionCountWithSearch(String search,int student);
}
