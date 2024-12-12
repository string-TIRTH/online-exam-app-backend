/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.IServices.IDifficultyService;
import com.oea.online_exam_app.Models.Difficulty;
import com.oea.online_exam_app.Repo.DifficultyRepo;

/**
 *
 * @author tirth
 */
@Service
public class DifficultyService implements IDifficultyService{

     @Autowired
    private DifficultyRepo difficultyRepo;
    @Override
    public int createDifficulty(Difficulty difficulty) {
        try {
            difficultyRepo.save(difficulty);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int createDifficulties(List<Difficulty> difficulties) {
        try {
            difficultyRepo.saveAll(difficulties);
            return 1;    
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int updateDifficulty(Difficulty difficulty,int diffcultyId) {
       try {
            Difficulty existingDifficulty = difficultyRepo.findById(diffcultyId).orElseThrow(() ->new IllegalArgumentException("Invalid difficultyId"));
            if (existingDifficulty != null) {
                existingDifficulty.setDifficultyText(difficulty.getDifficultyText());  
                difficultyRepo.save(existingDifficulty);
                return existingDifficulty.getDifficultyId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteDifficulty(int diffcultyId) {
        try {
            if (difficultyRepo.existsById(diffcultyId)) {
                difficultyRepo.deleteById(diffcultyId);
                return diffcultyId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

   @Override
    public List<Difficulty> getAllDifficulties() {
        try {
            List<Difficulty> difficulties = difficultyRepo.findAll(Sort.by(Sort.Direction.ASC,"difficultyId"));
            return difficulties;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
}
