# Online Exam Application (V2)
## [React App](https://github.com/string-TIRTH/online-exam-app/tree/V2)
## Overview
The **Online Exam Application** is a application for conducting online exams, including multiple-choice questions (MCQs) and programming-based questions. This Spring Boot-based application supports role-based access for aDmins, examiners, and students.


## Database Setup
Follow these steps to set up the database:

### Step 1: Create the Database
```sql
CREATE DATABASE online_exam_app_v2;
GO
USE online_exam_app_v2;
GO
```

### Step 2: Insert Master Data
Run the following SQL scripts to populate the master tables:

#### Run following queries
```sql
USE online_exam_app_v2;
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
INSERT INTO questions (question_id, question_text, question_type_id, category_id, difficulty_id,is_image) 
VALUES 
    (1, 'What is the capital of France?', 1, 1, 1,0), 
    (2, 'Which planet is known as the Red Planet?', 1, 1, 1,0), 
    (3, 'Who wrote "Hamlet"?', 1, 2, 2,0),
    (4, 'What is 2 + 2?', 1, 2, 1,0), 
    (5, 'Which language is primarily used for Android development?', 1, 3, 3,0),
    (6, 'What does HTML stand for?', 1, 3, 1,0),
    (7, 'Which company developed the Python language?', 1, 3, 2,0), 
    (8, 'What is the boiling point of water in Celsius?', 1, 1, 1,0), 
    (9, 'What does RAM stand for in computers?', 1, 1, 2,0), 
    (10, 'Which gas do plants absorb during photosynthesis?', 1, 2, 1,0),
	(11, 'Write a program to find max of an array', 2, 2, 1,0),
	(12, 'Write a program to reverse an array', 2, 2, 1, 0),
	(13, 'Write a program to find the sum of all elements in an array', 2, 2, 1, 0),
	(14, 'Write a program to check if a number is even or odd', 2, 2, 1, 0),
	(15, 'Write a program to print Fibonacci series up to N terms', 2, 2, 1, 0),
	(16, 'Write a program to find the factorial of a number', 2, 2, 1, 0),
	(17, 'Write a program to find the largest among three numbers', 2, 2, 1, 0),
	(18, 'Write a program to count the number of vowels in a string', 2, 2, 1, 0),
	(19, 'Write a program to find the length of a string', 2, 2, 1, 0),
	(20, 'Write a program to check if a number is prime', 2, 2, 1, 0),
	(21, 'Write a program to find the minimum of an array', 2, 2, 1, 0),
	(22, 'Write a program to find the second largest number in an array', 2, 2, 2, 0),
	(23, 'Write a program to check if a string is a palindrome', 2, 2, 2, 0),
	(24, 'Write a program to implement binary search in an array', 2, 2, 2, 0),
	(25, 'Write a program to count the frequency of elements in an array', 2, 2, 2, 0),
	(26, 'Write a program to remove duplicates from an array', 2, 2, 2, 0),
	(27, 'Write a program to implement linear search', 2, 2, 2, 0),
	(28, 'Write a program to rotate an array to the left by D positions', 2, 2, 2, 0),
	(29, 'Write a program to find the GCD of two numbers', 2, 2, 2, 0),
	(30, 'Write a program to sort an array using Bubble Sort', 2, 2, 2, 0),
	(31, 'Write a program to find all prime numbers up to N', 2, 2, 2, 0),
	(32, 'Write a program to solve the N-Queens problem', 2, 2, 3, 0),
	(33, 'Write a program to find the longest increasing subsequence in an array', 2, 2, 3, 0),
	(34, 'Write a program to find the maximum subarray sum (Kadanes Algorithm)', 2, 2, 3, 0),
	(35, 'Write a program to implement Merge Sort', 2, 2, 3, 0),
	(36, 'Write a program to check if a string is a valid anagram of another', 2, 2, 3, 0),
	(37, 'Write a program to implement Quick Sort', 2, 2, 3, 0),
	(38, 'Write a program to find the smallest window in a string containing all characters of another string', 2, 2, 3, 0),
	(39, 'Write a program to detect a cycle in a linked list', 2, 2, 3, 0),
	(40, 'Write a program to solve the Tower of Hanoi problem', 2, 2, 3, 0),
	(41, 'Write a program to calculate power using fast exponentiation', 2, 2, 3, 0),
	(42, 'What is the capital of Australia?', 1, 1, 2, 0),
	(43, 'Which organ is responsible for pumping blood?', 1, 1, 1, 0),
	(44, 'What is the value of pi (π) up to 2 decimal places?', 1, 2, 1, 0),
	(45, 'Who discovered penicillin?', 1, 2, 2, 0),
	(46, 'Which gas is most abundant in the Earths atmosphere?', 1, 1, 2, 0),
	(47, 'What is the square of 12?', 1, 2, 1, 0),
	(48, 'Which company developed JavaScript?', 1, 3, 2, 0),
	(49, 'What is the full form of HTTP?', 1, 3, 1, 0),
	(50, 'Which planet has the most moons?', 1, 1, 3, 0),
	(51, 'Which country hosted the 2020 Summer Olympics?', 1, 2, 2, 0),

	-- 52–61
	(52, 'Who wrote the "Iliad" and the "Odyssey"?', 1, 2, 2, 0),
	(53, 'Which part of a plant conducts photosynthesis?', 1, 1, 1, 0),
	(54, 'What is the cube root of 27?', 1, 2, 1, 0),
	(55, 'What is the name of the longest river in the world?', 1, 1, 2, 0),
	(56, 'Which programming language is used for AI development?', 1, 3, 2, 0),
	(57, 'What does IP stand for in networking?', 1, 3, 1, 0),
	(58, 'Which organ controls the human nervous system?', 1, 1, 1, 0),
	(59, 'What is the boiling point of water in Fahrenheit?', 1, 1, 1, 0),
	(60, 'Who was the first person to walk on the moon?', 1, 2, 1, 0),
	(61, 'What does the acronym "URL" stand for?', 1, 3, 1, 0),

	-- 62–71
	(62, 'What is the largest bone in the human body?', 1, 1, 2, 0),
	(63, 'What is the binary of decimal 10?', 1, 3, 2, 0),
	(64, 'What is the result of 8 mod 3?', 1, 2, 1, 0),
	(65, 'Who painted the ceiling of the Sistine Chapel?', 1, 2, 2, 0),
	(66, 'Which data structure uses FIFO?', 1, 3, 2, 0),
	(67, 'How many bytes are in a kilobyte (KB)?', 1, 3, 1, 0),
	(68, 'Which vitamin is produced when skin is exposed to sunlight?', 1, 1, 1, 0),
	(69, 'Which country has the largest population?', 1, 2, 2, 0),
	(70, 'What is the smallest prime number?', 1, 2, 1, 0),
	(71, 'What does CSS stand for?', 1, 3, 1, 0);
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
    (40, 'Hydrogen', 10, 0),
	(41, 'Canberra', 42, 1),
	(42, 'Sydney', 42, 0),
	(43, 'Melbourne', 42, 0),
	(44, 'Brisbane', 42, 0),

	(45, 'Heart', 43, 1),
	(46, 'Lungs', 43, 0),
	(47, 'Kidney', 43, 0),
	(48, 'Liver', 43, 0),

	(49, '3.14', 44, 1),
	(50, '3.15', 44, 0),
	(51, '3.13', 44, 0),
	(52, '3.16', 44, 0),

	(53, 'Alexander Fleming', 45, 1),
	(54, 'Marie Curie', 45, 0),
	(55, 'Isaac Newton', 45, 0),
	(56, 'Albert Einstein', 45, 0),

	(57, 'Nitrogen', 46, 1),
	(58, 'Oxygen', 46, 0),
	(59, 'Carbon Dioxide', 46, 0),
	(60, 'Hydrogen', 46, 0),

	(61, '144', 47, 1),
	(62, '124', 47, 0),
	(63, '132', 47, 0),
	(64, '154', 47, 0),

	(65, 'Netscape', 48, 1),
	(66, 'Microsoft', 48, 0),
	(67, 'Sun Microsystems', 48, 0),
	(68, 'Google', 48, 0),

	(69, 'HyperText Transfer Protocol', 49, 1),
	(70, 'HighText Transfer Protocol', 49, 0),
	(71, 'HyperText Translate Protocol', 49, 0),
	(72, 'Hyper Tool Transfer Protocol', 49, 0),

	(73, 'Saturn', 50, 1),
	(74, 'Jupiter', 50, 0),
	(75, 'Neptune', 50, 0),
	(76, 'Mars', 50, 0),

	(77, 'Japan', 51, 1),
	(78, 'China', 51, 0),
	(79, 'Brazil', 51, 0),
	(80, 'UK', 51, 0),

	(81, 'Homer', 52, 1),
	(82, 'Socrates', 52, 0),
	(83, 'Plato', 52, 0),
	(84, 'Aristotle', 52, 0),

	(85, 'Leaf', 53, 1),
	(86, 'Stem', 53, 0),
	(87, 'Root', 53, 0),
	(88, 'Flower', 53, 0),

	(89, '3', 54, 1),
	(90, '9', 54, 0),
	(91, '27', 54, 0),
	(92, '81', 54, 0),

	(93, 'Nile', 55, 1),
	(94, 'Amazon', 55, 0),
	(95, 'Yangtze', 55, 0),
	(96, 'Mississippi', 55, 0),

	(97, 'Python', 56, 1),
	(98, 'Java', 56, 0),
	(99, 'C++', 56, 0),
	(100, 'HTML', 56, 0),

	(101, 'Internet Protocol', 57, 1),
	(102, 'Internal Protocol', 57, 0),
	(103, 'Input Protocol', 57, 0),
	(104, 'Integrated Protocol', 57, 0),

	(105, 'Brain', 58, 1),
	(106, 'Spinal cord', 58, 0),
	(107, 'Heart', 58, 0),
	(108, 'Liver', 58, 0),

	(109, '212°F', 59, 1),
	(110, '100°F', 59, 0),
	(111, '180°F', 59, 0),
	(112, '98.6°F', 59, 0),

	(113, 'Neil Armstrong', 60, 1),
	(114, 'Buzz Aldrin', 60, 0),
	(115, 'Yuri Gagarin', 60, 0),
	(116, 'Michael Collins', 60, 0),

	(117, 'Uniform Resource Locator', 61, 1),
	(118, 'Universal Resource Link', 61, 0),
	(119, 'United Resource Locator', 61, 0),
	(120, 'Uniform Reference Link', 61, 0),

	(121, 'Femur', 62, 1),
	(122, 'Humerus', 62, 0),
	(123, 'Tibia', 62, 0),
	(124, 'Pelvis', 62, 0),

	(125, '1010', 63, 1),
	(126, '1100', 63, 0),
	(127, '1001', 63, 0),
	(128, '1110', 63, 0),

	(129, '2', 64, 1),
	(130, '1', 64, 0),
	(131, '3', 64, 0),
	(132, '0', 64, 0),

	(133, 'Michelangelo', 65, 1),
	(134, 'Leonardo da Vinci', 65, 0),
	(135, 'Raphael', 65, 0),
	(136, 'Donatello', 65, 0),

	(137, 'Queue', 66, 1),
	(138, 'Stack', 66, 0),
	(139, 'Tree', 66, 0),
	(140, 'Graph', 66, 0),

	(141, '1024', 67, 1),
	(142, '1000', 67, 0),
	(143, '512', 67, 0),
	(144, '2048', 67, 0),

	(145, 'Vitamin D', 68, 1),
	(146, 'Vitamin C', 68, 0),
	(147, 'Vitamin A', 68, 0),
	(148, 'Vitamin B12', 68, 0),

	(149, 'India', 69, 1),
	(150, 'China', 69, 0),
	(151, 'USA', 69, 0),
	(152, 'Indonesia', 69, 0),

	(153, '2', 70, 1),
	(154, '1', 70, 0),
	(155, '3', 70, 0),
	(156, '0', 70, 0),

	(157, 'Cascading Style Sheets', 71, 1),
	(158, 'Creative Style Syntax', 71, 0),
	(159, 'Computer Style Sheet', 71, 0),
	(160, 'Colorful Style Sheet', 71, 0);

SET IDENTITY_INSERT question_options OFF
-- Insert into question_example
SET IDENTITY_INSERT question_example ON;

INSERT INTO question_example (example_id, question_id, input_text, output_text) 
VALUES 
    (1, 11, 'Input: [7,10,5]', 'Output: 10'),
	(2, 12, 'Input: [1, 2, 3, 4]', 'Output: [4, 3, 2, 1]'),
	(3, 13, 'Input: [5, 10, 15]', 'Output: 30'),
	(4, 14, 'Input: 6', 'Output: Even'),
	(5, 15, 'Input: 5', 'Output: 0 1 1 2 3'),
	(6, 16, 'Input: 5', 'Output: 120'),
	(7, 17, 'Input: 10, 20, 5', 'Output: 20'),
	(8, 18, 'Input: "hello world"', 'Output: 3'),
	(9, 19, 'Input: "ChatGPT"', 'Output: 7'),
	(10, 20, 'Input: 17', 'Output: Prime'),
	(11, 21, 'Input: [9, 5, 8, 2]', 'Output: 2'),

	-- Medium Examples (ID: 22 to 31)
	(12, 22, 'Input: [3, 6, 9, 1]', 'Output: 6'),
	(13, 23, 'Input: "racecar"', 'Output: Palindrome'),
	(14, 24, 'Input: [1,3,5,7,9], Target: 5', 'Output: Index 2'),
	(15, 25, 'Input: [1,2,2,3,3,3]', 'Output: {1:1, 2:2, 3:3}'),
	(16, 26, 'Input: [1,2,2,3,3]', 'Output: [1,2,3]'),
	(17, 27, 'Input: [5,8,1,9], Target: 1', 'Output: Index 2'),
	(18, 28, 'Input: [1,2,3,4,5], D:2', 'Output: [3,4,5,1,2]'),
	(19, 29, 'Input: 12, 15', 'Output: 3'),
	(20, 30, 'Input: [4, 3, 2, 1]', 'Output: [1, 2, 3, 4]'),
	(21, 31, 'Input: N = 10', 'Output: [2, 3, 5, 7]'),

	-- Hard Examples (ID: 32 to 41)
	(22, 32, 'Input: N = 4', 'Output: All valid 4x4 queen placements'),
	(23, 33, 'Input: [10, 9, 2, 5, 3, 7, 101, 18]', 'Output: 4'),
	(24, 34, 'Input: [-2,1,-3,4,-1,2,1,-5,4]', 'Output: 6'),
	(25, 35, 'Input: [4,3,2,1]', 'Output: [1,2,3,4]'),
	(26, 36, 'Input: "listen", "silent"', 'Output: True'),
	(27, 37, 'Input: [5,3,8,4,2]', 'Output: [2,3,4,5,8]'),
	(28, 38, 'Input: String: "ADOBECODEBANC", Pattern: "ABC"', 'Output: "BANC"'),
	(29, 39, 'Input: LinkedList: 1->2->3->4->2 (cycle)', 'Output: True'),
	(30, 40, 'Input: N = 3', 'Output: Series of moves from A to C using B'),
	(31, 41, 'Input: base = 2, exponent = 10', 'Output: 1024');
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

You can find an example of the `application.properties` file configuration in the [applicationExample.properties](https://github.com/string-TIRTH/online-exam-app-backend/blob/V2/src/main/resources/applicationExample.properties) file.

Replace `https://github.com/string-TIRTH/online-exam-app-backend/blob/V2/src/main/resources/applicationExample.properties` with the actual path or URL to the file in your repository.


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



