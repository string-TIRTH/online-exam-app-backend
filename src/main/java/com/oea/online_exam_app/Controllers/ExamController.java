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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.oea.online_exam_app.DTO.QuestionsDTO;
import com.oea.online_exam_app.Models.Category;
import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Models.Question;
import com.oea.online_exam_app.Repo.CategoryRepo;
import com.oea.online_exam_app.Repo.ExamSubmissionRepo;
import com.oea.online_exam_app.Repo.PassingCriteriaRepo;
import com.oea.online_exam_app.Repo.QuestionRepo;
import com.oea.online_exam_app.Repo.UserRepo;
import com.oea.online_exam_app.Requests.Exam.CreateExamRequest;
import com.oea.online_exam_app.Requests.Exam.DeleteExamRequest;
import com.oea.online_exam_app.Requests.Exam.GetExamQuestionsRequest;
import com.oea.online_exam_app.Requests.Exam.SubmitExamRequest;
import com.oea.online_exam_app.Requests.Exam.SubmitMCQQuestionRequest;
import com.oea.online_exam_app.Requests.Exam.SubmitProgrammingQuestionRequest;
import com.oea.online_exam_app.Requests.Exam.UpdateExamRequest;
import com.oea.online_exam_app.Responses.BaseResponse;
import com.oea.online_exam_app.Responses.Exam.GetExamQuestionsResponse;
import com.oea.online_exam_app.Responses.Exam.GetExamResponse;
import com.oea.online_exam_app.Responses.Exam.GetExamsResponse;
import com.oea.online_exam_app.Services.ExamQuestionsService;
import com.oea.online_exam_app.Services.ExamService;
import com.oea.online_exam_app.Views.View;

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
    UserRepo userRepo;
    @Autowired
    QuestionRepo questionRepo;

    
    @Autowired
    PassingCriteriaRepo passingCriteriaRepo;

    @Autowired
    ExamSubmissionRepo examSubmissionRepo;

    @PostMapping("/create")
    @Transactional
    @JsonView(View.Admin.class)
    public ResponseEntity<BaseResponse> createExam(@RequestBody CreateExamRequest request) {
        try {
            Exam exam = new Exam();
            exam.setExamCode(request.getExamCode());
            exam.setExamDate(request.getExamDate());
            exam.setExamStartTime(request.getExamStartTime());
            exam.setExamEndTime(request.getExamEndTime());
            exam.setExamDurationInMinutes(request.getExamDurationInMinutes());
            exam.setPassingCriteria(passingCriteriaRepo.getReferenceById(request.getPassingCriteria()));
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
    @JsonView(View.Admin.class)
    public ResponseEntity<BaseResponse> updateExam(@RequestBody UpdateExamRequest request) {
        try {
            Exam exam = request.getExam();
            int examId = exam.getExamId();
            List<UpdateExamRequest.QuestionsUpdateRequest> questions = request.getQuestions();
            System.out.println(questions);
            if(examService.updateExam(exam, exam.getExamId())>0){
                if(!questions.isEmpty()){
                    questions.forEach(question->{
                        System.out.println(question);
                        if(examQuestionsService.updateExamQuestionByIds(examId, question.getOldQuestionsId(), question.getNewQuestionId()) == 0){
                            throw new Error("Error while updating exam question : "+ question.toString()); 
                        }
                    });
                }
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
    @JsonView(View.Admin.class)
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

    @GetMapping("/getAll")
    @Transactional
    @JsonView(View.Admin.class)
    public ResponseEntity<GetExamsResponse> getAllExam(@RequestParam int page, @RequestParam int limit) {
        try {
           
            List<Exam> exams = examService.getAllExams(page,limit);
            if(exams == null || exams.isEmpty()){
                GetExamsResponse getAllExamsResponse = new GetExamsResponse("success","Exams Not Found",null);
                return ResponseEntity.status(204).body(getAllExamsResponse);
            }
            
            GetExamsResponse getAllExamsResponse = new GetExamsResponse("success","OK",exams);
            return ResponseEntity.status(200).body(getAllExamsResponse);
        } catch (Exception e) {
            GetExamsResponse getAllExamsResponse = new GetExamsResponse("failed",
                    "Error" + e.toString(),null);
            return ResponseEntity.status(500).body(getAllExamsResponse);
        }
    }
    public List<Question> selectRandomQuestions(int noOfQuestions,Category category){
        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category.getCategoryId(), noOfQuestions);
        if(questions.size()==noOfQuestions){
            return questions;
        }
        throw new Error("Unable to fetch questions... not enough questions"); 
    }
    @GetMapping("/{examId}")
    @Transactional
    @JsonView(View.Public.class)
    public ResponseEntity<GetExamResponse> getExamById(@PathVariable int examId) {
        try {
           
            Exam exam = examService.getExamById(examId);
            if(exam == null ){
                GetExamResponse getAllExamsResponse = new GetExamResponse("success","Exam Not Found",null);
                return ResponseEntity.status(404).body(getAllExamsResponse);
            }
            
            GetExamResponse getAllExamsResponse = new GetExamResponse("success","OK",exam);
            return ResponseEntity.status(200).body(getAllExamsResponse);
        } catch (Exception e) {
            GetExamResponse getAllExamsResponse = new GetExamResponse("failed",
                    "Error" + e.toString(),null);
            return ResponseEntity.status(500).body(getAllExamsResponse);
        }
    }
    @PostMapping("/getExamsQuestions")
    @Transactional
    public ResponseEntity<GetExamQuestionsResponse> getExamsQuestions(@RequestBody GetExamQuestionsRequest request) {
        try {
            userRepo.findById(request.getUserId()).orElseThrow(()-> new IllegalArgumentException("Invalid userId"));      
            QuestionsDTO questions = examService.getExamQuestions(request.getExamId(), request.getUserId());
            System.out.println(questions);
            if(!questions.getQuestionsMCQ().isEmpty() || !questions.getQuestionsPro().isEmpty() ){
                GetExamQuestionsResponse getExamQuestionsResponse = new GetExamQuestionsResponse("success","OK",questions);
                return ResponseEntity.status(200).body(getExamQuestionsResponse);
            }
            GetExamQuestionsResponse getExamQuestionsResponse = new GetExamQuestionsResponse("error","Unable to fetch questions",null);
                return ResponseEntity.status(400).body(getExamQuestionsResponse);
        } catch (Exception e) {
            GetExamQuestionsResponse getExamQuestionsResponse = new GetExamQuestionsResponse("failed",
                    "Error " + e.toString(),null);
            return ResponseEntity.status(500).body(getExamQuestionsResponse);
        }
    }
    @PostMapping("/submitQuestionOption")
    @Transactional
    public ResponseEntity<BaseResponse> submitQuestionOption(@RequestBody SubmitMCQQuestionRequest request) {
        try {
            userRepo.findById(request.getUserId()).orElseThrow(()-> new IllegalArgumentException("Invalid userId"));  
            
            // check time 
            if(examService.updateSelectedOption(request.getUserId(),request.getExamSubmissionId(), request.getQuestionId(), request.getOptionId(),request.getStatusId())!=1){
                throw new Error("Error while submitting question"); 
            }    
            BaseResponse getExamQuestionsResponse = new BaseResponse("success","OK");
            return ResponseEntity.status(200).body(getExamQuestionsResponse);
        } catch (Exception e) {
            BaseResponse getExamQuestionsResponse = new BaseResponse("failed","Error " + e.toString());
            return ResponseEntity.status(200).body(getExamQuestionsResponse);
        }
    }
    @PostMapping("/submitProgrammingQuestion")
    @Transactional
    public ResponseEntity<BaseResponse> submitProgrammingQuestion(@RequestBody SubmitProgrammingQuestionRequest request) {
        try {
            userRepo.findById(request.getUserId()).orElseThrow(()-> new IllegalArgumentException("Invalid userId"));      
            // check time 
            if(examService.submitCode(request.getUserId(),request.getExamSubmissionId(), request.getQuestionId(), request.getSubmittedCode())!=1){
                throw new Error("Error while submitting programming question"); 
            }    
            BaseResponse submitProgrammingQuestionResponse = new BaseResponse("success","OK");
            return ResponseEntity.status(200).body(submitProgrammingQuestionResponse);
        } catch (Exception e) {
            BaseResponse getExamQuestionsResponse = new BaseResponse("failed","Error " + e.toString());
            return ResponseEntity.status(200).body(getExamQuestionsResponse);
        }
    }
    @PostMapping("/submitExam")
    @Transactional
    public ResponseEntity<BaseResponse> submitExam(@RequestBody SubmitExamRequest request) {
        try {
            userRepo.findById(request.getUserId()).orElseThrow(()-> new IllegalArgumentException("Invalid userId"));      
            // check time 
            if(examService.submitExam(request.getUserId(),request.getExamSubmissionId())!=1){
                throw new Error("Error while submitting question"); 
            }    
            BaseResponse generateResultMCQResponse = new BaseResponse("success","OK");
            return ResponseEntity.status(200).body(generateResultMCQResponse);
        } catch (Exception e) {
            BaseResponse getExamQuestionsResponse = new BaseResponse("failed","Error " + e.toString());
            return ResponseEntity.status(200).body(getExamQuestionsResponse);
        }
    }
}
