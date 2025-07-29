package onlineTest;

import java.util.Arrays;

public class MCquestion extends Question {
    private String[] answer;
    
    public MCquestion(int examId, int questionNumber, String text, double points, String[] answer) {
        super(examId, questionNumber, text, points);
        this.answer = answer;
    }
    
    public String[] getAnswer() {
        return answer;
    }
    
    @Override
    public double grade(Object answer) {
    	//Checks if the inputed answer and correct answer are equivalent arrays, rewarding points if so
        if (answer instanceof String[]) {
            String[] studentAnswer = (String[]) answer;
            if (Arrays.equals(this.answer, studentAnswer)) {
                return getPoints();
            }
        }
        return 0.0;
    }
}
