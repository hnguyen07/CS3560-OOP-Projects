import java.util.List;

/**
 * Represents a student who has an ID and answers to the question 
 * (Each student ID is unique. For simplicity, the student id is 
 * generated using a variable "count" that is incremented each time 
 * a student is created). 
 */
public class Student {

    // count plays the role of an ID to keep track of the number of students
    private static int count = 0;
    private final String studentId;
    private List<String> answers;

    /** count is incremented each time a student is created. */
    public Student() {
        count += 1;
        this.studentId= "" + count;
    }

    public String getId() {
        return studentId;
    }

    public List<String> getAnswers() {
        return answers;
    }

    /**
     * The student can have one or more than one answers
     * depending on the question type (single choice or multiple choice).
     */
    public void selectAns(List<String> selectedAnswers) {
        this.answers = selectedAnswers;
    }
}
