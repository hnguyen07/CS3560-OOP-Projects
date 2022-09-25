import java.util.List;

/**
 * Represents a multiple choice question.
 * Inherited from the Question class
 * Has its own definition for checking the answers
 */
public class MultipleChoiceQuestion extends Question {

    public MultipleChoiceQuestion(String content, List<String> choices, List<String> trueAns) {
        super(content, choices, trueAns);
    }

    /**
     * Represents a multiple choice question (Extends from the Question class).
     * Checks the correctness of the answers.
     * May have more than 1 correct answers per question (1 is accepted too).
     */
    public  String chkAns(List<String> submittedAnswers) {
        if (submittedAnswers.size() == 0) {
            return "Please choose at least 1 answer.";
        } else {
            // Check if the number of correct answers is equal to
            // the number of submitted answers from the student
            if (this.trueAns.size() == submittedAnswers.size()) {
                // Check if the submitted answers are in the answer keys
                for (String answer : submittedAnswers) {
                    if (!this.trueAns.contains(answer)) {
                        return "Incorrect answer.";
                    }
                }
                return "Correct answer!";
            }
            else {
                return "Incorrect answer.";
            }
        }
    }
}
