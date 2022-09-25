import java.util.List;

/** Interface for VotingService class */
public interface IVote {

    /**
     * Submits the student's ID and answers.
     * Returns true in the case of successful submission
     */
    boolean submit(String studentID, List<String> submittedAnswers);

    // Display the question and its choices to the students
    void questionDisplay();

    // Output the statistics of the submission result
    void statsDisplay();

    // Return the total number of submissions
    int totalSubmissions();
}
