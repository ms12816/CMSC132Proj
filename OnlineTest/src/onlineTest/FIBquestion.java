package onlineTest;

public class FIBquestion extends Question {
    private String[] answers;
    
    public FIBquestion(int examId, int questionNumber, String text, double points, String[] answers) {
        super(examId, questionNumber, text, points);
        this.answers = answers;
    }
    
    public String[] getAnswers() {
        return answers;
    }
    
    @Override
    public double grade(Object answer) {
    	//Make sure answer is in correct form
        if (answer instanceof String[]) {
        	//Checks if inputed answer array is correct
            String[] studentAnswer = (String[]) answer;
            int correct = 0;
            //Checks all the inputed answers, giving partial credit as a percentage of total questions
            for(String answers : this.answers) {
            	for(String studentAnswers : studentAnswer) {
            		if(answers.equals(studentAnswers)) {
            			correct++;
            			//Once correct is found break to outer loop
            			break;
            		}            		
            	}
            }
            //Get the percentage of total points the answer gets
            double partOfTotal = (double) correct / this.answers.length;
            return partOfTotal * this.getPoints();
        }
        return 0.0;
    }
}
