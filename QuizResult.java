/**
 * Represents the outcome of ONE completed quiz attempt.
 *
 * A User stores an ArrayList<QuizResult> to remember their quiz history.
 */
public class QuizResult {

    private String category;
    private int totalQuestions;
    private int correctAnswers;
    private double percentage;

    public QuizResult(String category, int totalQuestions, int correctAnswers) {
        this.category = category;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        // percentage = (correctAnswers * 100.0) / totalQuestions
        this.percentage = (correctAnswers * 100.0) / totalQuestions;
    }

    public String getCategory() {
        return category;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public double getPercentage() {
        return percentage;
    }

    /**
     * Returns the score formatted like "4/5".
     */
    public String getScoreText() {
        return correctAnswers + "/" + totalQuestions;
    }
}
