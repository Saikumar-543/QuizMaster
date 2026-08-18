/**
 * Represents a single multiple-choice quiz question.
 *
 * Demonstrates: Encapsulation and a simple, focused data-holding class
 * (sometimes called a "model" or "POJO" - Plain Old Java Object).
 */
public class Question {

    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer; // "A", "B", "C", or "D"
    private String category;

    public Question(String questionText, String optionA, String optionB,
                     String optionC, String optionD, String correctAnswer,
                     String category) {
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.category = category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getCategory() {
        return category;
    }
}
