/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oea.online_exam_app.Models.Role;

/**
 *
 * @author tirth
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>{
    Role findById(int roleId);
    Role findByRole(String role);
     @Query(value = "SELECT * FROM role_master ORDER BY role_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Role> getRoleList(int limit,int skip);

    @Query(value = "SELECT * FROM role_master WHERE role_id LIKE CONCAT(:search,'%') OR role LIKE CONCAT('%',:search,'%') ORDER BY role_id OFFSET :skip ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
    List<Role> getRoleListWithSearch(int limit,int skip,String search);

    @Query(value = "SELECT COUNT(*) FROM role_master WHERE role_id LIKE CONCAT(:search,'%') OR role LIKE CONCAT('%',:search,'%')", nativeQuery = true)
    long getRoleCountWithSearch(String search);
}
