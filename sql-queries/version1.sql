-- Create the database
CREATE DATABASE online_exam_app;
GO

USE online_exam_app;
GO

-- Create the role_master table
CREATE TABLE role_master (
    role_id INT PRIMARY KEY IDENTITY(1,1),
    role NVARCHAR(50) NOT NULL
);

-- Create the users table
CREATE TABLE [users] (
    user_id INT PRIMARY KEY IDENTITY(1,1),
    full_name NVARCHAR(100) NOT NULL,
    mobile_number NVARCHAR(15) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (role_id) REFERENCES role_master(role_id)
);

-- Create the exam_status_master table
CREATE TABLE exam_status_master (
    exam_status_id INT PRIMARY KEY IDENTITY(1,1),
    exam_status_txt NVARCHAR(50) NOT NULL
);

-- Create the question_submission_status_master table
CREATE TABLE question_submission_status_master (
    question_submission_status_id INT PRIMARY KEY IDENTITY(1,1),
    question_submission_status_text NVARCHAR(50) NOT NULL
);

-- Create the exam_master table
CREATE TABLE exam_master (
    exam_id INT PRIMARY KEY IDENTITY(1,1),
    exam_code NVARCHAR(50) NOT NULL UNIQUE,
    exam_date DATE NOT NULL,
    exam_start_time TIME NOT NULL,
    exam_end_time TIME NOT NULL,
    exam_duration_in_minutes INT NOT NULL,
    passing_criteria FLOAT NOT NULL,
    passing_value FLOAT NOT NULL
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
    exam_status_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES [users](user_id),
    FOREIGN KEY (exam_id) REFERENCES exam_master(exam_id),
    FOREIGN KEY (exam_status_id) REFERENCES exam_status_master(exam_status_id)
);

-- Create the question_type_master table
CREATE TABLE question_type_master (
    question_type_id INT PRIMARY KEY IDENTITY(1,1),
    question_type_text NVARCHAR(50) NOT NULL
);

-- Create the category_master table
CREATE TABLE category_master (
    category_id INT PRIMARY KEY IDENTITY(1,1),
    category_text NVARCHAR(100) NOT NULL
);

-- Create the difficulty_master table
CREATE TABLE difficulty_master (
    difficulty_id INT PRIMARY KEY IDENTITY(1,1),
    difficulty_txt NVARCHAR(50) NOT NULL,
    difficulty_weight FLOAT NOT NULL
);

-- Create the questions table
CREATE TABLE questions (
    question_id INT PRIMARY KEY IDENTITY(1,1),
    question_txt NVARCHAR(MAX) NOT NULL,
    category_id INT NOT NULL,
    question_type_id INT NOT NULL,
    difficulty_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category_master(category_id),
    FOREIGN KEY (question_type_id) REFERENCES question_type_master(question_type_id),
    FOREIGN KEY (difficulty_id) REFERENCES difficulty_master(difficulty_id)
);



-- Create the exam_questions table
CREATE TABLE exam_questions (
	exam_question_id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    exam_id INT NOT NULL,
    question_id INT NOT NULL,
    FOREIGN KEY (exam_id) REFERENCES exam_master(exam_id),
    FOREIGN KEY (question_id) REFERENCES questions(question_id)
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

-- Create the question_submission table
CREATE TABLE question_submission (
    selected_option_id INT,
    question_id INT NOT NULL,
    user_id INT NOT NULL,
    exam_submission_id INT NOT NULL,
	question_submission_status_id INT NOT NULL,
    PRIMARY KEY (exam_submission_id, question_id, user_id),
    FOREIGN KEY (selected_option_id) REFERENCES question_options(option_id),
    FOREIGN KEY (question_id) REFERENCES questions(question_id),
	FOREIGN KEY (question_submission_status_id) REFERENCES question_submission_status_master(question_submission_status_id),
    FOREIGN KEY (user_id) REFERENCES [users](user_id),
    FOREIGN KEY (exam_submission_id) REFERENCES exam_submission(exam_submission_id)
);
GO
