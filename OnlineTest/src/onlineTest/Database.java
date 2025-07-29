package onlineTest;

import java.io.Serializable;
import java.util.List;

public class Database implements Serializable {
    private List<Exam> examList;
    private List<Student> studentList;
    
    public Database(List<Exam> examList, List<Student> studentList) {
        this.examList = examList;
        this.studentList = studentList;
    }
    
    public List<Exam> getExamList() {
        return examList;
    }
    
    public List<Student> getStudentList() {
        return studentList;
    }
    
    public Exam findExam(int examId) {
        for (Exam exam : examList) {
            if (exam.getId() == examId) {
                return exam;
            }
        }
        return null;
    }
    //Find specified student by name
    public Student findStudent(String name) {
        for (Student s : studentList) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }
}
