/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Controllers;

import java.rmi.UnexpectedException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.oea.online_exam_app.DTO.ExamResultDetailDTO;
import com.oea.online_exam_app.DTO.ExamSubmissionDTO;
import com.oea.online_exam_app.DTO.MCQSubmissionDTO;
import com.oea.online_exam_app.DTO.ProSubmissionDTO;
import com.oea.online_exam_app.DTO.QuestionsDTO;
import com.oea.online_exam_app.Enums.ExamStatusCodeEnum;
import com.oea.online_exam_app.Enums.QuestionTypeEnum;
import com.oea.online_exam_app.Models.Category;
import com.oea.online_exam_app.Models.Difficulty;
import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Models.ExamQuestions;
import com.oea.online_exam_app.Models.ExamSubmission;
import com.oea.online_exam_app.Models.PassingCriteria;
import com.oea.online_exam_app.Models.Question;
import com.oea.online_exam_app.Models.QuestionType;
import com.oea.online_exam_app.Models.User;
import com.oea.online_exam_app.Repo.CategoryRepo;
import com.oea.online_exam_app.Repo.DifficultyRepo;
import com.oea.online_exam_app.Repo.ExamQuestionsRepo;
import com.oea.online_exam_app.Repo.ExamRepo;
import com.oea.online_exam_app.Repo.ExamSubmissionRepo;
import com.oea.online_exam_app.Repo.PassingCriteriaRepo;
import com.oea.online_exam_app.Repo.ProgrammingSubmissionRepo;
import com.oea.online_exam_app.Repo.QuestionRepo;
import com.oea.online_exam_app.Repo.QuestionSubmissionRepo;
import com.oea.online_exam_app.Repo.QuestionTypeRepo;
import com.oea.online_exam_app.Repo.UserRepo;
import com.oea.online_exam_app.Requests.Base.GetByIdRequest;
import com.oea.online_exam_app.Requests.Base.GetListWithPagingSearchRequest;
import com.oea.online_exam_app.Requests.Exam.CreateExamRequest;
import com.oea.online_exam_app.Requests.Exam.DeleteExamRequest;
import com.oea.online_exam_app.Requests.Exam.GetExamQuestionByCategoryAndQuestionTypeRequest;
import com.oea.online_exam_app.Requests.Exam.GetExamQuestionsRequest;
import com.oea.online_exam_app.Requests.Exam.GetExamStatusRequest;
import com.oea.online_exam_app.Requests.Exam.GetExamSubmissionRequest;
import com.oea.online_exam_app.Requests.Exam.GetQuestionSubmissionRequest;
import com.oea.online_exam_app.Requests.Exam.ReplaceExamQuestionRequest;
import com.oea.online_exam_app.Requests.Exam.SubmitCodeReviewRequest;
import com.oea.online_exam_app.Requests.Exam.SubmitExamRequest;
import com.oea.online_exam_app.Requests.Exam.SubmitMCQQuestionRequest;
import com.oea.online_exam_app.Requests.Exam.SubmitProgrammingQuestionRequest;
import com.oea.online_exam_app.Requests.Exam.UpdateExamRequest;
import com.oea.online_exam_app.Responses.Base.GetListWithPagingSearchResponse;
import com.oea.online_exam_app.Responses.BaseResponse;
import com.oea.online_exam_app.Responses.Exam.GetExamPassingCriteriaResponse;
import com.oea.online_exam_app.Responses.Exam.GetExamQuestionsResponse;
import com.oea.online_exam_app.Responses.Exam.GetExamResponse;
import com.oea.online_exam_app.Responses.Exam.GetExamStatusResponse;
import com.oea.online_exam_app.Responses.Exam.GetExamSubmissionListResponse;
import com.oea.online_exam_app.Responses.Exam.GetExamsResponse;
import com.oea.online_exam_app.Services.ExamQuestionsService;
import com.oea.online_exam_app.Services.ExamService;
import com.oea.online_exam_app.Services.ExamSubmissionService;
import com.oea.online_exam_app.Services.ProgrammingSubmissionService;
import com.oea.online_exam_app.Services.QuestionsSubmissionService;
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

    @Autowired
    ExamRepo examRepo;
    @Autowired
    ExamQuestionsRepo examQuestionsRepo;

    @Autowired
    DifficultyRepo difficultyRepo;

    @Autowired
    QuestionTypeRepo questionTypeRepo;

    @Autowired
    ExamSubmissionService examSubmissionService;

    @Autowired
    QuestionsSubmissionService questionsSubmissionService;

    @Autowired
    ProgrammingSubmissionService programmingSubmissionService;

    @Autowired
    QuestionSubmissionRepo questionSubmissionRepo;

    @Autowired
    ProgrammingSubmissionRepo programmingSubmissionRepo;

    @PostMapping("/create")
    @Transactional
    @JsonView(View.Admin.class)
    public ResponseEntity<BaseResponse> createExam(@RequestBody CreateExamRequest request) {
        try {
            Exam exam = new Exam();
            int totalMarks = 0;
            exam.setExamCode(request.getExamCode());
            exam.setExamDate(request.getExamDate());
            exam.setExamStartTime(request.getExamStartTime());
            exam.setExamEndTime(request.getExamEndTime());
            exam.setExamDurationInMinutes(request.getExamDurationInMinutes());
            exam.setPassingCriteria(passingCriteriaRepo.findByPassingCriteriaText(request.getPassingCriteria()));
            exam.setPassingValue(request.getPassingValue());
            exam.setTotalMarks(request.getTotalMarks());
            examService.createExam(exam);
            QuestionType questionTypeMCQ = questionTypeRepo.findByQuestionTypeText(QuestionTypeEnum.MCQ.name())
                    .orElseThrow(() -> new UnexpectedException("something went wrong, not able to fetch questionType"));
            QuestionType questionTypePro = questionTypeRepo.findByQuestionTypeText(QuestionTypeEnum.Programming.name())
                    .orElseThrow(() -> new UnexpectedException("something went wrong, not able to fetch questionType"));

            if (request.getMcqQuestions() != null) {
                for (CreateExamRequest.QuestionsMCQ cq : request.getMcqQuestions()) {
                    totalMarks++;
                    Category category = categoryRepo.findById(cq.getCategoryId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid categoryId"));
                    int noOfQuestions = cq.getNoOfQuestions();
                    List<Question> questions = selectRandomMCQQuestions(noOfQuestions, category, questionTypeMCQ);
                    if (examQuestionsService.createExamQuestions(exam, questions) == 0) {
                        throw new Error("Error while creating exam");
                    }
                }
            }
            if (request.getProQuestions() != null) {
                for (CreateExamRequest.QuestionsPro cq : request.getProQuestions()) {
                    totalMarks++;
                    Difficulty difficulty = difficultyRepo.findById(cq.getDifficultyId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid difficultyId"));
                    totalMarks += difficulty.getDifficultyWeight();
                    int noOfQuestions = cq.getNoOfQuestions();
                    List<Question> questions = selectRandomProQuestions(noOfQuestions, difficulty, questionTypePro);
                    if (examQuestionsService.createExamQuestions(exam, questions) == 0) {
                        throw new Error("Error while creating exam");
                    }
                }
            }
            exam.setTotalMarks(totalMarks);
            examRepo.save(exam);
            BaseResponse createQuestionResponse = new BaseResponse("success", "Exam Created Successfully");
            return ResponseEntity.status(201).body(createQuestionResponse);
        } catch (Exception e) {
            BaseResponse createQuestionResponse = new BaseResponse("failed",
                    "Error" + e.toString());
            return ResponseEntity.status(500).body(createQuestionResponse);
        }
    }

    @PostMapping("/update")
    @Transactional
    @JsonView(View.Admin.class)
    public ResponseEntity<BaseResponse> updateExam(@RequestBody UpdateExamRequest request) {
        try {
            Exam exam = request.getExam();
            int examId = exam.getExamId();
            List<UpdateExamRequest.QuestionsUpdateRequest> questions = request.getQuestions();
            System.out.println(questions);
            if (examService.updateExam(exam, exam.getExamId()) > 0) {
                // if(!questions.isEmpty()){
                // questions.forEach(question->{
                // System.out.println(question);
                // if(examQuestionsService.updateExamQuestionByIds(examId,
                // question.getOldQuestionsId(), question.getNewQuestionId()) == 0){
                // throw new Error("Error while updating exam question : "+
                // question.toString());
                // }
                // });
                // }
                BaseResponse createQuestionResponse = new BaseResponse("success", "Exam Updated Successfully");
                return ResponseEntity.status(200).body(createQuestionResponse);
            }
            throw new Error("Error while updating exam");
        } catch (Exception e) {
            BaseResponse createQuestionResponse = new BaseResponse("failed",
                    "Error" + e.toString());
            return ResponseEntity.status(500).body(createQuestionResponse);
        }
    }

    @PostMapping("/delete")
    @Transactional
    @JsonView(View.Admin.class)
    public ResponseEntity<BaseResponse> deleteExam(@RequestBody DeleteExamRequest request) {
        try {
            int examId = request.getExamId();
            int response = examService.deleteExam(examId);
            if (response > 0) {
                BaseResponse createQuestionResponse = new BaseResponse("success", "Exam Updated Successfully");
                return ResponseEntity.status(200).body(createQuestionResponse);
            } else if (response == -1) {
                BaseResponse createQuestionResponse = new BaseResponse("failed", "Exam Not Found");
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

            List<Exam> exams = examService.getAllExams(page, limit);
            if (exams == null || exams.isEmpty()) {
                GetExamsResponse getAllExamsResponse = new GetExamsResponse("success", "Exams Not Found", null);
                return ResponseEntity.status(204).body(getAllExamsResponse);
            }

            GetExamsResponse getAllExamsResponse = new GetExamsResponse("success", "OK", exams);
            return ResponseEntity.status(200).body(getAllExamsResponse);
        } catch (Exception e) {
            GetExamsResponse getAllExamsResponse = new GetExamsResponse("failed",
                    "Error" + e.toString(), null);
            return ResponseEntity.status(500).body(getAllExamsResponse);
        }
    }

    public List<Question> selectRandomMCQQuestions(int noOfQuestions, Category category, QuestionType questionType) {

        List<Question> questions = questionRepo.findRandomMCQQuestionsByCategory(category.getCategoryId(),
                noOfQuestions, questionType.getQuestionTypeId());
        if (questions.size() == noOfQuestions) {
            return questions;
        }
        throw new Error("Unable to fetch questions... not enough questions");
    }

    public List<Question> selectRandomProQuestions(int noOfQuestions, Difficulty difficulty,
            QuestionType questionType) {

        List<Question> questions = questionRepo.findRandomProQuestionsByDifficulty(difficulty.getDifficultyId(),
                noOfQuestions, questionType.getQuestionTypeId());
        if (questions.size() == noOfQuestions) {
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
            if (exam == null) {
                GetExamResponse getAllExamsResponse = new GetExamResponse("success", "Exam Not Found", null);
                return ResponseEntity.status(404).body(getAllExamsResponse);
            }

            GetExamResponse getAllExamsResponse = new GetExamResponse("success", "OK", exam);
            return ResponseEntity.status(200).body(getAllExamsResponse);
        } catch (Exception e) {
            GetExamResponse getAllExamsResponse = new GetExamResponse("failed",
                    "Error" + e.toString(), null);
            return ResponseEntity.status(500).body(getAllExamsResponse);
        }
    }

    @PostMapping("/getExamQuestions")
    @Transactional
    public ResponseEntity<GetExamQuestionsResponse> getExamsQuestions(@RequestBody GetExamQuestionsRequest request) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(email);
            User user = userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid user"));
            Exam exam = examRepo.findByExamCode(request.getExamCode())
                    .orElseThrow(() -> new IllegalArgumentException(ExamStatusCodeEnum.InvalidCode.name()));

            QuestionsDTO questions = examService.getExamQuestions(exam, user);
            ExamSubmission examSubmission = examSubmissionRepo.findByUserAndExam(user, exam);
            if (examSubmission.getScoredMarks() != 0) {
                GetExamQuestionsResponse getExamQuestionsResponse = new GetExamQuestionsResponse(
                        ExamStatusCodeEnum.AlreadySubmitted.name(), "Exam Over", -1, null, -1, -1,
                        ExamStatusCodeEnum.AlreadySubmitted.getCode());
                return ResponseEntity.status(200).body(getExamQuestionsResponse);
            }
            LocalTime nowTime = LocalTime.now();
            LocalTime examStartTime = examSubmission.getExamStartTime().toLocalTime();
            LocalTime examEndTime = examSubmission.getExamEndTime().toLocalTime();
            System.out.println("examStartTime : " + examStartTime);
            System.out.println("examEndTime : " + examEndTime);
            System.out.println("nowTime : " + nowTime);
            if (examEndTime.isBefore(nowTime)) {
                GetExamQuestionsResponse getExamQuestionsResponse = new GetExamQuestionsResponse(
                        ExamStatusCodeEnum.Timeout.name(), "Exam Time Over", -1, null, -1, -1,
                        ExamStatusCodeEnum.Timeout.getCode());
                return ResponseEntity.status(200).body(getExamQuestionsResponse);
            }
            int remainingTime = (int) Duration.between(nowTime, examEndTime).toMinutes();
            int totalTime = (int) Duration.between(examStartTime, examEndTime).toMinutes();
            if (!questions.getQuestionsMCQ().isEmpty() || !questions.getQuestionsPro().isEmpty()) {
                GetExamQuestionsResponse getExamQuestionsResponse = new GetExamQuestionsResponse(
                        ExamStatusCodeEnum.InProgress.name(), "OK", examSubmission.getExamSubmissionId(), questions,
                        remainingTime, totalTime, ExamStatusCodeEnum.InProgress.getCode());
                return ResponseEntity.status(200).body(getExamQuestionsResponse);
            }
            GetExamQuestionsResponse getExamQuestionsResponse = new GetExamQuestionsResponse(
                    ExamStatusCodeEnum.SomeThingWentWrong.name(), "Unable to fetch questions", -1, null, -1, -1,
                    ExamStatusCodeEnum.SomeThingWentWrong.getCode());
            return ResponseEntity.status(400).body(getExamQuestionsResponse);
        } catch (Exception e) {
            System.out.println(e.getCause());
            if (e.getMessage().equals(ExamStatusCodeEnum.InvalidCode.name())) {
                GetExamQuestionsResponse getExamQuestionsResponse = new GetExamQuestionsResponse(
                        ExamStatusCodeEnum.InvalidCode.name(),
                        "Invalid Exam Code", -1, null, -1, -1, ExamStatusCodeEnum.InvalidCode.getCode());
                return ResponseEntity.status(400).body(getExamQuestionsResponse);
            }
            GetExamQuestionsResponse getExamQuestionsResponse = new GetExamQuestionsResponse(
                    ExamStatusCodeEnum.ServerError.name(),
                    "Error " + e.toString(), -1, null, -1, -1, ExamStatusCodeEnum.ServerError.getCode());
            return ResponseEntity.status(500).body(getExamQuestionsResponse);
        }
    }

    @PostMapping("/getExamStatus")
    @Transactional
    public ResponseEntity<GetExamStatusResponse> getExamStatus(@RequestBody GetExamStatusRequest request) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(email);
            User user = userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid user"));
            Exam exam = examRepo.findByExamCode(request.getExamCode())
                    .orElseThrow(() -> new IllegalArgumentException(ExamStatusCodeEnum.InvalidCode.name()));
            LocalTime examStartTime = exam.getExamStartTime();
            LocalTime examEndTime = exam.getExamEndTime();
            LocalDate examDate = exam.getExamDate();

            LocalDateTime startDateTime = LocalDateTime.of(examDate, examStartTime);
            LocalDateTime endDateTime = LocalDateTime.of(examDate, examEndTime);
            LocalDateTime currentDateTime = LocalDateTime.now();

            if (currentDateTime.isAfter(endDateTime)) {
                GetExamStatusResponse getExamStatus = new GetExamStatusResponse(
                        ExamStatusCodeEnum.Timeout.name(),
                        "Exam Time Over",
                        ExamStatusCodeEnum.Timeout.getCode());
                return ResponseEntity.status(200).body(getExamStatus);
            }

            if (currentDateTime.isBefore(startDateTime)) {
                GetExamStatusResponse getExamStatus = new GetExamStatusResponse(
                        ExamStatusCodeEnum.NotStarted.name(),
                        "Exam Not Started",
                        ExamStatusCodeEnum.NotStarted.getCode());
                return ResponseEntity.status(200).body(getExamStatus);
            }
            ExamSubmission examSubmission = examSubmissionRepo.findByUserAndExam(user, exam);
            if (examSubmission != null) {
                if (examSubmission.getScoredMarks() != 0) {
                    GetExamStatusResponse getExamStatus = new GetExamStatusResponse(
                            ExamStatusCodeEnum.AlreadySubmitted.name(), "Exam Over",
                            ExamStatusCodeEnum.AlreadySubmitted.getCode());
                    return ResponseEntity.status(200).body(getExamStatus);
                }
                LocalDateTime nowTime = LocalDateTime.now();
                LocalDateTime examStartedEndTime = examSubmission.getExamEndTime();
                System.out.println("examStartTime : " + examStartTime);
                System.out.println("examEndTime : " + examStartedEndTime);
                System.out.println("nowTime : " + nowTime);
                if (examStartedEndTime.isBefore(nowTime)) {
                    GetExamStatusResponse getExamStatus = new GetExamStatusResponse(ExamStatusCodeEnum.Timeout.name(),
                            "Exam Time Over", ExamStatusCodeEnum.Timeout.getCode());
                    return ResponseEntity.status(200).body(getExamStatus);
                }
            }
            GetExamStatusResponse getExamStatus = new GetExamStatusResponse(ExamStatusCodeEnum.InProgress.name(),
                    "Exam Started", ExamStatusCodeEnum.InProgress.getCode());
            return ResponseEntity.status(200).body(getExamStatus);
        } catch (Exception e) {
            System.out.println(e);
            if (e.getMessage().equals(ExamStatusCodeEnum.InvalidCode.name())) {
                GetExamStatusResponse getExamStatus = new GetExamStatusResponse(ExamStatusCodeEnum.InvalidCode.name(),
                        "Invalid Exam Code", ExamStatusCodeEnum.InvalidCode.getCode());
                return ResponseEntity.status(400).body(getExamStatus);
            }
            GetExamStatusResponse getExamStatus = new GetExamStatusResponse(ExamStatusCodeEnum.ServerError.name(),
                    "Error " + e.toString(), ExamStatusCodeEnum.ServerError.getCode());
            return ResponseEntity.status(500).body(getExamStatus);
        }
    }

    @PostMapping("/submitQuestionOption")
    @Transactional
    public ResponseEntity<BaseResponse> submitQuestionOption(@RequestBody SubmitMCQQuestionRequest request) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid user"));

            // check time
            if (examService.updateSelectedOption(request.getQuestionSubmissionId(), request.getOptionId(),
                    request.getStatusId()) != 1) {
                throw new Error("Error while submitting question");
            }
            BaseResponse getExamQuestionsResponse = new BaseResponse("success", "OK");
            return ResponseEntity.status(200).body(getExamQuestionsResponse);
        } catch (Exception e) {
            BaseResponse getExamQuestionsResponse = new BaseResponse("failed", "Error " + e.toString());
            return ResponseEntity.status(200).body(getExamQuestionsResponse);
        }
    }

    @PostMapping("/submitProgrammingQuestion")
    @Transactional
    public ResponseEntity<BaseResponse> submitProgrammingQuestion(
            @RequestBody SubmitProgrammingQuestionRequest request) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid user"));
            // check time
            if (examService.submitCode(request.getProgrammingSubmissionId(), request.getSubmittedCode()) != 1) {
                // throw new Error("Error while submitting programming question");
            }
            BaseResponse submitProgrammingQuestionResponse = new BaseResponse("success", "OK");
            return ResponseEntity.status(200).body(submitProgrammingQuestionResponse);
        } catch (Exception e) {
            BaseResponse getExamQuestionsResponse = new BaseResponse("failed", "Error " + e.toString());
            return ResponseEntity.status(200).body(getExamQuestionsResponse);
        }
    }

    @PostMapping("/submitExam")
    @Transactional
    public ResponseEntity<BaseResponse> submitExam(@RequestBody SubmitExamRequest request) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid user"));
            // check time
            if (examService.submitExam(request.getExamSubmissionId()) != 1) {
                throw new Error("Error while submitting question");
            }
            BaseResponse generateResultMCQResponse = new BaseResponse("success", "OK");
            return ResponseEntity.status(200).body(generateResultMCQResponse);
        } catch (Exception e) {
            BaseResponse getExamQuestionsResponse = new BaseResponse("failed", "Error " + e.toString());
            return ResponseEntity.status(200).body(getExamQuestionsResponse);
        }
    }

    @GetMapping("/getPassingCriteria")
    public ResponseEntity<GetExamPassingCriteriaResponse> getPassingCriteria() {
        try {
            List<PassingCriteria> passingCriterias = passingCriteriaRepo.findAll();
            if (passingCriterias != null && !passingCriterias.isEmpty()) {
                return ResponseEntity.status(200)
                        .body(new GetExamPassingCriteriaResponse("success", "OK", passingCriterias));
            }
            return ResponseEntity.status(200)
                    .body(new GetExamPassingCriteriaResponse("success", "no passing criteria found", null));

        } catch (Exception e) {
            return ResponseEntity.status(200)
                    .body(new GetExamPassingCriteriaResponse("failed", "Error " + e.toString(), null));
        }
    }

    @PostMapping("/getExamList")
    public ResponseEntity<GetListWithPagingSearchResponse> getExamList(
            @RequestBody GetListWithPagingSearchRequest request) {
        try {
            List<Exam> exams = examService.getExams(request.getPage(), request.getLimit(), request.getSearch());
            long examCount = examRepo.getExamCountWithSearch(request.getSearch());
            if (exams != null && !exams.isEmpty()) {
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                        "success", "ok", exams, examCount));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed", "exams not found", null, 0));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
                    "failed", "Internal server error", null, 0));
        }

    }

    @PostMapping("/getExamQuestionByExamId")
    public ResponseEntity<GetListWithPagingSearchResponse> getExamQuestionByExamId(
            @RequestBody GetByIdRequest request) {
        try {
            List<ExamQuestions> examQuestions = examQuestionsService.getExamQuestionByExamId(request.getId());
            if (examQuestions != null && !examQuestions.isEmpty()) {
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                        "success", "ok", examQuestions, examQuestions.size()));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed", "exams not found", null, 0));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
                    "failed", "Internal server error", null, 0));
        }

    }

    @PostMapping("/getExamQuestionByCategoryAndQuestionType")
    public ResponseEntity<GetListWithPagingSearchResponse> getExamQuestionByCategoryAndQuestionType(
            @RequestBody GetExamQuestionByCategoryAndQuestionTypeRequest request) {
        try {
            List<Question> replacementQuestions = examQuestionsService.getReplacementQuestions(request.getCategoryId(),
                    request.getQuestionTypeId(), request.getExamId());
            if (replacementQuestions != null && !replacementQuestions.isEmpty()) {
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                        "success", "ok", replacementQuestions, replacementQuestions.size()));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed", "exams not found", null, 0));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
                    "failed", "Internal server error", null, 0));
        }

    }

    @PostMapping("/replaceExamQuestions")
    @Transactional
    public ResponseEntity<BaseResponse> replaceExamQuestions(@RequestBody ReplaceExamQuestionRequest request) {
        try {
            examQuestionsRepo.findById(request.getOldExamQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid examQuestionId"));
            if (examQuestionsRepo.existsById(request.getOldExamQuestionId())
                    && questionRepo.existsById(request.getNewQuestionId())) {
                if (examQuestionsService.updateExamQuestionByIds(request.getExamId(), request.getOldExamQuestionId(),
                        request.getNewQuestionId()) > 0) {
                    BaseResponse replaceExamQuestionsResponse = new BaseResponse("success",
                            "Question Replaced Successfully");
                    return ResponseEntity.status(200).body(replaceExamQuestionsResponse);
                }
            }

            BaseResponse replaceExamQuestionsResponse = new BaseResponse("failed",
                    "Unable to Replaced Question Successfully");
            return ResponseEntity.status(400).body(replaceExamQuestionsResponse);
        } catch (Exception e) {
            BaseResponse createQuestionResponse = new BaseResponse("failed",
                    "Error" + e.toString());
            return ResponseEntity.status(500).body(createQuestionResponse);
        }
    }

    @PostMapping("/getExamResultDetails")
    public ResponseEntity<GetListWithPagingSearchResponse> getExamResultDetails(
            @RequestBody GetListWithPagingSearchRequest request) {
        try {
            List<ExamResultDetailDTO> examResultDetails = examService.getExamResultDetails(request.getPage(),
                    request.getLimit(), request.getSearch());
            long examCount = examRepo.getExamCountWithSearch(request.getSearch());
            if (examResultDetails != null && !examResultDetails.isEmpty()) {
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                        "success", "ok", examResultDetails, examCount));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed", "exams not found", null, 0));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
                    "failed", "Internal server error", null, 0));
        }

    }

    @PostMapping("/getExamSubmissions")
    public ResponseEntity<GetExamSubmissionListResponse> getExamSubmissions(
            @RequestBody GetExamSubmissionRequest request) {
        try {
            List<ExamSubmissionDTO> examSubmissions = examSubmissionService.getExamSubmissions(request.getExamId(),
                    request.getPage(), request.getLimit(), request.getSearch());
            long examCount = examSubmissionRepo.getExamSubmissionCountWithSearch(request.getExamId(),
                    request.getSearch());
            if (examSubmissions != null && !examSubmissions.isEmpty()) {
                Exam exam = examRepo.findById(request.getExamId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid examId"));
                long proQuestionCount = examQuestionsRepo.getProQuestionCount(request.getExamId());
                long mcqQuestionCount = examQuestionsRepo.getMcqQuestionCount(request.getExamId());
                return ResponseEntity.status(200).body(new GetExamSubmissionListResponse(
                        "success", "ok", examSubmissions, examCount, exam.getTotalMarks(), (int) proQuestionCount,
                        (int) mcqQuestionCount));
            }
            return ResponseEntity.status(200).body(new GetExamSubmissionListResponse(
                    "failed", "exams not found", null, 0, 0, 0, 0));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetExamSubmissionListResponse(
                    "failed", "Internal server error", null, 0, 0, 0, 0));
        }

    }

    @PostMapping("/getMCQSubmissions")
    public ResponseEntity<GetListWithPagingSearchResponse> getMCQSubmissions(
            @RequestBody GetQuestionSubmissionRequest request) {
        try {
            List<MCQSubmissionDTO> examSubmissions = questionsSubmissionService.getQuestionSubmissions(
                    request.getExamSubmissionId(), request.getPage(), request.getLimit(), request.getSearch());
            long examCount = questionSubmissionRepo.getQuestionSubmissionCountWithSearch(request.getExamSubmissionId(),
                    request.getSearch());
            if (examSubmissions != null && !examSubmissions.isEmpty()) {
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                        "success", "ok", examSubmissions, examCount));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed", "exams not found", null, 0));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
                    "failed", "Internal server error", null, 0));
        }

    }

    @PostMapping("/getProSubmissions")
    public ResponseEntity<GetListWithPagingSearchResponse> getProSubmissions(
            @RequestBody GetQuestionSubmissionRequest request) {
        try {
            List<ProSubmissionDTO> proSubmissionDTOs = programmingSubmissionService.getQuestionSubmissions(
                    request.getExamSubmissionId(), request.getPage(), request.getLimit(), request.getSearch());
            long examCount = programmingSubmissionRepo
                    .getProgrammingSubmissionCountWithSearch(request.getExamSubmissionId(), request.getSearch());
            if (proSubmissionDTOs != null && !proSubmissionDTOs.isEmpty()) {
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                        "success", "ok", proSubmissionDTOs, examCount));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed", "exams not found", null, 0));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
                    "failed", "Internal server error", null, 0));
        }

    }

    @PostMapping("/submitCodeReview")
    public ResponseEntity<BaseResponse> submitCodeReview(@RequestBody SubmitCodeReviewRequest request) {
        try {
            if (programmingSubmissionService.submitCodeReview(request.getProgrammingSubmissionId(),
                    request.getIsCorrect()) > 0) {
                return ResponseEntity.status(200)
                        .body(new BaseResponse("success", "Code Review Submitted Successfully"));
            }
            return ResponseEntity.status(200).body(new BaseResponse("failed", "Code Review Not Submitted"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new BaseResponse("failed", "Internal server error"));
        }

    }

}
