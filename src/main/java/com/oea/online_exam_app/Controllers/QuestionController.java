/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Controllers;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oea.online_exam_app.Enums.QuestionTypeEnum;
import com.oea.online_exam_app.Models.Category;
import com.oea.online_exam_app.Models.Difficulty;
import com.oea.online_exam_app.Models.Question;
import com.oea.online_exam_app.Models.QuestionExample;
import com.oea.online_exam_app.Models.QuestionOption;
import com.oea.online_exam_app.Models.QuestionType;
import com.oea.online_exam_app.Repo.CategoryRepo;
import com.oea.online_exam_app.Repo.DifficultyRepo;
import com.oea.online_exam_app.Repo.QuestionTypeRepo;
import com.oea.online_exam_app.Requests.Question.CreateQuestionRequest;
import com.oea.online_exam_app.Requests.Question.CreateQuestionRequest.QuestionExampleRequest;
import com.oea.online_exam_app.Requests.Question.CreateQuestionRequest.QuestionOptionRequest;
import com.oea.online_exam_app.Responses.Question.CreateQuestionResponse;
import com.oea.online_exam_app.Services.QuestionExampleService;
import com.oea.online_exam_app.Services.QuestionOptionService;
import com.oea.online_exam_app.Services.QuestionService;

/**
 *
 * @author tirth
 */
@RestController
@RequestMapping("api/v1/question")

public class QuestionController {
    public enum QuestionTypeText {
        MCQ,
        PROGRAMMING
    }

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionOptionService questionOptionService;

    @Autowired
    QuestionExampleService questionExampleService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    DifficultyRepo difficultyRepo;

    @Autowired
    QuestionTypeRepo questionTypeRepo;

