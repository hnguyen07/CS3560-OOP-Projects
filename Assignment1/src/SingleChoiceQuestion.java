import java.util.List;

/**
 * Represents a single choice question.
 * Extends from the Question class.
 * Has its own definition for checking the correctness of the answers.
 */
public class SingleChoiceQuestion extends Question {

    public SingleChoiceQuestion(String content, List<String> choices, List<String> trueAns) {
        super(content, choices, trueAns);

        if (trueAns.size() != 1) {
            throw new IllegalArgumentException("Only one correct answer is allowed!");
        }
    }

    /**
     * Represents a single choice question (Extends from the Question class).
     * Checks the correctness of the answers.
     * Only 1 correct answer per question.
     */
    public String chkAns(List<String> submittedAnswers) {
        if (submittedAnswers.size() > 1) {
            return "Only 1 answer is allowed for a single-choice question!";
        }
        else if (submittedAnswers.size() != 1) {
            return "Please choose 1 answer.";
        } else {
            // Compare the submitted answer with the answer key
            if (this.trueAns.get(0).compareTo(submittedAnswers.get(0)) == 0) {
                return "Correct answer!";
            } else {
                return "Incorrect answer.";
            }
        }
    }
}
