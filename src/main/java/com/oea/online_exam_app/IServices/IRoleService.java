/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.Models.Role;

/**
 *
 * @author tirth
 */
public interface IRoleService {
    public int createRole(Role role);
    public int updateRole(Role role,int roleId);
    public int deleteRole(int roleId);
    public Role getRoleById(int roleId);
    public Role getRoleByRole(String role);
    public List<Role> getAllRoles();
    public List<Role> getRoles(int page,int limit,String search);
}