    @PostMapping("/create/single")
    public ResponseEntity<CreateQuestionResponse> createQuestions(@RequestBody CreateQuestionRequest request) {
        try {
            Category category = categoryRepo.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid categoryId"));
            Difficulty difficulty = difficultyRepo.findById(request.getDifficultyId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid difficultyId"));
            QuestionType questionType = questionTypeRepo.findById(request.getQuestionTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid questionTypeId"));
            Question question = new Question();
            
            question.setCategory(category);
            question.setDifficulty(difficulty);
            question.setQuestionType(questionType);
            question.setQuestionText(request.getQuestionText());

            int questionId = questionService.createQuestion(question);
            if (questionType.getQuestionTypeText().equals(QuestionTypeEnum.MCQ.name())) {
                List<QuestionOptionRequest> questionOptions = request.getQuestionOptions();
                System.out.println(questionOptions);
                validateQuestionOptions(questionOptions,QuestionOptionRequest::getIsCorrect);
                questionOptions.forEach(option -> {
                    QuestionOption questionOption = new QuestionOption();
                    questionOption.setQuestion(question);
                    questionOption.setOptionText(option.getOptionText());
                    questionOption.setIsCorrect(option.getIsCorrect());
                    System.out.println(option.getIsCorrect());
                    questionOptionService.createQuestionOption(questionOption);
                });
                CreateQuestionResponse createQuestionResponse = new CreateQuestionResponse("success",
                        "question created successfully");
                return ResponseEntity.status(201).body(createQuestionResponse);
            } else if (questionType.getQuestionTypeText().equals(QuestionTypeEnum.Programming.name())) {
                List<QuestionExampleRequest> questionExamples = request.getQuestionExamples();
                validateQuestionExamples(questionExamples);

                questionExamples.forEach(option -> {
                    QuestionExample questionExample = new QuestionExample();
                    questionExample.setQuestion(question);
                    questionExample.setInputText(option.getInputText());
                    questionExample.setOutputText(option.getOutputText());
                    questionExampleService.createQuestionExample(questionExample);
                });
                CreateQuestionResponse createQuestionResponse = new CreateQuestionResponse("success",
                        "question created successfully");
                return ResponseEntity.status(201).body(createQuestionResponse);
            } else {
                questionService.deleteQuestion(questionId);
                CreateQuestionResponse createQuestionResponse = new CreateQuestionResponse("failed",
                        "invalid questionTypeId");
                return ResponseEntity.status(400).body(createQuestionResponse);
            }
        } catch (Exception e) {
            CreateQuestionResponse createQuestionResponse = new CreateQuestionResponse("failed",
                    "Error" + e.toString());
            return ResponseEntity.status(500).body(createQuestionResponse);
        }

    }

    @PostMapping("/create/bulk")
    @Transactional
    public ResponseEntity<CreateQuestionResponse> createQuestion(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(400)
                        .body(new CreateQuestionResponse("failed", "Please upload a CSV file"));
            }

            List<Question> questions = new ArrayList<>();
            try (CSVParser csvParser = new CSVParser(new InputStreamReader(file.getInputStream()),
                    CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

                List<QuestionOption> allQuestionOptions = new ArrayList<>();
                List<QuestionExample> allQuestionExamples = new ArrayList<>();
                for (CSVRecord record : csvParser) {
                    try {
                        String questionText = record.get("questionText");
                        Category category = categoryRepo.findByCategoryText(record.get("category"))
                                .orElseThrow(() -> new IllegalArgumentException("Invalid category: "));
                        Difficulty difficulty = difficultyRepo.findByDifficultyText(record.get("difficulty"))
                                .orElseThrow(
                                        () -> new IllegalArgumentException("Invalid difficultyId:"));
                        QuestionType questionType = questionTypeRepo.findByQuestionTypeText(record.get("questionType"))
                                .orElseThrow(() -> new IllegalArgumentException(
                                        "Invalid questionType: "));
                        Question question = new Question();
                        question.setCategory(category);
                        question.setDifficulty(difficulty);
                        question.setQuestionType(questionType);
                        question.setQuestionText(questionText);
                        if (questionType.getQuestionTypeText().equals(QuestionTypeEnum.MCQ.name())) {
                            List<QuestionOption> questionOptions = new ArrayList<>();
                            int optionCount = Integer.parseInt(record.get("optionCount")); 
                            for (int i = 1; i <= optionCount; i++) {
                                QuestionOption option = new QuestionOption();
                                option.setOptionText(record.get("optionText" + i));
                                option.setQuestion(question);                                
                                option.setIsCorrect(Boolean.valueOf(record.get("optionCorrect" + i)));
                                questionOptions.add(option);
                            }
                            validateQuestionOptions(questionOptions,QuestionOption::getIsCorrect);
                            allQuestionOptions.addAll(questionOptions);
                        } else if (questionType.getQuestionTypeText().equals(QuestionTypeEnum.Programming.name())) {
                            List<QuestionExample> questionExamples = new ArrayList<>();
                            int exampleCount = 2;
                            for (int i = 1; i <= exampleCount; i++) {
                                QuestionExample example = new QuestionExample();
                                example.setInputText(record.get("inputText" + i));
                                example.setQuestion(question);  
                                example.setOutputText(record.get("outputText" + i));
                                questionExamples.add(example);
                            }
                            validateQuestionExamples(questionExamples);
                            allQuestionExamples.addAll(questionExamples);
                        }else {
                            throw new IllegalArgumentException(
                                "Error in record: " + record.toString() + ", Reason: Invalid QuestionType");
                        }
                        questions.add(question);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException(
                                "Error in record: " + record.toString() + ", Reason: " + e.getMessage());
                    }
                }
                questionService.createQuestions(questions);
                questionExampleService.createQuestionExamples(allQuestionExamples);
                questionOptionService.createQuestionOptions(allQuestionOptions);
                return ResponseEntity.status(201)
                        .body(new CreateQuestionResponse("success", "All questions created successfully"));
            } catch (Exception e) {
                throw new IllegalArgumentException("Error processing the file: " + e.getMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new CreateQuestionResponse("failed", "Error: " + e.getMessage()));
        }
    }
    private <T> void validateQuestionOptions(List<T> options, Function<T, Boolean> isCorrectExtractor) {
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("MCQ questions must have options.");
        }
        if (options.stream().noneMatch(isCorrectExtractor::apply)) {
            throw new IllegalArgumentException("MCQ questions must have at least one correct option.");
        }
    }
    private <T> void validateQuestionExamples(List<T> examples) {
        if (examples == null || examples.isEmpty()) {
            throw new IllegalArgumentException("Programming questions must have examples.");
        }
    }
}
