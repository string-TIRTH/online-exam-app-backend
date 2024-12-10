CREATE DATABASE online_exam_app;
GO

USE online_exam_app;
GO

-- Create the role_master table
CREATE TABLE role_master (
    role_id TINYINT PRIMARY KEY IDENTITY(1,1),
    role NVARCHAR(50) NOT NULL
);

-- Create the users table
CREATE TABLE [users] (
    user_id INT PRIMARY KEY IDENTITY(1,1),
    full_name NVARCHAR(100) NOT NULL,
    mobile_number NVARCHAR(15) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    role_id TINYINT NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (role_id) REFERENCES role_master(role_id)
);


-- Create the question_type_master table
CREATE TABLE question_type_master (
    question_type_id TINYINT PRIMARY KEY IDENTITY(1,1),
    question_type_text NVARCHAR(50) NOT NULL
);

-- Create the category_master table
CREATE TABLE category_master (
    category_id TINYINT PRIMARY KEY IDENTITY(1,1),
    category_text NVARCHAR(100) NOT NULL
);

-- Create the difficulty_master table
CREATE TABLE difficulty_master (
    difficulty_id TINYINT PRIMARY KEY IDENTITY(1,1),
    difficulty_text NVARCHAR(50) NOT NULL,
    difficulty_weight FLOAT NOT NULL
);



-- Create the questions table
CREATE TABLE questions (
    question_id INT PRIMARY KEY IDENTITY(1,1),
    question_text NVARCHAR(MAX) NOT NULL,
    category_id TINYINT NOT NULL,
    question_type_id TINYINT NOT NULL,
    difficulty_id TINYINT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category_master(category_id),
    FOREIGN KEY (question_type_id) REFERENCES question_type_master(question_type_id),
    FOREIGN KEY (difficulty_id) REFERENCES difficulty_master(difficulty_id)
);

-- Create the question_options table
CREATE TABLE question_options (
    option_id INT PRIMARY KEY IDENTITY(1,1),
    question_id INT NOT NULL,
    option_text NVARCHAR(MAX) NOT NULL,
    is_correct BIT NOT NULL,
    FOREIGN KEY (question_id) REFERENCES questions(question_id)
);

-- Create the question_example table
CREATE TABLE question_example (
    example_id INT PRIMARY KEY IDENTITY(1,1),
    question_id INT NOT NULL,
    input_text NVARCHAR(MAX) NOT NULL,
    output_text NVARCHAR(MAX) NOT NULL,
    FOREIGN KEY (question_id) REFERENCES questions(question_id)
);

-- Create the passing_critaria_master table
CREATE TABLE passing_criteria_master (
    passing_criteria_id TINYINT PRIMARY KEY IDENTITY(1,1),
    passing_criteria_text NVARCHAR(50) NOT NULL
);
-- Create the exam_master table
CREATE TABLE exam_master (
    exam_id INT PRIMARY KEY IDENTITY(1,1),
    exam_code NVARCHAR(50) NOT NULL UNIQUE,
    exam_date DATE NOT NULL,
    exam_start_time TIME NOT NULL,
    exam_end_time TIME NOT NULL,
    exam_duration_in_minutes INT NOT NULL,
	total_marks SMALLINT NOT NULL,
    passing_criteria_id TINYINT NOT NULL,
    passing_value FLOAT NOT NULL,
	FOREIGN KEY (passing_criteria_id) REFERENCES passing_criteria_master(passing_criteria_id)
);

-- Create the exam_questions table
CREATE TABLE exam_questions (
    exam_id INT NOT NULL,
    question_id INT NOT NULL,
    PRIMARY KEY (exam_id, question_id),
    FOREIGN KEY (exam_id) REFERENCES exam_master(exam_id),
    FOREIGN KEY (question_id) REFERENCES questions(question_id)
);


