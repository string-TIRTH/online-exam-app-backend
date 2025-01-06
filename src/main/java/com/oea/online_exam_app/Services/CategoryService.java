/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.ICategoryService;
import com.oea.online_exam_app.Models.Category;
import com.oea.online_exam_app.Repo.CategoryRepo;

/**
 *
 * @author tirth
 */
@Service
public class CategoryService implements ICategoryService{

    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public int createCategory(Category category) {
        try {
            categoryRepo.save(category);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int createCategories(List<Category> categories) {
        try {
            categoryRepo.saveAll(categories);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int updateCategory(Category category,Integer categoryId) {
       try {
            Category existingCategory = categoryRepo.findById(categoryId).orElseThrow(() ->new IllegalArgumentException("Invalid categoryId"));
            if (existingCategory != null) {
                existingCategory.setCategoryText(category.getCategoryText());  
                categoryRepo.save(existingCategory);
                return existingCategory.getCategoryId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteCategory(Integer categoryId) {
        try {
            if (categoryRepo.existsById(categoryId)) {
                categoryRepo.deleteById(categoryId);
                return categoryId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            List<Category> categories = categoryRepo.findAll(Sort.by(Sort.Direction.ASC,"categoryId"));
            return categories;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }

    @Override
    public List<Category> getCategories(int page,int limit,String search) {
        try {
            int offset = (page - 1) * limit;
            List<Category> categories;
            if(search.trim().isBlank()){
                categories = categoryRepo.getCategoryList(limit,offset);

            }else{
                categories = categoryRepo.getCategoryListWithSearch(limit,offset,search);
            }
            return categories;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
}
