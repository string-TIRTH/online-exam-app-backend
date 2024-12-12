/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.oea.online_exam_app.IServices;

import java.util.List;

import com.oea.online_exam_app.Models.Difficulty;

/**
 *
 * @author tirth
 */

public interface IDifficultyService {
    public int createDifficulty(Difficulty difficulty);
    public int createDifficulties(List<Difficulty> difficulties);
    public int updateDifficulty(Difficulty difficulty, int difficultyId);
    public int deleteDifficulty(int difficultyId);
    public List<Difficulty> getAllDifficulties();
}
