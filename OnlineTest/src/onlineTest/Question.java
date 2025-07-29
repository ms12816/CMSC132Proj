package onlineTest;

import java.io.Serializable;

public abstract class Question implements Serializable {
    private int examId;
    private int questionNumber;
    private String text;
    private double points;
    
    public Question(int examId, int questionNumber, String text, double points) {
        this.examId = examId;
        this.questionNumber = questionNumber;
        this.text = text;
        this.points = points;
    }
    
    public int getExamId() {
        return examId;
    }
    
    public int getQuestionNumber() {
        return questionNumber;
    }
    
    public String getText() {
        return text;
    }
    
    public double getPoints() {
        return points;
    }
    
    public abstract double grade(Object answer);
}
