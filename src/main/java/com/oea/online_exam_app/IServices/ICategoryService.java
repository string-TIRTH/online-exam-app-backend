/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.Models.Category;

/**
 *
 * @author tirth
 */

public interface ICategoryService {
    public int createCategory(Category category);
    public int createCategories(List<Category> categories);
    public int updateCategory(Category category, Integer categoryId);
    public int deleteCategory( Integer categoryId );
    public List<Category> getAllCategories();
    public List<Category> getCategories(int page,int limit,String search);
}
