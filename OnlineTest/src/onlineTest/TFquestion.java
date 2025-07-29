package onlineTest;

public class TFquestion extends Question {
    private boolean answer;
    
    public TFquestion(int examId, int questionNumber, String text, double points, boolean answer) {
        super(examId, questionNumber, text, points);
        this.answer = answer;
    }
    
    public String getAnswer() {
    	return answer ? "True" : "False";
    }
    
    @Override
    public double grade(Object answer) {
    	//Check if inputed answer if correct, simply a true/ false check 
        if (answer instanceof Boolean) {
            return ((Boolean) answer) == this.answer ? getPoints() : 0.0;
        }
        return 0.0;
    }
}
