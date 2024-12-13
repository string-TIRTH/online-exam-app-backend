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
import org.springframework.web.bind.annotation.GetMapping;
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
import com.oea.online_exam_app.Repo.QuestionRepo;
import com.oea.online_exam_app.Repo.QuestionTypeRepo;
import com.oea.online_exam_app.Requests.Base.GetListWithPagingSearchRequest;
import com.oea.online_exam_app.Requests.Question.CreateQuestionCategoryRequest;
import com.oea.online_exam_app.Requests.Question.CreateQuestionDifficultyRequest;
import com.oea.online_exam_app.Requests.Question.CreateQuestionRequest;
import com.oea.online_exam_app.Requests.Question.CreateQuestionRequest.QuestionExampleRequest;
import com.oea.online_exam_app.Requests.Question.CreateQuestionRequest.QuestionOptionRequest;
import com.oea.online_exam_app.Requests.Question.CreateQuestionTypeRequest;
import com.oea.online_exam_app.Requests.Question.DeleteCategoryRequest;
import com.oea.online_exam_app.Requests.Question.DeleteDifficultyRequest;
import com.oea.online_exam_app.Requests.Question.DeleteExamplesRequest;
import com.oea.online_exam_app.Requests.Question.DeleteOptionsRequest;
import com.oea.online_exam_app.Requests.Question.DeleteQuestionRequest;
import com.oea.online_exam_app.Requests.Question.DeleteQuestionTypeRequest;
import com.oea.online_exam_app.Requests.Question.UpdateCategoryRequest;
import com.oea.online_exam_app.Requests.Question.UpdateDifficultyRequest;
import com.oea.online_exam_app.Requests.Question.UpdateQuestionRequest;
import com.oea.online_exam_app.Requests.Question.UpdateQuestionTypeRequest;
import com.oea.online_exam_app.Responses.Base.GetListWithPagingSearchResponse;
import com.oea.online_exam_app.Responses.BaseResponse;
import com.oea.online_exam_app.Responses.Question.CreateQuestionResponse;
import com.oea.online_exam_app.Responses.Question.GetQuestionAssosiateDataReponse;
import com.oea.online_exam_app.Services.CategoryService;
import com.oea.online_exam_app.Services.DifficultyService;
import com.oea.online_exam_app.Services.QuestionExampleService;
import com.oea.online_exam_app.Services.QuestionOptionService;
import com.oea.online_exam_app.Services.QuestionService;
import com.oea.online_exam_app.Services.QuestionTypeService;

