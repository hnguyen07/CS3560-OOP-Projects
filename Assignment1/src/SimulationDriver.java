import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The SimulationDriver class automatically simulate the whole process of voting.
 * As a result, the process of manual inputting of questions or answers are 
 * skipped (We actually can edit the main to turn it into manual mode)
 * Functions of the class:
 * a. Create a question type and configure the answers
 * b. Configure the question for Voting Service
 * c. Randomly generate a number of students and the answers
 * d. Submit all the students' answers to Voting Service
 * e. Call the Voting Service to output function to display the result.
 */
class SimulationDriver {

        public static void main(String[] args) {

            // Prepare the single-choice questions
            String singleQ1 = "Do we need to know Java Swing for CS3560?";
            List<String> singleChoice1 = new ArrayList<>();
            List<String> trueAnswer1 = new ArrayList<>();
            singleChoice1.add("Yes");
            singleChoice1.add("No");
            trueAnswer1.add(singleChoice1.get(0));

            String singleQ2 = "Can color-blinded people see colors?";
            List<String> singleChoice2 = new ArrayList<>();
            List<String> trueAnswer2 = new ArrayList<>();
            singleChoice2.add("Yes");
            singleChoice2.add("No");
            trueAnswer2.add(singleChoice2.get(0));

            // Prepare the multiple-choice questions
            String multiQ1 = "How many World Wars that we had?";
            List<String> multiChoices1 = new ArrayList<>();
            List<String> trueAnswers1 = new ArrayList<>();
            multiChoices1.add("WW1");
            multiChoices1.add("WW2");
            multiChoices1.add("WW3");
            trueAnswers1.add(multiChoices1.get(0));
            trueAnswers1.add(multiChoices1.get(1));

            String multiQ2 = "Which ones below are dogs?";
            List<String> multiChoices2 = new ArrayList<>();
            List<String> trueAnswers2 = new ArrayList<>();
            multiChoices2.add("Shiba");
            multiChoices2.add("Birman");
            multiChoices2.add("Golden Retriever");
            multiChoices2.add("Scottish Fold");
            trueAnswers2.add(multiChoices2.get(0));
            trueAnswers2.add(multiChoices2.get(2));


            // Runs the simulation for 4 questions
            SimulationRun("SingleChoice", singleQ1, singleChoice1, trueAnswer1, 30);
            SimulationRun("SingleChoice", singleQ2, singleChoice2, trueAnswer2, 40);
            SimulationRun("MultipleChoices", multiQ1, multiChoices1, trueAnswers1, 30);
            SimulationRun("MultipleChoices", multiQ2, multiChoices2, trueAnswers2, 40);
        }

        /**
         * Simulates the voting process
         * Display stats after getting all the submissions from the students
         */
        private static void SimulationRun(String qType, String content, List<String> choices, 
                                        List<String> answers, int numOfStudents) {

            final Question question;
            final IVote iVote;
            final Student[] students = new Student[numOfStudents];

            // Create the type of question according to the provided type
            question = qType.equals("SingleChoice") ? new SingleChoiceQuestion(content, choices, answers)
                        : new MultipleChoiceQuestion(content, choices, answers);

            // Create a Voting Service to initiate the voting process       
            iVote = new VotingService(question);
            iVote.questionDisplay();   

            /*
             * Create the students based on the provided number
             * Auto generates the students' answers for the voting
             * Submit their answers to the voting service
             * Display the correct answer(s) and the stats of the answer distribution 
             */
            for (int i = 0; i < numOfStudents; i++) {
                students[i] = new Student();
                students[i].selectAns(randGenerateAns(choices, qType));
                iVote.submit(students[i].getId(), students[i].getAnswers());
            }
            iVote.statsDisplay();
        }

        /**
         * Choose the answers for the students randomly based on the 
         * provided choices for the question
         */
        private static List<String> randGenerateAns(List<String> choices, String qType) {

            // Default number of answers should be at least 1
            int numOfAns = 1;
            Random rand = new Random();
            ArrayList<String> answers = new ArrayList<>();

            /*
             * Apply for Multiple-choice questions
             * Generates randomly the number of choices that a student chooses 
             * (must be >= 1 and <= number of choices)
             */
            if (qType.equals("MultipleChoices")) {
                numOfAns = rand.nextInt(choices.size() + 1);
                while (numOfAns == 0) {
                    numOfAns = rand.nextInt(choices.size() + 1);
                }
            }

            /*
             * Pick some random choices based on the number that we got above
             * Add them to the student's answer
             */
            for (int i = 0; i < numOfAns; i++) {
                String choice = choices.get(rand.nextInt(choices.size()));
                // Make sure no duplicate choices before adding to the student's answer(s)
                if (!answers.contains(choice)) {
                    answers.add(choice);
                }
            }
            return answers;
        }
}