-- Create the exam_status_master table
CREATE TABLE exam_status_master (
    exam_status_id TINYINT PRIMARY KEY IDENTITY(1,1),
    exam_status_text NVARCHAR(50) NOT NULL
);

-- Create the exam_submission table
CREATE TABLE exam_submission (
    exam_submission_id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT NOT NULL,
    exam_id INT NOT NULL,
    exam_start_time DATETIME NOT NULL,
    exam_end_time DATETIME NOT NULL,
    total_marks FLOAT NOT NULL,
    scored_marks FLOAT NOT NULL,
    exam_status_id TINYINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES [users](user_id),
    FOREIGN KEY (exam_id) REFERENCES exam_master(exam_id),
    FOREIGN KEY (exam_status_id) REFERENCES exam_status_master(exam_status_id)
);

-- Create the programming_submission table
CREATE TABLE programming_submission (
    exam_submission_id INT NOT NULL,
    user_id INT NOT NULL,
    question_id INT NOT NULL,
    submitted_code NVARCHAR(MAX) NOT NULL,
    PRIMARY KEY (exam_submission_id, question_id),
    FOREIGN KEY (exam_submission_id) REFERENCES exam_submission(exam_submission_id),
    FOREIGN KEY (user_id) REFERENCES [users](user_id),
    FOREIGN KEY (question_id) REFERENCES questions(question_id)
);
	
-- Create the question_submission_status_master table
CREATE TABLE question_submission_status_master (
    question_submission_status_id TINYINT PRIMARY KEY IDENTITY(1,1),
    question_submission_status_text NVARCHAR(50) NOT NULL
);
-- Create the question_submission table
CREATE TABLE question_submission (
	question_submission_id INT PRIMARY KEY IDENTITY(1,1),
    selected_option_id INT,
    question_id INT NOT NULL,
    user_id INT NOT NULL,
    exam_submission_id INT NOT NULL,
	question_submission_status_id TINYINT NOT NULL,
    FOREIGN KEY (selected_option_id) REFERENCES question_options(option_id),
    FOREIGN KEY (question_id) REFERENCES questions(question_id),
    FOREIGN KEY (user_id) REFERENCES [users](user_id),
	FOREIGN KEY (question_submission_status_id) REFERENCES question_submission_status_master(question_submission_status_id),
    FOREIGN KEY (exam_submission_id) REFERENCES exam_submission(exam_submission_id)
);


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
    (1, 'Tirth 1', '1234567891', 'tirth@gmail.com', 'pwsd', 1),
    (2, 'Tirth 2', '3234567891', 'tirth1@gmail.com', 'pwsd', 1),
    (3, 'Tirth 3', '2234567891', 'tirth2@gmail.com', 'pwsd', 1);
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
-- Insert into exam_submission
SET IDENTITY_INSERT exam_submission ON;

INSERT INTO exam_submission (exam_submission_id, user_id, exam_id, exam_start_time, exam_end_time, scored_marks, exam_status_id) 
VALUES 
    (1, 1, 1, '2024-12-04 10:00:00', '2024-12-04 13:00:00', 0, 1);
SET IDENTITY_INSERT exam_submission OFF
-- Insert into question_submission
SET IDENTITY_INSERT question_submission ON;

INSERT INTO question_submission (question_submission_id, selected_option_id, question_id, user_id, exam_submission_id, question_submission_status_id)
VALUES
    (1,1,1,1, 1, 1), 
	(2, 5,2,1, 1, 1), 
	(3, 9,  3,1, 1, 1), 
	(4, 13, 4, 1, 1, 2), 
	(5, 17, 5, 1, 1, 1), 
	(6, 21, 6, 1, 1, 1), 
	(7, 25, 7, 1, 1, 1), 
	(8, 29, 8, 1, 1, 2), 
	(9, 22, 9, 1, 1, 1), 
	(10,  21,10, 1, 1, 1);
SET IDENTITY_INSERT question_submission OFF
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