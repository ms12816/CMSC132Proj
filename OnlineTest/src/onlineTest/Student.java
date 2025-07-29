package onlineTest;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable{
    private String name;
    private List<Exam> exams;
    
    public Student(String name) {
        this.name = name;
        this.exams = new ArrayList<Exam>();
    }
    
    public String getName() {
        return name;
    }
    
    public List<Exam> getExams() {
        return exams;
    }
    
    public Exam findExam(int examId) {
        for (Exam exam : exams) {
            if (exam.getId() == examId) {
                return exam;
            }
        }
        return null;
    }
    
    public void addExam(Exam exam) {
    	//Add exam if it does not already exist
        if (!exams.contains(exam)) {
            exams.add(exam);
        }
    }
}
