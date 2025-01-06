# Online Exam Application

## Overview
The **Online Exam Application** is a application for conducting online exams, including multiple-choice questions (MCQs) and programming-based questions. This Spring Boot-based application supports role-based access for aDmins, examiners, and students.


## Database Setup
Follow these steps to set up the database:

### Step 1: Create the Database
```sql
CREATE DATABASE online_exam_app_v1;
GO
USE online_exam_app_v1;
GO
```

### Step 2: Insert Master Data
Run the following SQL scripts to populate the master tables:

#### Run following queries
```sql
USE online_exam_app_v1;
GO
SET IDENTITY_INSERT difficulty_master ON;

-- Insert into difficulty_master

INSERT INTO difficulty_master (difficulty_id, difficulty_text, difficulty_weight)
VALUES 
    (1, 'Easy', 1),
    (2, 'Medium', 2),
    (3, 'Hard', 3);
SET IDENTITY_INSERT difficulty_master OFF
-- Insert into question_type_master
SET IDENTITY_INSERT question_type_master ON;
INSERT INTO question_type_master (question_type_id, question_type_text)
VALUES 
    (1, 'MCQ'),
    (2, 'Programming');
SET IDENTITY_INSERT question_type_master OFF
-- Insert into category_master
SET IDENTITY_INSERT category_master ON;
INSERT INTO category_master (category_id, category_text)
VALUES 
    (1, 'Technical'),
    (2, 'Logical'),
    (3, 'Programming');
SET IDENTITY_INSERT category_master OFF
-- Insert into role_master
SET IDENTITY_INSERT role_master ON;
INSERT INTO role_master (role_id, role)
VALUES 
    (1, 'Student'),
    (2, 'Admin'),
    (3, 'Examiner');
SET IDENTITY_INSERT role_master OFF
-- Insert into users
SET IDENTITY_INSERT users ON;
INSERT INTO users (user_id, full_name, mobile_number, email, password, role_id)
VALUES 
    (1, 'Student', '1234567891', 'user@student.com', '$2a$10$9AIPijfeN2kBlqxpJ7xiSehZ7AiLEVG6lL3UsHOyIm5nmzibisD/y', 1),
    (2, 'Admin', '3234567891', 'user@admin.com', '$2a$10$9AIPijfeN2kBlqxpJ7xiSehZ7AiLEVG6lL3UsHOyIm5nmzibisD/y', 2),
    (3, 'Examiner', '2234567891', 'user@examiner.com', '$2a$10$9AIPijfeN2kBlqxpJ7xiSehZ7AiLEVG6lL3UsHOyIm5nmzibisD/y', 3);
SET IDENTITY_INSERT users OFF
-- Insert into questions
SET IDENTITY_INSERT questions ON;
INSERT INTO questions (question_id, question_text, question_type_id, category_id, difficulty_id) 
VALUES 
    (1, 'What is the capital of France?', 1, 1, 1), 
    (2, 'Which planet is known as the Red Planet?', 1, 1, 1), 
    (3, 'Who wrote "Hamlet"?', 1, 2, 2),
    (4, 'What is 2 + 2?', 1, 2, 1), 
    (5, 'Which language is primarily used for Android development?', 1, 3, 3),
    (6, 'What does HTML stand for?', 1, 3, 1),
    (7, 'Which company developed the Python language?', 1, 3, 2), 
    (8, 'What is the boiling point of water in Celsius?', 1, 1, 1), 
    (9, 'What does RAM stand for in computers?', 1, 1, 2), 
    (10, 'Which gas do plants absorb during photosynthesis?', 1, 2, 1),
	(11, 'Write a program to find max of an array', 2, 2, 1);
SET IDENTITY_INSERT questions OFF
-- Insert into question_options
SET IDENTITY_INSERT question_options ON;

INSERT INTO question_options (option_id, option_text, question_id, is_correct) 
VALUES
    (1, 'Paris', 1, 1), 
    (2, 'London', 1, 0),
    (3, 'Berlin', 1, 0),
    (4, 'Rome', 1, 0),
    (5, 'Earth', 2, 0),
    (6, 'Mars', 2, 1), 
    (7, 'Jupiter', 2, 0),
    (8, 'Venus', 2, 0),
    (9, 'Shakespeare', 3, 1), 
    (10, 'Hemingway', 3, 0),
    (11, 'Tolkien', 3, 0),
    (12, 'Austen', 3, 0),
    (13, '4', 4, 1),
    (14, '3', 4, 0),
    (15, '5', 4, 0),
    (16, '6', 4, 0),
    (17, 'Java', 5, 1), 
    (18, 'C++', 5, 0),
    (19, 'Python', 5, 0),
    (20, 'Ruby', 5, 0),
    (21, 'HyperText Markup Language', 6, 1), 
    (22, 'High Transfer Machine Language', 6, 0),
    (23, 'Home Tool Markup Language', 6, 0),
    (24, 'None of the above', 6, 0),
    (25, 'Google', 7, 0),
    (26, 'Microsoft', 7, 0),
    (27, 'Guido van Rossum', 7, 1), 
    (28, 'Apple', 7, 0),
    (29, '0°C', 8, 0),
    (30, '100°C', 8, 1), 
    (31, '50°C', 8, 0),
    (32, '200°C', 8, 0),
    (33, 'Random Access Memory', 9, 1), 
    (34, 'Read Access Memory', 9, 0),
    (35, 'Rapid Access Memory', 9, 0),
    (36, 'None of the above', 9, 0),
    (37, 'Carbon Dioxide', 10, 1), 
    (38, 'Oxygen', 10, 0),
    (39, 'Nitrogen', 10, 0),
    (40, 'Hydrogen', 10, 0);
SET IDENTITY_INSERT question_options OFF
-- Insert into question_example
SET IDENTITY_INSERT question_example ON;

INSERT INTO question_example (example_id, question_id, input_text, output_text) 
VALUES 
    (1, 1, 'Input: [7,10,5]', 'Output: 10');
SET IDENTITY_INSERT question_example OFF
-- Insert into exam_status_master
SET IDENTITY_INSERT exam_status_master ON;

INSERT INTO exam_status_master (exam_status_id, exam_status_text)
VALUES 
    (1, 'InProgress'),
    (2, 'Terminated'),
    (3, 'Completed'),
    (4, 'AutoSubmitted'),
    (5, 'Late');
SET IDENTITY_INSERT exam_status_master OFF
-- Insert into question_submission_status_master
SET IDENTITY_INSERT question_submission_status_master ON;

INSERT INTO question_submission_status_master (question_submission_status_id, question_submission_status_text)
VALUES 
    (1, 'Attempted'),
    (2, 'UnAttepted'),
    (3, 'FlagReview');
SET IDENTITY_INSERT question_submission_status_master OFF
-- Insert into passing_criteria_master
SET IDENTITY_INSERT passing_criteria_master ON;

INSERT INTO passing_criteria_master (passing_criteria_id, passing_criteria_text)
VALUES 
    (1, 'Percentage'),
    (2, 'Percentile'),
    (3, 'Top');
SET IDENTITY_INSERT passing_criteria_master OFF
-- Insert into exam_master
SET IDENTITY_INSERT exam_master ON;

INSERT INTO exam_master (exam_id, exam_code, exam_date, exam_start_time, exam_end_time, exam_duration_in_minutes, passing_criteria_id, passing_value, total_marks)
VALUES
    (1, 'EXAM001', '2024-12-05', '10:00:00', '13:00:00', 120, 1, 80, 100);
SET IDENTITY_INSERT exam_master OFF

-- Insert into exam_questions
SET IDENTITY_INSERT exam_questions ON;
INSERT INTO exam_questions (exam_question_id, exam_id, question_id)
VALUES
    (1, 1, 1),
    (2, 1, 2),
    (3, 1, 3),
    (4, 1, 4),
    (5, 1, 5),
    (6, 1, 6),
    (7, 1, 7),
    (8, 1, 8),
    (9, 1, 9),
    (10, 1, 11);

SET IDENTITY_INSERT exam_questions OFF
```

### Step 3: Update application.properties file (ref. applicationExample.properties)

You can find an example of the `application.properties` file configuration in the [applicationExample.properties](https://github.com/string-TIRTH/online-exam-app-backend/blob/V1/src/main/resources/applicationExample.properties) file.

Replace `https://github.com/string-TIRTH/online-exam-app-backend/blob/V1/src/main/resources/applicationExample.properties` with the actual path or URL to the file in your repository.


## Running the Application
1. Clone the repository:
    ```bash
    git clone https://github.com/string-TIRTH/online-exam-app-backend.git
    ```

2. Navigate to the project directory:
    ```bash
    cd online-exam-app
    ```

3. Build the application:
    ```bash
    mvn clean install
    ```

4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

5. Run React App:
    ```
    http://localhost:3000
    ```

---

## Default Users
| Role       | Email                | Password |
|------------|----------------------|----------|
| Student    | user@student.com     | admin    |
| Admin      | user@admin.com       | admin    |
| Examiner   | user@examiner.com    | admin    |


