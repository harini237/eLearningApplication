package Service;

import Entity.ActivityAttempt;
import Entity.Question;
import Repository.ActivityAttemptRepository;

import java.sql.Timestamp;
import java.time.Instant;

public class ScoreService {
    private final ActivityAttemptRepository attemptRepository;

    public ScoreService() {
        this.attemptRepository = new ActivityAttemptRepository();
    }

    // Method to log an activity attempt and calculate score
    public void logActivityAttempt(String activityId, Question question, int selectedOptionId, String studentId) {
        // Determine the score based on whether the selected option is correct
        int attemptScore = (selectedOptionId == question.getCorrectOption()) ? 3 : 1;
        String feedbackMessage;

        if (selectedOptionId == question.getCorrectOption()) {
            feedbackMessage = "Correct! You earned 3 points.";
        } else {
            feedbackMessage = "Incorrect. The correct answer is " + question.getCorrectOption() + ": " +
                    getExplanationForOption(question, question.getCorrectOption()) + "\nYou earned 1 point.";
        }

        // Print feedback message
        System.out.println(feedbackMessage);

        // Create an ActivityAttempt object
        ActivityAttempt attempt = new ActivityAttempt(
                activityId,
                question.getSectionId(),
                question.getChapterId(),
                question.getTextbookId(),
                studentId,
                question.getContentBlockId(),
                selectedOptionId,
                attemptScore,
                Timestamp.from(Instant.now())
        );

        // Log the attempt in the database
        attemptRepository.createActivityAttempt(attempt);
    }

    // Method to get explanation for a specific option
    private String getExplanationForOption(Question question, int option) {
        return switch (option) {
            case 1 -> question.getExplanation1();
            case 2 -> question.getExplanation2();
            case 3 -> question.getExplanation3();
            case 4 -> question.getExplanation4();
            default -> "No explanation available.";
        };
    }

    // Method to get total participation points for a student
    public int getTotalParticipationPoints(String studentId) {
        return attemptRepository.calculateTotalPoints(studentId);
    }
}

