/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.DTO.ExamResultDetailDTO;
import com.oea.online_exam_app.DTO.QuestionsDTO;
import com.oea.online_exam_app.Enums.ExamStatusEnum;
import com.oea.online_exam_app.Enums.PassingCriteriaEnum;
import com.oea.online_exam_app.Enums.QuestionSubmissionStatusEnum;
import com.oea.online_exam_app.Enums.QuestionTypeEnum;
import com.oea.online_exam_app.IServices.IExamService;
import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Models.ExamStatus;
import com.oea.online_exam_app.Models.ExamSubmission;
import com.oea.online_exam_app.Models.PassingCriteria;
import com.oea.online_exam_app.Models.ProgrammingSubmission;
import com.oea.online_exam_app.Models.Question;
import com.oea.online_exam_app.Models.QuestionSubmission;
import com.oea.online_exam_app.Models.QuestionSubmissionStatus;
import com.oea.online_exam_app.Models.QuestionType;
import com.oea.online_exam_app.Models.User;
import com.oea.online_exam_app.Repo.ExamQuestionsRepo;
import com.oea.online_exam_app.Repo.ExamRepo;
import com.oea.online_exam_app.Repo.ExamStatusRepo;
import com.oea.online_exam_app.Repo.ExamSubmissionRepo;
import com.oea.online_exam_app.Repo.ProgrammingSubmissionRepo;
import com.oea.online_exam_app.Repo.QuestionSubmissionRepo;
import com.oea.online_exam_app.Repo.QuestionTypeRepo;


/**
 *
 * @author tirth
 */
@Service
public class ExamService implements IExamService {

    @Autowired
    private ExamRepo examRepo;

    @Autowired
    private ExamSubmissionRepo examSubmissionRepo;

    @Autowired
    private QuestionSubmissionRepo questionSubmissionRepo;

    @Autowired
    private ProgrammingSubmissionRepo programmingSubmissionRepo;

    @Autowired
    private com.oea.online_exam_app.Repo.QuestionSubmissionStatusRepo QuestionSubmissionStatusRepo;

    @Autowired
    private ExamQuestionsRepo examQuestionsRepo;

    @Autowired
    private ExamStatusRepo examStatusRepo;


