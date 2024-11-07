/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oea.online_exam_app.Models.Role;
import com.oea.online_exam_app.Requests.Role.CreateRoleRequest;
import com.oea.online_exam_app.Responses.Role.CreateRoleResponse;
import com.oea.online_exam_app.Services.RoleService;


/**
 *
 * @author tirth
 */
@RestController
@RequestMapping("api/v1/role")
public class RoleController{

    @Autowired
    RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<CreateRoleResponse> createRole(@RequestBody CreateRoleRequest createRoleRequest) {
        try {
            Role role = new Role(createRoleRequest.getRoleName());
            roleService.createRole(role);
            CreateRoleResponse createRoleResponse = new CreateRoleResponse("success","role created successfully");
            return ResponseEntity.ok(createRoleResponse);
        } catch (Exception e) {
            CreateRoleResponse createRoleResponse = new CreateRoleResponse("failed","internal server error");
            return ResponseEntity.ok(createRoleResponse);
        }
    }

    @PostMapping("/update/{roleId}")
    public ResponseEntity<Integer> updateRole(@PathVariable("roleId") int roleId,Role role) {
        try {
            roleService.updateRole(role, roleId);
            return ResponseEntity.ok(role.getRoleId()); 
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); 
        }
    }

    @DeleteMapping("/delete/{roleId}")
    public ResponseEntity<Integer> deleteRole(@PathVariable("roleId") int roleId) {
        try {
            int role = roleService.deleteRole(roleId);
            return ResponseEntity.ok(role); 
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); 
        }
    }
}
