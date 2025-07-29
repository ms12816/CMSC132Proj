package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import onlineTest.SystemManager;
/**
 * 
 * You need student tests if you are looking for help during office hours about
 * bugs in your code.
 * 
 * @author UMCP CS Department
 *
 */

public class StudentTests {

    // Test for addExam (and that the exam appears in getKey)
    @Test
    public void testAddExam() {
        SystemManager manager = new SystemManager();
        boolean result = manager.addExam(1, "Test Exam");
        assertTrue("Exam should be added", result);
        String key = manager.getKey(1);
        assertFalse("getKey should not return 'Exam not found'", key.contains("Exam not found"));
    }
    
    // Test for addTrueFalseQuestion by verifying the key output contains capitalized True/False.
    @Test
    public void testAddTrueFalseQuestion() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Test Exam");
        manager.addTrueFalseQuestion(1, 1, "Is Java fun?", 5, true);
        String key = manager.getKey(1);
        // Expect the correct answer to be printed as "True"
        assertTrue("Key should contain capitalized True", key.contains("True"));
    }
    
    // Test for addMultipleChoiceQuestion using getKey output.
    @Test
    public void testAddMultipleChoiceQuestion() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Test Exam");
        String questionText = "Select letters A and B";
        String[] answers = {"A", "B"};
        manager.addMultipleChoiceQuestion(1, 1, questionText, 10, answers);
        String key = manager.getKey(1);
        // Key should show answers inside brackets
        assertTrue("Key should contain '[' and ']'", key.contains("[") && key.contains("]"));
        // And should contain both answers
        assertTrue("Key should contain A", key.contains("A"));
        assertTrue("Key should contain B", key.contains("B"));
    }
    
    // Test for addFillInTheBlanksQuestion by verifying sorted output in key.
    @Test
    public void testAddFillInTheBlanksQuestion() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Test Exam");
        String questionText = "Fill in the blanks";
        String[] answers = {"zeta", "alpha", "beta"};
        manager.addFillInTheBlanksQuestion(1, 1, questionText, 12, answers);
        String key = manager.getKey(1);
        // Since FIB answers are sorted, expect "alpha, beta, zeta"
        assertTrue("Key should contain sorted answers", key.contains("[alpha, beta, zeta]"));
    }
    
    // Test for getKey (using a combination of question types)
    @Test
    public void testGetKey() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Test Exam");
        manager.addTrueFalseQuestion(1, 1, "TF Q?", 5, false);
        manager.addMultipleChoiceQuestion(1, 2, "MC Q?", 10, new String[]{"X", "Y"});
        manager.addFillInTheBlanksQuestion(1, 3, "FIB Q?", 8, new String[]{"apple","banana"});
        String key = manager.getKey(1);
        // Key should contain all three question texts and correct answers.
        assertTrue(key.contains("TF Q?"));
        assertTrue(key.contains("MC Q?"));
        assertTrue(key.contains("FIB Q?"));
        assertTrue(key.contains("False"));
        assertTrue(key.contains("[X, Y]"));
        // Sort for FIB should result in [apple, banana]
        assertTrue(key.contains("[apple, banana]"));
    }
    
    // Test for addStudent: ensure new student is added and duplicate is rejected.
    @Test
    public void testAddStudent() {
        SystemManager manager = new SystemManager();
        boolean added = manager.addStudent("Smith,John");
        assertTrue("Should add new student", added);
        boolean duplicate = manager.addStudent("Smith,John");
        assertFalse("Duplicate student should not be added", duplicate);
    }
    
    // Test for answerTrueFalseQuestion and getExamScore
    @Test
    public void testAnswerTrueFalseQuestion() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Test Exam");
        manager.addTrueFalseQuestion(1, 1, "TF Q?", 5, true);
        manager.addStudent("Smith,John");
        manager.answerTrueFalseQuestion("Smith,John", 1, 1, true);
        double score = manager.getExamScore("Smith,John", 1);
        assertEquals("Score for correct TF answer", 5.0, score, 0.001);
    }
    
    // Test for answerMultipleChoiceQuestion and getExamScore
    @Test
    public void testAnswerMultipleChoiceQuestion() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Test Exam");
        String questionText = "MC Q?";
        String[] correct = {"A", "B"};
        manager.addMultipleChoiceQuestion(1, 1, questionText, 10, correct);
        manager.addStudent("Smith,John");
        manager.answerMultipleChoiceQuestion("Smith,John", 1, 1, new String[]{"A", "B"});
        double score = manager.getExamScore("Smith,John", 1);
        assertEquals("Score for correct MC answer", 10.0, score, 0.001);
    }
    
    // Test for answerFillInTheBlanksQuestion and getExamScore - full credit case.
    @Test
    public void testAnswerFillInTheBlanksQuestionFullCredit() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Test Exam");
        String questionText = "FIB Q?";
        String[] correct = {"apple", "banana"};
        manager.addFillInTheBlanksQuestion(1, 1, questionText, 8, correct);
        manager.addStudent("Smith,John");
        manager.answerFillInTheBlanksQuestion("Smith,John", 1, 1, new String[]{"banana", "apple"});
        double score = manager.getExamScore("Smith,John", 1);
        // Expect full credit (8 points) if the answers (order doesn't matter after sorting) match.
        assertEquals("Full credit for FIB Q", 8.0, score, 0.001);
    }
    
    // Test for answerFillInTheBlanksQuestion partial credit.
    @Test
    public void testAnswerFillInTheBlanksQuestionPartialCredit() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Test Exam");
        String questionText = "FIB Q?";
        String[] correct = {"apple", "banana"};
        manager.addFillInTheBlanksQuestion(1, 1, questionText, 8, correct);
        manager.addStudent("Smith,John");
        // Student gives only one correct answer.
        manager.answerFillInTheBlanksQuestion("Smith,John", 1, 1, new String[]{"apple"});
        double score = manager.getExamScore("Smith,John", 1);
        // Expect partial credit: half of 8 = 4
        assertEquals("Partial credit for FIB Q", 4.0, score, 0.001);
    }
    
    // Test for getGradingReport (should include "Final Score:" and question details)
    @Test
    public void testGetGradingReport() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Test Exam");
        manager.addTrueFalseQuestion(1, 1, "TF Q?", 5, true);
        manager.addStudent("Smith,John");
        manager.answerTrueFalseQuestion("Smith,John", 1, 1, true);
        String report = manager.getGradingReport("Smith,John", 1);
        assertTrue(report.contains("Final Score:"));
        assertTrue(report.contains("Question #1"));
    }
    
    // Test for setLetterGradesCutoffs and getCourseLetterGrade
    @Test
    public void testCourseLetterGrade() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Test Exam");
        manager.addTrueFalseQuestion(1, 1, "TF Q?", 100, true);
        manager.addStudent("Smith,John");
        manager.answerTrueFalseQuestion("Smith,John", 1, 1, true);
        // Full score for exam is 100 => numeric grade 100.
        manager.setLetterGradesCutoffs(new String[]{"A","B","C","D","F"}, new double[]{90,80,70,60,0});
        String letter = manager.getCourseLetterGrade("Smith,John");
        assertEquals("Letter grade should be A", "A", letter);
    }
    
    // Test for getCourseNumericGrade
    @Test
    public void testCourseNumericGrade() {
        SystemManager manager = new SystemManager();
        // Create two exams, each 100 points total.
        manager.addExam(1, "Exam 1");
        manager.addTrueFalseQuestion(1, 1, "TF Q1?", 50, true);
        manager.addTrueFalseQuestion(1, 2, "TF Q2?", 50, false);
        manager.addExam(2, "Exam 2");
        manager.addTrueFalseQuestion(2, 1, "TF Q3?", 100, true);
        // Add a student and answer both exams fully correctly.
        manager.addStudent("Smith,John");
        manager.answerTrueFalseQuestion("Smith,John", 1, 1, true);
        manager.answerTrueFalseQuestion("Smith,John", 1, 2, false);
        manager.answerTrueFalseQuestion("Smith,John", 2, 1, true);
        double numericGrade = manager.getCourseNumericGrade("Smith,John");
        // Each exam: 100%, so course numeric grade should be 100
        assertEquals("Numeric grade should be 100", 100.0, numericGrade, 0.001);
    }
    
    // Test for getMaxScore
    @Test
    public void testGetMaxScore() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Exam 1");
        manager.addTrueFalseQuestion(1, 1, "TF Q1?", 100, true);
        // Add two students with different scores.
        manager.addStudent("Smith,John");
        manager.addStudent("Doe,Jane");
        manager.answerTrueFalseQuestion("Smith,John", 1, 1, true);  // Score: 100
        manager.answerTrueFalseQuestion("Doe,Jane", 1, 1, false);    // Score: 0
        double maxScore = manager.getMaxScore(1);
        assertEquals("Max score should be 100", 100.0, maxScore, 0.001);
    }
    
    // Test for getMinScore
    @Test
    public void testGetMinScore() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Exam 1");
        manager.addTrueFalseQuestion(1, 1, "TF Q1?", 100, true);
        // Add two students with different scores.
        manager.addStudent("Smith,John");
        manager.addStudent("Doe,Jane");
        manager.answerTrueFalseQuestion("Smith,John", 1, 1, true);  // Score: 100
        manager.answerTrueFalseQuestion("Doe,Jane", 1, 1, false);   // Score: 0
        double minScore = manager.getMinScore(1);
        assertEquals("Min score should be 0", 0.0, minScore, 0.001);
    }
    
    // Test for getAverageScore
    @Test
    public void testGetAverageScore() {
        SystemManager manager = new SystemManager();
        manager.addExam(1, "Exam 1");
        manager.addTrueFalseQuestion(1, 1, "TF Q1?", 100, true);
        // Three students with scores: 100, 0, 100.
        manager.addStudent("Student1");
        manager.addStudent("Student2");
        manager.addStudent("Student3");
        manager.answerTrueFalseQuestion("Student1", 1, 1, true);
        manager.answerTrueFalseQuestion("Student2", 1, 1, false);
        manager.answerTrueFalseQuestion("Student3", 1, 1, true);
        double avgScore = manager.getAverageScore(1);
        // Average of (100, 0, 100) should be approximately 66.67
        assertEquals("Average score should be 66.67", 66.67, avgScore, 0.5);
    }
}