import jakarta.transaction.Transactional;

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
    CategoryRepo categoryRepo;

    @Autowired
    DifficultyRepo difficultyRepo;

    @Autowired
    QuestionTypeRepo questionTypeRepo;

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    CategoryService categoryService;

    @Autowired
    DifficultyService difficultyService;

    @Autowired
    QuestionTypeService questionTypeService;

    
    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionOptionService questionOptionService;

    @Autowired
    QuestionExampleService questionExampleService;


    @PostMapping("/create/single")
    public ResponseEntity<CreateQuestionResponse> createQuestions(@RequestBody CreateQuestionRequest request) {
        try {
            Category category = categoryRepo.findByCategoryText(request.getCategory())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category name"));
            Difficulty difficulty = difficultyRepo.findByDifficultyText(request.getDifficulty())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid difficulty name"));
            QuestionType questionType = questionTypeRepo.findByQuestionTypeText(request.getQuestionType())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid questionType name"));
            Question question = new Question();

            question.setCategory(category);
            question.setDifficulty(difficulty);
            question.setQuestionType(questionType);
            question.setQuestionText(request.getQuestionText());

            int questionId = questionService.createQuestion(question);
            if (questionType.getQuestionTypeText().equals(QuestionTypeEnum.MCQ.name())) {
                List<QuestionOptionRequest> questionOptions = request.getQuestionOptions();
                System.out.println(questionOptions);
                validateQuestionOptions(questionOptions, QuestionOptionRequest::getIsCorrect);
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
                            validateQuestionOptions(questionOptions, QuestionOption::getIsCorrect);
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
                        } else {
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
    @PostMapping("/update")
    public ResponseEntity<BaseResponse> updateQuestion(@RequestBody UpdateQuestionRequest request) {
        try {
            System.out.println(request.getQuestion());
            categoryRepo.findById(request.getQuestion().getCategory().getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category name"));
            difficultyRepo.findById(request.getQuestion().getDifficulty().getDifficultyId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid difficulty name"));
            questionTypeRepo.findById(request.getQuestion().getQuestionType().getQuestionTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid questionType name"));
            Question question = request.getQuestion();
            if(questionService.updateQuestion(question, question.getQuestionId())>0){
                
                if(question.getQuestionType().getQuestionTypeText().equals(QuestionTypeEnum.MCQ.name())){
                    List<QuestionOption> options = request.getQuestion().getOptions();
                    options.forEach(option->{
                        if(option.getOptionId() == -1){
                            option.setQuestion(question);
                            questionOptionService.createQuestionOption(option);
                        }else {
                            questionOptionService.updateQuestionOption(option, option.getOptionId());
                        }
                    });
                }else if (question.getQuestionType().getQuestionTypeText().equals(QuestionTypeEnum.Programming.name())){
                    List<QuestionExample> examples = request.getQuestion().getExamples();
                    examples.forEach(example->{
                        questionExampleService.updateQuestionExample(example, example.getExampleId());
                    });
                }
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","question updated successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to update question"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }
    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> deleteQuestion(@RequestBody DeleteQuestionRequest request) {
        try {
            if(questionService.deleteQuestion(request.getQuestionId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","question deleted successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to delete question"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }
    @PostMapping("/deleteOptions")
    public ResponseEntity<BaseResponse> deleteOption(@RequestBody DeleteOptionsRequest request) {
        try {
            questionRepo.findById(request.getQuestionId()).orElseThrow(()-> new IllegalArgumentException("invalid questionId"));
            List<Integer> optionIds = request.getOptionIds();
            optionIds.forEach((optionId)->{
                questionOptionService.deleteQuestionOption(optionId);
            });
            return ResponseEntity.status(200).body(new BaseResponse(
                "success","options deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }
    }

    @PostMapping("/deleteExamples")
    public ResponseEntity<BaseResponse> deleteOption(@RequestBody DeleteExamplesRequest request) {
        try {
            questionRepo.findById(request.getQuestionId()).orElseThrow(()-> new IllegalArgumentException("invalid questionId"));
            List<Integer> exampleIds = request.getExampleIds();
            exampleIds.forEach((exampleId)->{
                questionExampleService.deleteQuestionExample(exampleId);
            });
            return ResponseEntity.status(200).body(new BaseResponse(
                "success","examples deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
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

    @GetMapping("/getQuestionAssosicateData")
    public ResponseEntity<GetQuestionAssosiateDataReponse> getQuestionAssosicateData() {
        try {
            List<Category> category = categoryService.getAllCategories();
            List<Difficulty> difficulty = difficultyService.getAllDifficulties();
            List<QuestionType> questionType = questionTypeService.getAllQuestionTypes();
            return ResponseEntity.status(200)
                    .body( new GetQuestionAssosiateDataReponse(
                        "success","ok",category,difficulty,questionType));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body( new GetQuestionAssosiateDataReponse(
                        "failed","Internal server error unable to fetch data",null,null,null));
        }

    }
    @PostMapping("/createQuestionType")
    public ResponseEntity<BaseResponse> createQuestionType(@RequestBody CreateQuestionTypeRequest request) {
        try {
            QuestionType questionType = new QuestionType(request.getQuestionTypeText());
            if(questionTypeService.createQuestionType(questionType)!=1){
                return ResponseEntity.status(400).body(new BaseResponse(
                    "failed","unable to create questionType"
                ));
            }
            return ResponseEntity.status(201).body(new BaseResponse(
                    "success","question type created successfully"
                ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new BaseResponse(
                    "failed","Internal server error"
                ));
        }

    }

    @PostMapping("/createCategory")
    public ResponseEntity<BaseResponse> createCategory(@RequestBody CreateQuestionCategoryRequest request) {
        try {
            Category category = new Category(request.getCategoryText());
            if(categoryService.createCategory(category)!=1){
                return ResponseEntity.status(400).body(new BaseResponse(
                    "failed","unable to create category"
                ));
            }
            return ResponseEntity.status(201).body(new BaseResponse(
                    "success","category created successfully"
                ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new BaseResponse(
                    "failed","Internal server error"
                ));
        }

    }

    @PostMapping("/createDifficulty")
    public ResponseEntity<BaseResponse> createQuestionType(@RequestBody CreateQuestionDifficultyRequest request) {
        try {
            Difficulty difficulty = new Difficulty(request.getDifficultyText(),request.getDifficultyWeight());
            if(difficultyService.createDifficulty(difficulty)!=1){
                return ResponseEntity.status(400).body(new BaseResponse(
                    "failed","unable to create question difficulty"
                ));
            }
            return ResponseEntity.status(201).body(new BaseResponse(
                    "success","question difficulty created successfully"
                ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new BaseResponse(
                    "failed","Internal server error"
                ));
        }

    }

    @PostMapping("/getQuestionList")
    public ResponseEntity<GetListWithPagingSearchResponse> getQuestionList(@RequestBody GetListWithPagingSearchRequest request) {
        try {
            List<Question> questions = questionService.getQuestions(request.getPage(),request.getLimit(),request.getSearch());

            long questionCount = questionRepo.getQuestionCountWithSearch(request.getSearch());
            if(questions!= null && !questions.isEmpty()){
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "success","ok",questions,questionCount
                ));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed","questions not found",null,0
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
               "failed","Internal server error",null,0
            ));
        }

    }

    @PostMapping("/getCategoryList")
    public ResponseEntity<GetListWithPagingSearchResponse> getCategoryList(@RequestBody GetListWithPagingSearchRequest request) {
        try {
            List<Category> categories = categoryService.getCategories(request.getPage(),request.getLimit(),request.getSearch());

            long categoryCount = categoryRepo.getCategoryCountWithSearch(request.getSearch());
            if(categories!= null && !categories.isEmpty()){
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "success","ok",categories,categoryCount
                ));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed","categories not found",null,0
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
               "failed","Internal server error",null,0
            ));
        }

    }

    @PostMapping("/getDifficultyList")
    public ResponseEntity<GetListWithPagingSearchResponse> getDifficultyList(@RequestBody GetListWithPagingSearchRequest request) {
        try {
            List<Difficulty> difficulties = difficultyService.getDifficulties(request.getPage(),request.getLimit(),request.getSearch());

            long difficultyCount = difficultyRepo.getDifficultyCountWithSearch(request.getSearch());
            if(difficulties!= null && !difficulties.isEmpty()){
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "success","ok",difficulties,difficultyCount
                ));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed","difficulties not found",null,0
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
               "failed","Internal server error",null,0
            ));
        }

    }

    @PostMapping("/getQuestionTypeList")
    public ResponseEntity<GetListWithPagingSearchResponse> getQuestionTypeList(@RequestBody GetListWithPagingSearchRequest request) {
        try {
            List<QuestionType> questionTypes = questionTypeService.getQuestionTypes(request.getPage(),request.getLimit(),request.getSearch());

            long questionTypeCount = questionTypeRepo.getQuestionTypeCountWithSearch(request.getSearch());
            if(questionTypes!= null && !questionTypes.isEmpty()){
                return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "success","ok",questionTypes,questionTypeCount
                ));
            }
            return ResponseEntity.status(200).body(new GetListWithPagingSearchResponse(
                    "failed","questionTypes not found",null,0
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetListWithPagingSearchResponse(
               "failed","Internal server error",null,0
            ));
        }

    }
    @PostMapping("/updateCategory")
    public ResponseEntity<BaseResponse> updateCategory(@RequestBody UpdateCategoryRequest request) {
        try {
           
            Category category = request.getCategory();
            if(categoryService.updateCategory(category, category.getCategoryId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","category updated successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to update category"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }

    @PostMapping("/updateDifficulty")
    public ResponseEntity<BaseResponse> updateDifficulty(@RequestBody UpdateDifficultyRequest request) {
        try {
           
            Difficulty difficulty = request.getDifficulty();
            if(difficultyService.updateDifficulty(difficulty, difficulty.getDifficultyId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","difficulty updated successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to update difficulty"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }
    @PostMapping("/updateQuestionType")
    public ResponseEntity<BaseResponse> updateQuestionType(@RequestBody UpdateQuestionTypeRequest request) {
        try {
           
            QuestionType questionType = request.getQuestionType();
            if(questionTypeService.updateQuestionType(questionType, questionType.getQuestionTypeId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","questionType updated successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to update questionType"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }

    @PostMapping("/deleteCategory")
    public ResponseEntity<BaseResponse> deleteCategory(@RequestBody DeleteCategoryRequest request) {
        try {
            if(categoryService.deleteCategory(request.getCategoryId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","category deleted successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to delete category"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }
    @PostMapping("/deleteDifficulty")
    public ResponseEntity<BaseResponse> deleteDifficulty(@RequestBody DeleteDifficultyRequest request) {
        try {
            if(difficultyService.deleteDifficulty(request.getDifficultyId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","difficulty deleted successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to delete difficulty"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }
    @PostMapping("/deleteQuestionType")
    public ResponseEntity<BaseResponse> deleteQuestionType(@RequestBody DeleteQuestionTypeRequest request) {
        try {
            if(questionTypeService.deleteQuestionType(request.getQuestionTypeId())>0){
                return ResponseEntity.status(200).body(new BaseResponse(
                    "success","questionType deleted successfully"
                ));
            }
            return ResponseEntity.status(400).body(new BaseResponse(
                "failed","unable to delete questionType"
            ));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new BaseResponse(
               "failed","Internal server error"
            ));
        }

    }
}
