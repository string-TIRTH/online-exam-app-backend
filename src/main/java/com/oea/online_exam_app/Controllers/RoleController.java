/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oea.online_exam_app.Models.Role;
import com.oea.online_exam_app.Repo.RoleRepo;
import com.oea.online_exam_app.Requests.Base.GetListWithPagingSearchRequest;
import com.oea.online_exam_app.Requests.Role.CreateRoleRequest;
import com.oea.online_exam_app.Requests.Role.DeleteRoleRequest;
import com.oea.online_exam_app.Requests.Role.UpdateRoleRequest;
import com.oea.online_exam_app.Responses.Base.GetListWithPagingSearchResponse;
import com.oea.online_exam_app.Responses.BaseResponse;
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
    
    @Autowired
    RoleRepo roleRepo;

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

    @PostMapping("/update")
        public ResponseEntity<BaseResponse> updateQuestion(@RequestBody UpdateRoleRequest request) {
            try {
                Role role = request.getRole();
                if(roleService.updateRole(role, role.getRoleId())>0){
                    return ResponseEntity.status(200).body(new BaseResponse(
                        "success","question updated successfully"
                    ));
                }
                return ResponseEntity.status(400).body(new BaseResponse(
                    "failed","unable to update question"
                ));
            } catch (Exception e) {
                System.out.println(e);
                return ResponseEntity.status(500).body(new BaseResponse(
                "failed","Internal server error"
                ));
            }

        }
    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> deleteRole(@RequestBody DeleteRoleRequest request) {
        try {
            if(roleService.deleteRole(request.getRoleId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","role deleted successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to delete role"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }
    @PostMapping("/getRoleList")
    public ResponseEntity<GetListWithPagingSearchResponse> getRoleList(@RequestBody GetListWithPagingSearchRequest request) {
        try {
            List<Role> roles = roleService.getRoles(request.getPage(),request.getLimit(),request.getSearch());
            long roleCount = roleRepo.getRoleCountWithSearch(request.getSearch());
            if(roles!= null && !roles.isEmpty()){
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "success","ok",roles,roleCount
                ));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed","roles not found",null,0
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
               "failed","Internal server error",null,0
            ));
        }

    }
}
