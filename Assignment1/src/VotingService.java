import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 * The Voting Service implements the IVote class.
 * It is first configured with a given question type and candidate answers. 
 * Then, it can start accepting submissions from students.
 * It also outputs the statistics of the submission results.
 */
public class VotingService implements IVote {

    private final Hashtable<String, List<String>> allSubmissions;
    private final Question question;
    private int numOfCorrectAns;

    public VotingService(Question q) {
        this.question = q;
        this.allSubmissions = new Hashtable<>();
        this.numOfCorrectAns = 0;
    }

    public boolean submit(String studentId, List<String> selectedAnswers) {

        // Check if the students select the answers
        if (selectedAnswers.isEmpty()) {
            return false;
        }

        // Removes the duplicate answers of a student
        List<String> dupRemovedAns = rmvDup(selectedAnswers);
        List<String> validAns = new ArrayList<>();

        // Check if the answers of the student are within the provided choices
        for (String ans : dupRemovedAns) {
            if (this.question.validChoices(ans)) {
                validAns.add(ans);
            }
        }

        /*
         * Inserts a specific studentId and the linked answers into the submissions.
         * If an existing studentId is passed, the previous answers gets replaced by 
         * the new answers.
         */
        this.allSubmissions.put(studentId, validAns);
        return true;
    }

    /**
     * This function removes the duplicate answers of a student by using set as a holder.
     * FYI, set does not allow duplicate values.
     */

    private List<String> rmvDup(List<String> selectedAnswers) {

        // Remove the duplicate answers
        Set<String> temp = new HashSet<>(selectedAnswers);
        return new ArrayList<>(temp);
    }

    /** Display the question and its choices */
    public void questionDisplay() {

        String desciption = ">>>>>>>>>>>>>>> QUESTION <<<<<<<<<<<<<<<\n";
        desciption += this.question.getQuestion();
        int count = 0;

        for (String choice : this.question.getChoices()) {
            count++;
            desciption += "\n" + count + ". " + choice;
        }

        // Prints the question
        System.out.println(desciption);
    }

    /** Outputs the statistics of the submission results. */
    public void statsDisplay() {
        
        // Get the stats
        calculateStats();

        String stats = "________________________________________\n" +
                    "Correct Answer(s) = " + this.question.trueAns +
                    "\nAnswer Distribution:"; 
                    

        // Counts the number of answers for each choice
        List<String> choices = this.question.getChoices();

        for(String choice : choices) {
            int numOfChoices = 0;

            for (String studentId : this.allSubmissions.keySet()) {
                if (this.allSubmissions.get(studentId).contains(choice)) {
                    numOfChoices++;
                }
            }
            stats += "\n" + choice + ": " + numOfChoices;

        }
        stats += "\n________________________________________\n" +
                "Total Submissions Received: " + this.totalSubmissions() +
                "\nTotal Correct Answers     : " + this.numOfCorrectAns +
                "\n\n";

        // Prints the stats
        System.out.println(stats);
    }

    public int totalSubmissions() {

        return this.allSubmissions.size();
    }


    /** Counts the number of correct answers submitted for the question. */
    private void calculateStats() {

        this.numOfCorrectAns = 0;

        for (String studentId : this.allSubmissions.keySet()) {
            String result = question.chkAns(this.allSubmissions.get(studentId));

            // Increments the number of correct answers if they match the answer keys
            if (result.compareTo("Correct answer!") == 0) {
                this.numOfCorrectAns++;
            }
        }
    }
}
