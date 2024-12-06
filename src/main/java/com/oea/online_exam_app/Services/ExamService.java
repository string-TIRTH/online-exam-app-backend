/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oea.online_exam_app.DTO.QuestionsDTO;
import com.oea.online_exam_app.Enums.ExamStatusEnum;
import com.oea.online_exam_app.Enums.QuestionSubmissionStatusEnum;
import com.oea.online_exam_app.Enums.QuestionTypeEnum;
import com.oea.online_exam_app.IServices.IExamService;
import com.oea.online_exam_app.Models.Exam;
import com.oea.online_exam_app.Models.ExamStatus;
import com.oea.online_exam_app.Models.ExamSubmission;
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
import com.oea.online_exam_app.Repo.UserRepo;

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
    private UserRepo userRepo;

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
                existingExam.setExamStartTime(exam.getExamStartTime());
                existingExam.setExamEndTime(exam.getExamEndTime());
                existingExam.setExamDurationInMinutes(exam.getExamDurationInMinutes());
                existingExam.setPassingCriteria(exam.getPassingCriteria());
                existingExam.setPassingValue(exam.getPassingValue());
                existingExam.setExamDate(exam.getExamDate());
                existingExam.setTotalMarks(exam.getTotalMarks());
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
    public QuestionsDTO getExamQuestions(int examId, int userId) {
        try {
            User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
            Exam exam = examRepo.findById(examId).orElseThrow(() -> new IllegalArgumentException("Invalid examId"));
            ExamSubmission examSubmissionExists = examSubmissionRepo.findByUserAndExam(user, exam);
            QuestionType mcq =  questionTypeRepo.findByQuestionTypeText(QuestionTypeEnum.MCQ.name()).orElseThrow(() -> new IllegalArgumentException("Invalid question type"));
            System.out.println(mcq);
            QuestionType programming =  questionTypeRepo.findByQuestionTypeText(QuestionTypeEnum.Programming.name()).orElseThrow(() -> new IllegalArgumentException("Invalid question type"));
            List<Question> mcqQuestionsRaw = examQuestionsRepo.findByExamIdAndQuestionTypeId(examId,mcq.getQuestionTypeId());
            List<Question> programmingQuestionsRaw = examQuestionsRepo.findByExamIdAndQuestionTypeId(examId,programming.getQuestionTypeId());
            if (examSubmissionExists != null) {
                List<QuestionsDTO.MCQQuestion> mcqDTOs = mcqQuestionsRaw.stream()
                        .map(this::convertToMCQDTO)
                        .toList();

                List<QuestionsDTO.ProgrammingQuestion> programmingDTOs = programmingQuestionsRaw.stream()
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
            List<QuestionsDTO.MCQQuestion> mcqDTOs = mcqQuestionsRaw.stream()
                    .map(this::convertToMCQDTO)
                    .toList();

            List<QuestionsDTO.ProgrammingQuestion> programmingDTOs = programmingQuestionsRaw.stream()
                    .map(this::convertToProgrammingDTO)
                    .toList();
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
            System.out.println(mcqDTOs);
            questionSubmissionRepo.saveAll(questionSubmissionsList);
            programmingSubmissionRepo.saveAll(programmingSubmission);
            return new QuestionsDTO(mcqDTOs, programmingDTOs);
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }

    private QuestionsDTO.MCQQuestion convertToMCQDTO(Question question) {
        return new QuestionsDTO.MCQQuestion(
                question.getQuestionId(),
                question.getQuestionText(),
                question.getOptions().stream()
                        .map(option -> new QuestionsDTO.MCQQuestion.QuestionOption(option.getOptionId(),
                                option.getOptionText()))
                        .toList());
    }

    private QuestionsDTO.ProgrammingQuestion convertToProgrammingDTO(Question question) {
        return new QuestionsDTO.ProgrammingQuestion(
                question.getQuestionId(),
                question.getQuestionText(),
                question.getExamples().stream()
                        .map(example -> new QuestionsDTO.ProgrammingQuestion.ProgrammingExample(
                                example.getExampleId(),
                                example.getInputText(),
                                example.getOutputText()))
                        .toList());
    }

    @Override
    public int updateSelectedOption(int userId,int examSubmissionId,int questionId,  int optionId, int statusId) {
        try {
            Integer optionIdFinal = optionId==0?null:optionId;
            if(questionSubmissionRepo.updateOptionAndStatus(userId, examSubmissionId, questionId, optionIdFinal, statusId)>0){
                return 1;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int submitCode(int userId,int examSubmissionId,int questionId, String code) {
        try {
            questionSubmissionRepo.updateProgrammingCode(userId, examSubmissionId, questionId, code);
            if(questionSubmissionRepo.updateProgrammingCode(userId, examSubmissionId, questionId, code)>0){
                return 1;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    @Override
    public int submitExam(int userId,int examSubmissionId) {
        try {
            ExamSubmission examSubmission = examSubmissionRepo.findById(examSubmissionId).orElseThrow(()-> new InvalidAttributesException("Invalid ExamSubmissionId"));
            int scoredMarks = calculateScoredMarks(examSubmissionId);
            System.out.println(scoredMarks);
            examSubmission.setScoredMarks(scoredMarks);
            if(LocalDateTime.now().isAfter(examSubmission.getExam().getExamEndTime())){
                examSubmission.setExamStatus(examStatusRepo.findByExamStatusText(ExamStatusEnum.Late.name()));
            }else{
                examSubmission.setExamStatus(examStatusRepo.findByExamStatusText(ExamStatusEnum.Completed.name()));
            }
            examSubmission.setExamEndTime(LocalDateTime.now());
            examSubmissionRepo.save(examSubmission);
            return 1;
        } catch (Exception e) {
            System.out.println(e.getCause());
            return 0;
        }
    }

    public int calculateScoredMarks(int examSubmissionId){
        int totalScoredMarks = 0;
        List<QuestionSubmission> questions = questionSubmissionRepo.getCorrectSubmissions(examSubmissionId);
        for (QuestionSubmission question : questions) {
            totalScoredMarks += question.getQuestion().getDifficulty().getDifficultyWeight();
        }
        return totalScoredMarks;
    }
}