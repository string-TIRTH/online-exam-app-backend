/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oea.online_exam_app.Models.Category;
import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Models.Question;
import com.oea.online_exam_app.Repo.CategoryRepo;
import com.oea.online_exam_app.Repo.QuestionRepo;
import com.oea.online_exam_app.Requests.Exam.CreateExamRequest;
import com.oea.online_exam_app.Requests.Exam.DeleteExamRequest;
import com.oea.online_exam_app.Requests.Exam.UpdateExamRequest;
import com.oea.online_exam_app.Responses.BaseResponse;
import com.oea.online_exam_app.Services.ExamQuestionsService;
import com.oea.online_exam_app.Services.ExamService;

/**
 *
 * @author tirth
 */
@RestController
@RequestMapping("api/v1/exam")

public class ExamController {

    @Autowired
    ExamService examService;

    @Autowired
    ExamQuestionsService examQuestionsService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    QuestionRepo questionRepo;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<BaseResponse> createExam(@RequestBody CreateExamRequest request) {
        try {
            Exam exam = new Exam();
            exam.setExamCode(request.getExamCode());
            exam.setExamDate(request.getExamDate());
            exam.setExamStartTime(request.getExamStartTime());
            exam.setExamEndTime(request.getExamEndTime());
            exam.setExamDurationInMinutes(request.getExamDurationInMinutes());
            exam.setPassingCriteria(request.getPassingCriteria());
            exam.setPassingValue(request.getPassingValue());
            exam.setTotalMarks(request.getTotalMarks());

            examService.createExam(exam);
            if (request.getCategoryWiseQuestions() != null) {
                for (CreateExamRequest.CategoryQuestion cq : request.getCategoryWiseQuestions()) {
                    Category category = categoryRepo.findById(cq.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Invalid categoryId"));
                    int noOfQuestions = cq.getNoOfQuestions();
                    List<Question> questions = selectRandomQuestions(noOfQuestions, category);
                    if(examQuestionsService.createExamQuestions(exam, questions)== 0){
                        throw new Error("Error while creating exam"); 
                    }
                }
            }
            BaseResponse createQuestionResponse = new BaseResponse("success","Exam Created Successfully");
                return ResponseEntity.status(201).body(createQuestionResponse);
        } catch (Exception e) {
            BaseResponse createQuestionResponse = new BaseResponse("failed",
                    "Error" + e.toString());
            return ResponseEntity.status(500).body(createQuestionResponse);
        }
    }


    @PutMapping("/update")
    @Transactional
    public ResponseEntity<BaseResponse> updateExam(@RequestBody UpdateExamRequest request) {
        try {
            Exam exam = request.getExam();
            if(examService.updateExam(exam, exam.getExamId())>0){
                BaseResponse createQuestionResponse = new BaseResponse("success","Exam Updated Successfully");
                return ResponseEntity.status(200).body(createQuestionResponse);
            }
            throw new Error("Error while updating exam"); 
        } catch (Exception e) {
            BaseResponse createQuestionResponse = new BaseResponse("failed",
                    "Error" + e.toString());
            return ResponseEntity.status(500).body(createQuestionResponse);
        }
    }

    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<BaseResponse> deleteExam(@RequestBody DeleteExamRequest request) {
        try {
            int examId = request.getExamId();
            int response = examService.deleteExam(examId);
            if(response>0){
                BaseResponse createQuestionResponse = new BaseResponse("success","Exam Updated Successfully");
                return ResponseEntity.status(200).body(createQuestionResponse);
            }else if(response == -1){
                BaseResponse createQuestionResponse = new BaseResponse("failed","Exam Not Found");
                return ResponseEntity.status(400).body(createQuestionResponse);
            }
            throw new Error("Error while updating exam"); 
        } catch (Exception e) {
            BaseResponse createQuestionResponse = new BaseResponse("failed",
                    "Error" + e.toString());
            return ResponseEntity.status(500).body(createQuestionResponse);
        }
    }
    public List<Question> selectRandomQuestions(int noOfQuestions,Category category){
        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category.getCategoryId(), noOfQuestions);
        if(questions.size()==noOfQuestions){
            return questions;
        }
        throw new Error("Unable to fetch questions... not enough questions"); 
    }
}
