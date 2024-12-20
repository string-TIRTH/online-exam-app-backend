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
public interface IExaminerService {
    public int registerExaminer(User examiner);
    public int registerExaminers(List<User> examiners);
    public int updateExaminer(User examiner,int userId);
    public int deleteExaminer(int userId);
    public List<User> getExaminers(int page,int limit,String search,int roleId);
}