    @Autowired
    private QuestionTypeRepo questionTypeRepo;

    
    @Override
    public int createExam(Exam exam) {
        try {
            examRepo.save(exam);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateExam(Exam exam, int examId) {
        try {
            Exam existingExam = examRepo.findById(examId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid examId"));
            if (existingExam != null) {
                existingExam.setExamCode(exam.getExamCode());
                existingExam.setExamStartTime(exam.getExamStartTime());
                existingExam.setExamEndTime(exam.getExamEndTime());
                existingExam.setExamDurationInMinutes(exam.getExamDurationInMinutes());
                existingExam.setPassingCriteria(exam.getPassingCriteria());
                existingExam.setPassingValue(exam.getPassingValue());
                existingExam.setExamDate(exam.getExamDate());
                examRepo.save(existingExam);
                return existingExam.getExamId();
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int deleteExam(int examId) {
        try {
            if (examRepo.existsById(examId)) {
                examRepo.deleteById(examId);
                return examId;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public Exam getExamById(int examId) {
        try {
            Exam exam = examRepo.findById(examId).orElseThrow(() -> new IllegalArgumentException("Invalid categoryId"));
            return exam;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }

    @Override
    public List<Exam> getAllExams(int page, int limit) {
        try {
            int offset = (page - 1) * limit;
            List<Exam> exams = examRepo.getExamsWithPagination(offset, limit, LocalDate.now());
            return exams;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }

    @Override
    public QuestionsDTO getExamQuestions(Exam exam, User user) {
        try {
            ExamSubmission examSubmissionExists = examSubmissionRepo.findByUserAndExam(user, exam);
            QuestionType mcq =  questionTypeRepo.findByQuestionTypeText(QuestionTypeEnum.MCQ.name()).orElseThrow(() -> new IllegalArgumentException("Invalid question type"));
            QuestionType programming =  questionTypeRepo.findByQuestionTypeText(QuestionTypeEnum.Programming.name()).orElseThrow(() -> new IllegalArgumentException("Invalid question type"));
            List<Question> mcqQuestionsRaw = examQuestionsRepo.findByExamIdAndQuestionTypeId(exam.getExamId(),mcq.getQuestionTypeId());
            List<Question> programmingQuestionsRaw = examQuestionsRepo.findByExamIdAndQuestionTypeId(exam.getExamId(),programming.getQuestionTypeId());
            if (examSubmissionExists != null) {
                List<QuestionSubmission> mcqQuestions = questionSubmissionRepo.findByExamSubmissionAndUser(examSubmissionExists, user); 
                List<ProgrammingSubmission> proQuestions = programmingSubmissionRepo.findByExamSubmissionAndUser(examSubmissionExists, user); 
                List<QuestionsDTO.MCQQuestion> mcqDTOs = mcqQuestions.stream()
                        .map(this::convertToMCQDTO)
                        .toList();

                List<QuestionsDTO.ProgrammingQuestion> programmingDTOs = proQuestions.stream()
                .map(this::convertToProgrammingDTO)
                .toList();

                return new QuestionsDTO(mcqDTOs, programmingDTOs);

            }
            ExamStatus examStatus = examStatusRepo.findByExamStatusText(ExamStatusEnum.InProgress.name());
            QuestionSubmissionStatus questionSubmissionStatus = QuestionSubmissionStatusRepo
                    .findByQuestionSubmissionStatusText(QuestionSubmissionStatusEnum.UnAttepted.name());

            ExamSubmission examSubmission = new ExamSubmission(
                    exam, user, LocalDateTime.now(), LocalDateTime.now().plus(exam.getExamDurationInMinutes(),ChronoUnit.MINUTES), 0, examStatus);
            examSubmissionRepo.save(examSubmission);

            List<QuestionSubmission> questionSubmissionsList = new ArrayList<>();
            List<ProgrammingSubmission> programmingSubmission = new ArrayList<>();
            
            mcqQuestionsRaw.forEach(question -> {
                questionSubmissionsList.add(
                        new QuestionSubmission(
                                question,
                                user,
                                examSubmission,
                                null,
                                questionSubmissionStatus));
            });
            programmingQuestionsRaw.forEach(question -> {
                programmingSubmission.add(
                        new ProgrammingSubmission(
                                examSubmission,
                                user,
                                question,
                                ""));
            }); 
            questionSubmissionRepo.saveAll(questionSubmissionsList);
            programmingSubmissionRepo.saveAll(programmingSubmission);
            List<QuestionsDTO.MCQQuestion> mcqDTOs = questionSubmissionsList.stream()
                        .map(this::convertToMCQDTO)
                        .toList();

            List<QuestionsDTO.ProgrammingQuestion> programmingDTOs = programmingSubmission.stream()
            .map(this::convertToProgrammingDTO)
            .toList();
            return new QuestionsDTO(mcqDTOs, programmingDTOs);
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
    private QuestionsDTO.MCQQuestion convertToMCQDTO(QuestionSubmission question) {
        return new QuestionsDTO.MCQQuestion(
            question.getQuestionSubmissionId(),
            question.getQuestion().getQuestionText(),
            question.getQuestionSubmissionStatus(),
            question.getQuestion().getOptions() != null
                ? question.getQuestion().getOptions().stream()
                    .map(option -> new QuestionsDTO.MCQQuestion.QuestionOption(
                        option.getOptionId(),
                        option.getOptionText(),
                        option.getOptionId() == (question.getSelectedOption() != null ? question.getSelectedOption().getOptionId() : -1)
                    ))
                    .collect(Collectors.toList())
                : new ArrayList<>()
        );
    }


    private QuestionsDTO.ProgrammingQuestion convertToProgrammingDTO(ProgrammingSubmission question) {
        return new QuestionsDTO.ProgrammingQuestion(
                question.getProgrammingSubmissionId(),
                question.getQuestion().getQuestionText(),
                question.getSubmittedCode(),
                question.getQuestion().getExamples().stream()
                        .map(example -> new QuestionsDTO.ProgrammingQuestion.ProgrammingExample(
                                example.getExampleId(),
                                example.getInputText(),
                                example.getOutputText()))
                        .toList());
    }

    @Override
    public int updateSelectedOption(int questionSubmissionId,int optionId, int statusId) {
        try {
            Integer optionIdFinal = optionId==0?null:optionId;
            if(questionSubmissionRepo.updateOptionAndStatus(questionSubmissionId, optionIdFinal, statusId)>0){
                return 1;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int submitCode(int programmingSubmissionId, String code) {
        try {
            System.out.println(programmingSubmissionId);
            System.out.println(code);
            if(questionSubmissionRepo.updateProgrammingCode(programmingSubmissionId, code)>0){
                return 1;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int submitExam(int examSubmissionId) {
        try {
            ExamSubmission examSubmission = examSubmissionRepo.findById(examSubmissionId).orElseThrow(()-> new InvalidAttributesException("Invalid ExamSubmissionId"));
            
            
            int scoredMarks = calculateScoredMarks(examSubmissionId);
            examSubmission.setScoredMarks(scoredMarks);
            if(LocalTime.now().isAfter(examSubmission.getExam().getExamEndTime())){
                examSubmission.setExamStatus(examStatusRepo.findByExamStatusText(ExamStatusEnum.Late.name()));
            }else{
                examSubmission.setExamStatus(examStatusRepo.findByExamStatusText(ExamStatusEnum.Completed.name()));
            }
            // examSubmission.setExamEndTime(LocalDateTime.now());
            examSubmissionRepo.save(examSubmission);
            return 1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public List<ExamResultDetailDTO> getExamResultDetails(int page,int limit,String search) {
        try {
            System.out.println(page);
            System.out.println(limit);
            System.out.println(search);
            List<ExamResultDetailDTO> examResultDetailDTOs = new ArrayList<>();
            List<Exam> exams;
            int offset = (page - 1) * limit;
            if(search.trim().isBlank()){
                exams = examRepo.getExamList(limit,offset);

            }else{
                exams = examRepo.getExamListWithSearch(limit,offset,search);
            }
            for (Exam exam : exams) {
                List<ExamSubmission> examSubmissions = examSubmissionRepo.findByExam(exam);
                int totalMarks = exam.getTotalMarks();
                PassingCriteria passingCriteria = exam.getPassingCriteria();
                int passingValue = exam.getPassingValue();
                int totalStudents=examSubmissions.size();
                int totalQualifiedStudents=0;
                for(ExamSubmission examSub:examSubmissions){
                    if(passingCriteria.getPassingCriteriaText().equals(PassingCriteriaEnum.Percentage.name())){
                        if(((examSub.getScoredMarks()*100)/(totalMarks==0?1:totalMarks))>=passingValue){
                            totalQualifiedStudents++;
                        }
                    }
                }
                if(passingCriteria.getPassingCriteriaText().equals(PassingCriteriaEnum.Top.name())){
                    totalQualifiedStudents=passingValue>examSubmissions.size()?examSubmissions.size():passingValue;
                }
                examResultDetailDTOs.add(new ExamResultDetailDTO(exam.getExamId(),exam.getExamCode(),exam.getExamDate(),totalStudents,totalQualifiedStudents));
                
                
            }
            return examResultDetailDTOs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    

    public int calculateScoredMarks(int examSubmissionId){
        // int totalScoredMarks = 0;

        // For v1 
        int totalMarks = questionSubmissionRepo.getCorrectSubmissions(examSubmissionId);
        return totalMarks;

        // For v2 
        // List<QuestionSubmission> questions = questionSubmissionRepo.getCorrectSubmissions(examSubmissionId);
        // for (QuestionSubmission question : questions) {
        //     totalScoredMarks += question.getQuestion().getDifficulty().getDifficultyWeight(); //For V2 when there will be question as per difficulty 
        // }
        // return totalScoredMarks;

    }
    @Override
    public List<Exam> getExams(int page,int limit,String search) {
        try {
            int offset = (page - 1) * limit;
            List<Exam> exams;
            if(search.trim().isBlank()){
                exams = examRepo.getExamList(limit,offset);

            }else{
                exams = examRepo.getExamListWithSearch(limit,offset,search);
            }
            return exams;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
}