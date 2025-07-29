package onlineTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* Purpose:
 * 	 This class implements the Manager interface and serves as the central data manager for the online test system.
 *   It coordinates the creation and management of exams, questions (True/False, Multiple Choice, Fill-in-the-Blanks),
 *   student records, and exam submissions. It provides methods to add exams and questions, register students,
 *   record student answers, compute exam scores, generate grading reports, and calculate overall course grade
 *   
 *   @Author Marco Sinobad 
 */

public class SystemManager implements Manager, Serializable {
    private Database database;
    private String[] letterGrades;
    private double[] cutoffs;
    
    public SystemManager() {
        // Initializes the database with empty exam and student lists.
        database = new Database(new ArrayList<Exam>(), new ArrayList<Student>());
    }

    @Override
    public boolean addExam(int examId, String title) {
    	//Check for null title or empty title 
        if(title == null || title.trim().isEmpty())  
            throw new IllegalArgumentException("Invalid title");
        //Check if exam is already in database
        if(this.database.findExam(examId) != null) return false;
        
        //Create new exam using parameters and add to database
        Exam newExam = new Exam(examId, title);
        this.database.getExamList().add(newExam);
        // Add this exam to every student's exam list.
        for(Student s : this.database.getStudentList()){
            s.addExam(newExam);
        }
        //Return true if exam is added
        return true;
    }

