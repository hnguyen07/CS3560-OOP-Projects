import java.util.List;

/**
 * Abstract class for a single choice or multiple choice question.
 * It includes the question's content, the given choices, and the
 * true answers.
 */
public abstract class Question {

    protected String content;
    protected List<String> choices;
    protected List<String> trueAns;

    public Question(String content, List<String> choices, List<String> trueAns) {
        this.content = content;
        this.choices = choices;
        this.trueAns = trueAns;
    }

    public String getQuestion() {

        return this.content;
    }

    public List<String> getChoices() {

        return this.choices;
    }

    /**
     * Return true if the answer are in the provided choices
     */
    public boolean validChoices(String answer) {

        return this.choices.contains(answer);
    }    
    
    /**
     * Abstract function to be defined in the child classes.
     * Single choice and multiple choice questions have different 
     * approach in checking the correctness of the submitted answers. 
     */
    public abstract String chkAns(List<String> submittedAnswers);
}
