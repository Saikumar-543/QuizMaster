import java.util.ArrayList;

/**
 * Represents a registered user of the Quiz Master application.
 *
 * Demonstrates: Encapsulation (private fields + public getters/setters),
 * Constructors, and Object relationships (a User "has" a list of QuizResults).
 */
public class User {

    // ---------- Fields (private = Encapsulation) ----------
    private String username;
    private String email;
    private String password;
    private String phoneNumber;

    // Statistics
    private int totalQuizzes;
    private double bestScore;      // stored as percentage, e.g. 90.0
    private double averageScore;   // stored as percentage, e.g. 78.0

    // Each user keeps track of their OWN quiz history.
    // This is how we guarantee "user-specific results" (Requirement 11)
    // without needing a database or complex session system.
    private ArrayList<QuizResult> quizResults;

    // ---------- Constructor ----------
    public User(String username, String email, String password, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.totalQuizzes = 0;
        this.bestScore = 0;
        this.averageScore = 0;
        this.quizResults = new ArrayList<>();
    }

    // ---------- Getters ----------
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getTotalQuizzes() {
        return totalQuizzes;
    }

    public double getBestScore() {
        return bestScore;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public ArrayList<QuizResult> getQuizResults() {
        return quizResults;
    }

    // ---------- Behavior ----------

    /**
     * Adds a new quiz result to this user's history and
     * recalculates their statistics (total quizzes, best score, average score).
     *
     * Input: a QuizResult object produced after a quiz is completed.
     * Processing: append to list, update totalQuizzes, recompute best & average.
     * Output: none (updates internal state of the User object).
     */
    public void addQuizResult(QuizResult result) {
        quizResults.add(result);
        totalQuizzes = quizResults.size();

        double sum = 0;
        for (QuizResult r : quizResults) {
            sum += r.getPercentage();
            if (r.getPercentage() > bestScore) {
                bestScore = r.getPercentage();
            }
        }
        averageScore = sum / quizResults.size();
    }
}