    @Override
    public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
    	//Check if exam exists in database after finding it
        Exam exam = this.database.findExam(examId);
        if (exam == null) { 
            System.out.println("Exam not found.");
            return;
        }
        //Create new true/false question using parameters
        TFquestion trueFalse = new TFquestion(examId, questionNumber, text, points, answer);
        // Adjusting index
        int index = questionNumber - 1;
        // If a question already exists at that index, overwrite it using set; otherwise add.
        if(index < exam.getQuestions().size()) {
            exam.getQuestions().set(index, trueFalse);
        } else {
            exam.getQuestions().add(trueFalse);
        }
    }

    @Override
    public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
    	//Check if exam exists in database after finding it
        Exam exam = this.database.findExam(examId);
        if (exam == null) {
            System.out.println("Exam not found.");
            return;
        }
        //Create new multiple choice question using parameters
        MCquestion multipleChoice = new MCquestion(examId, questionNumber, text, points, answer);
        //Adjust index
        int index = questionNumber - 1;
        //Check if question already exists, if so overwrite 
        if(index < exam.getQuestions().size()) {
            exam.getQuestions().set(index, multipleChoice);
        } else {
            exam.getQuestions().add(multipleChoice);
        }
    }

    @Override    
    public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
        Exam exam = this.database.findExam(examId);
        if (exam == null) {
            System.out.println("Exam not found.");
            return;
        }
        FIBquestion fillInBlankQuestion = new FIBquestion(examId, questionNumber, text, points, answer);
        //Adjust for index
        int index = questionNumber - 1;
        //Check if exam exists same as first two addX methods.
        if(index < exam.getQuestions().size()) {
            exam.getQuestions().set(index, fillInBlankQuestion);
        } else {
            exam.getQuestions().add(fillInBlankQuestion);
        }
    }

    @Override
    public String getKey(int examId) {
    	//Check if exam exists
        Exam exam = this.database.findExam(examId);
        if(exam == null) return "Exam not found";
        //Initialize key string
        String key = "";
        //Outer loop iterates through the questions
        for(int i = 0; i < exam.getQuestions().size(); i++) {    
        	//Create question variable to make code cleaner
            Question temp = exam.getQuestions().get(i);
            //Add information common to all questions first
            key += "Question Text: " + temp.getText() + "\n"  
                   + "Points: " + temp.getPoints() + "\n" 
                   + "Correct Answer: ";
            //Check for what type of question is being examined to add appropriate answer
            if(temp instanceof MCquestion) {
            	//Answers come bracketed
                key += "[";
                //Temporary answer variable for cleaner code
                String[] tempAnswers = ((MCquestion) temp).getAnswer();
                //Loop through all the answers adding them in order separated by comma to the key
                for(int j = 0; j < tempAnswers.length; j++) {
                    key += tempAnswers[j];  
                    //Only add comma if there are multiple correct mcq answers, or if the iteration hasn't reached
                    //near the end
                    if(j < tempAnswers.length - 1) {
                        key += ", ";
                    }
                }
                //Close bracket
                key += "]";
            } else if(temp instanceof FIBquestion) {
            	//Answers come bracketed
                key += "[";
                //Temporary to make code cleaner
                String[] tempAnswers = ((FIBquestion) temp).getAnswers();
                //Sort the array of answers as order does matter for fill in the blank questions
                Arrays.sort(tempAnswers);  
                //Add answers in corrected order to key, separated by comma, unless its at the end
                for(int j = 0; j < tempAnswers.length; j++) {
                    key += tempAnswers[j];
                    if(j < tempAnswers.length - 1) {
                        key += ", ";
                    }
                }
                //Close bracket of answers
                key += "]";
            } else if(temp instanceof TFquestion) {
            	//Only one correct answer simply add to key.
                key += ((TFquestion) temp).getAnswer();        
            }
            key += "\n"; // Add a new line after each question
        }
        return key;
    }

    @Override
    public boolean addStudent(String name) {
        // Changed the logic: add student if it does not exist.
        if(this.database.findStudent(name) != null)
            return false;
        Student s = new Student(name);
        // Add all existing exams to the student's exam list.
        for(Exam e : this.database.getExamList()){
            s.addExam(e);
        }
        this.database.getStudentList().add(s);
        return true;
    }

    @Override
    public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
        Student student = this.database.findStudent(studentName);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        Exam exam = student.findExam(examId);
        if (exam == null) {
            System.out.println("Exam not found for student.");
            return;
        }
        ExamSubmission submission = exam.getSubmissionForStudent(studentName);
        if(submission == null) {
            submission = new ExamSubmission(exam);  // Use already found exam.
            exam.addSubmission(studentName, submission);
        }
        submission.answerQuestion(questionNumber - 1, answer); // using questionNumber - 1 inline.
    }

    @Override
    public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
        Student student = database.findStudent(studentName);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        Exam exam = student.findExam(examId);
        if (exam == null) {
            System.out.println("Exam not found for student.");
            return;
        }
        ExamSubmission submission = exam.getSubmissionForStudent(studentName);
        if (submission == null) {
            submission = new ExamSubmission(exam);
            exam.addSubmission(studentName, submission);
        }
        submission.answerQuestion(questionNumber - 1, answer);
    }

    @Override
    public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
        Student student = database.findStudent(studentName);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        Exam exam = student.findExam(examId);
        if (exam == null) {
            System.out.println("Exam not found for student.");
            return;
        }
        ExamSubmission submission = exam.getSubmissionForStudent(studentName);
        if (submission == null) {
            submission = new ExamSubmission(exam);
            exam.addSubmission(studentName, submission);
        }
        submission.answerQuestion(questionNumber - 1, answer);
    }

    @Override
    public double getExamScore(String studentName, int examId) {
        Student student = this.database.findStudent(studentName);
        if (student == null) return 0.0;
        Exam exam = student.findExam(examId);
        if (exam == null) return 0.0;
        ExamSubmission submission = exam.getSubmissionForStudent(studentName);
        if (submission == null) return 0.0;
        return submission.computeScore();
    }

    @Override
    public String getGradingReport(String studentName, int examId) {
        String report = "";
        //Find student, exam, and student's submission for said exam while checking if anything is null
        Student student = this.database.findStudent(studentName);
        if(student == null) return "Student not found.";
        Exam exam = this.database.findExam(examId);
        if(exam == null) return "Exam not found.";
        ExamSubmission submission = exam.getSubmissionForStudent(studentName);
        //Check if student has submitted specified exam
        if(submission == null) return "No submission for exam.";
        double score = 0;
        double scoreOutOf = 0;
        //Iterate through exam 
        for(int i = 0; i < exam.getQuestions().size(); i++) {
        	//Get each question and retrieve answer in object form in order to pass it in to correct question type
            Question question = exam.getQuestions().get(i);
            Object answer = submission.getStudentAnswers().get(i);
            double questionScore = question.grade(answer);
            double questionPoints = question.getPoints();
            //Update current student score and total possible score.
            score += questionScore;
            scoreOutOf += questionPoints;
            //Add question information to the report
            report += "Question #" + question.getQuestionNumber() + " " + questionScore + " points out of " + questionPoints + "\n";       
        }
        //Final score 
        report += "Final Score: " + score + " out of " + scoreOutOf;
        return report;
    }

    @Override
    public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
    	//Update Instance variables
        this.letterGrades = letterGrades;
        this.cutoffs = cutoffs;
    }

    @Override
    public double getCourseNumericGrade(String studentName) {
        double percentage = 0.0;
        int examCount = 0;
        //Find specified student
        Student student = this.database.findStudent(studentName);
        //If student does not exist return 0
        if (student == null) return 0.0;
        //Loop through students exams
        for (int i = 0; i < student.getExams().size(); i++) {
        	//Get score for each exam
            double score = this.getExamScore(studentName, student.getExams().get(i).getId());
            double examTotalPoints = 0.0;
            //Get total points for specified exams 
            for (Question q : student.getExams().get(i).getQuestions()) {
                examTotalPoints += q.getPoints();
            }
            //Verify that exam 
            if (examTotalPoints > 0) {
            	//Calculate exam score in percentage and add it to the total percentage 
                double examPercentage = (score / examTotalPoints) * 100.0;
                percentage += examPercentage;
                //Update count of exams
                examCount++;
            }
        }
        //Return 0 if there are no exams, otherwise divide total percentage by count to get the grade
        return examCount == 0 ? 0.0 : percentage / examCount;
    }

    @Override
    public String getCourseLetterGrade(String studentName) {
    	//Retrieve grade using previous method for specified student
        double numericGrade = getCourseNumericGrade(studentName);
        //Loop through the grade cutoffs
        for (int i = 0; i < cutoffs.length; i++) {
        	//Once students grade reaches correct grade range return letter grade 
            if (numericGrade >= cutoffs[i]) {
                return letterGrades[i];
            }
        }
        //Return last grade if no applicable range is found
        return letterGrades[letterGrades.length - 1];
    }

    @Override
    public String getCourseGrades() {
    	//Create list of students
        List<Student> studentList = this.database.getStudentList();
        // Sort students by name.
        Collections.sort(studentList, (s1, s2) -> s1.getName().compareTo(s2.getName()));
        String grades = "";
        //Loop through list, getting grade for each student 
        for (Student s : studentList) {
            double numericGrade = getCourseNumericGrade(s.getName());
            String letterGrade = getCourseLetterGrade(s.getName());
            //Each line is added for a students grade
            grades += s.getName() + " " + numericGrade + " " + letterGrade + "\n";
        }
        return grades;
    }

    @Override
    public double getMaxScore(int examId) {
        // Find the specified exam in the database.
        Exam exam = this.database.findExam(examId);
        if (exam == null) return 0.0;
        
        // Flag to indicate if we have found at least one valid submission.
        boolean initialized = false;
        // This will hold the maximum score found.
        double maxScore = 0.0;    
        // Iterate through every student in the database.
        for (Student student : this.database.getStudentList()) {
            // Retrieve the student's submission for this exam.
            ExamSubmission sub = exam.getSubmissionForStudent(student.getName());
            if (sub != null) {
                // Compute the score from the submission.
                double score = sub.computeScore();
                if (!initialized) {
                    maxScore = score;
                    initialized = true;
                } else if (score > maxScore) {
                    // Update maxScore if this submission's score is higher.
                    maxScore = score;
                }
            }
        }
        // Return the maximum score found, or 0.0 if no submissions were found.
        return initialized ? maxScore : 0.0;
    }

    @Override
    public double getMinScore(int examId) {
        // Find the specified exam in the database.
        Exam exam = this.database.findExam(examId);
        if (exam == null) return 0.0;
        
        // Flag to indicate if a valid submission has been found.
        boolean initialized = false;
        // This will hold the minimum score found.
        double minScore = 0.0;
        
        // Iterate through all students in the database.
        for (Student student : this.database.getStudentList()) {
            // Get the student's submission for the exam.
            ExamSubmission sub = exam.getSubmissionForStudent(student.getName());
            if (sub != null) {
                // Compute the submission's score.
                double score = sub.computeScore();
                if (!initialized) {
                    minScore = score;
                    initialized = true;
                } else if (score < minScore) {
                    // Update minScore if this submission's score is lower.
                    minScore = score;
                }
            }
        }
        // Return the minimum score found, or 0.0 if no submissions were found.
        return initialized ? minScore : 0.0;
    }

    @Override
    public double getAverageScore(int examId) {
        // Find the exam with the specified examId.
        Exam exam = this.database.findExam(examId);
        if (exam == null) return 0.0;
        
        // Total score across all submissions and count of valid submissions.
        double totalScore = 0.0;
        int count = 0;
        
        // Iterate over all students in the database.
        for (Student student : this.database.getStudentList()) {
            // Retrieve the student's submission for the exam.
            ExamSubmission sub = exam.getSubmissionForStudent(student.getName());
            if (sub != null) {
                // Accumulate the score and increase the submission count.
                totalScore += sub.computeScore();
                count++;
            }
        }
        // Calculate and return the average; return 0.0 if no valid submissions exist.
        return count > 0 ? totalScore / count : 0.0;
    }


    @Override
    public void saveManager(Manager manager, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(manager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Manager restoreManager(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Manager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
