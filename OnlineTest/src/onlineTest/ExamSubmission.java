package onlineTest;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class ExamSubmission implements Serializable {
    private Exam exam;
    private List<Object> studentAnswers;
    
    public ExamSubmission(Exam exam) {
        this.exam = exam;
        studentAnswers = new ArrayList<Object>();
        //Creat e anew list of answers relative to the size of the exam 
        for (int i = 0; i < exam.getQuestions().size(); i++) {
            studentAnswers.add(null);
        }
    }
    
    public void answerQuestion(int index, Object answer) {
    	//Method used when student answers question to place the answer in the correct spot
        if (index < studentAnswers.size()) {
            studentAnswers.set(index, answer);
        } else {
            while (studentAnswers.size() < index) {
                studentAnswers.add(null);
            }
            studentAnswers.add(answer);
        }
    }
    
    public List<Object> getStudentAnswers() {
        return studentAnswers;
    }
    
    public double computeScore() {
    	//Compute score for given submission
        double total = 0;
        //Get all questions in list
        List<Question> questions = exam.getQuestions();
        //Loop through using students answer for each question using grade method
        for (int i = 0; i < questions.size(); i++) {
            Object answer = (i < studentAnswers.size()) ? studentAnswers.get(i) : null;
            total += questions.get(i).grade(answer);
        }
        return total;
    }
}
