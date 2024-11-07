/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IRoleService;
import com.oea.online_exam_app.Models.Role;
import com.oea.online_exam_app.Repo.RoleRepo;

/**
 *
 * @author tirth
 */
@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public int createRole(Role role) {
        Role savedRole = roleRepo.save(role);
        return savedRole.getRoleId();  
    }

    @Override
    public int updateRole(Role role, int roleId) {
        Role existingRole = roleRepo.findById(roleId);
        if (existingRole != null) {
            existingRole.setRole(role.getRole());  
            roleRepo.save(existingRole);
            return existingRole.getRoleId();
        }
        return -1; 
    }

    @Override
    public int deleteRole(int roleId) {
        if (roleRepo.existsById(roleId)) {
            roleRepo.deleteById(roleId);
            return roleId;
        }
        return -1; 
    }

    @Override
    public Role getRoleById(int roleId) {
        Role role = roleRepo.findById(roleId);
        return role;
    }

    @Override
    public Role getRoleByRole(String roleName) {
        Role role = roleRepo.findByRole(roleName);
        return role;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}
