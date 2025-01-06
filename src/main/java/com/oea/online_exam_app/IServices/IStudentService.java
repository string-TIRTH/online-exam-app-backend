 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.Models.User;

/**
 *
 * @author tirth
 */
public interface IStudentService {
    public int registerStudent(User student);
    public int registerStudents(List<User> students);
    public int updateStudent(User student,int userId);
    public int deleteStudent(int userId);
    public List<User> getStudents(int page,int limit,String search,int roleId);
}
