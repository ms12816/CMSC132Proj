package onlineTest;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Exam implements Serializable{
    private int examId;
    private String title;
    private List<Question> questions;
    private Map<String, ExamSubmission> submissions;
    
    public Exam(int examId, String title) {
        this.examId = examId;
        this.title = title;
        this.questions = new ArrayList<Question>();
        this.submissions = new HashMap<>();
    }
    
    public int getId() {
        return examId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public List<Question> getQuestions() {
        return questions;
    }
    
    public void addSubmission(String studentName, ExamSubmission submission) {
        submissions.put(studentName, submission);
    }
    
    public ExamSubmission getSubmissionForStudent(String studentName) {
        return submissions.get(studentName);
    }
}
