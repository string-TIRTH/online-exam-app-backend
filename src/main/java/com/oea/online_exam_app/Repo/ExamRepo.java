/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.Repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oea.online_exam_app.Models.Exam;


/**
 *
 * @author tirth
 */
@Repository
public interface ExamRepo extends JpaRepository<Exam, Integer> {
    Optional<Exam> findById(int id);

    @Query(value = "SELECT * FROM exam_master WHERE exam_date > :localDate ORDER BY exam_date OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Exam> getExamsWithPagination(int skip,int limit,LocalDate localDate);

}
