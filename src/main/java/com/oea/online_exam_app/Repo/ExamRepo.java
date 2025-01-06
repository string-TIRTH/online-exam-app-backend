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
    Optional<Exam> findById(int examId);
    Optional<Exam> findByExamCode(String examCode);
    @Query(value = "SELECT * FROM exam_master WHERE exam_date > :localDate ORDER BY exam_date OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Exam> getExamsWithPagination(int skip,int limit,LocalDate localDate);
    @Query(value = "SELECT * FROM exam_master ORDER BY exam_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Exam> getExamList(int limit,int skip);

    @Query(value = "SELECT * FROM exam_master WHERE exam_id LIKE CONCAT(:search,'%') OR exam_code LIKE CONCAT(:search,'%') ORDER BY exam_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Exam> getExamListWithSearch(int limit,int skip,String search);

    @Query(value = "SELECT COUNT(*) FROM exam_master WHERE exam_id LIKE CONCAT(:search,'%') OR exam_code LIKE CONCAT(:search,'%')", nativeQuery = true)
    long getExamCountWithSearch(String search);
}
