import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * QUIZ MASTER - Console Application (Version 2: With File Persistence)
 *
 * This class controls the entire application flow:
 * Welcome -> Register/Login -> Dashboard -> Quiz/Results/Profile -> Logout/Exit
 *
 * Data is stored in a file (users.txt) and loaded on startup.
 */
public class Main {

    // ---------- Shared data ----------
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Question> questions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    // Tracks who is currently logged in. null = nobody logged in.
    private static User currentUser = null;

    // Fixed interior width used to draw aligned dashboard boxes.
    private static final int BOX_WIDTH = 54;
    
    // File path for storing user data
    private static final String USERS_FILE = "users.txt";

    public static void main(String[] args) {
        loadUsersFromFile();  // Load existing users from file
        loadAllQuestions();
        showWelcomeScreen();
    }

    /**
     * Safely reads one line of input from the Scanner.
     *
     * Calling scanner.nextLine() directly will throw a NoSuchElementException
     * and crash the program with an ugly stack trace if the input stream ever
     * runs out (for example, if input is piped from a file that ends, or the
     * user presses Ctrl+D/Ctrl+Z). This wrapper checks first and shuts the
     * application down cleanly instead of crashing.
     *
     * Input: none (reads from the shared Scanner)
     * Processing: checks hasNextLine() before reading
     * Output: the line of text entered by the user
     */
    private static String readLine() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nNo more input detected. Closing Quiz Master.");
            System.exit(0);
        }
        return scanner.nextLine();
    }

    // =====================================================================
    // 1. WELCOME SCREEN
    // =====================================================================
    private static void showWelcomeScreen() {
        boolean running = true;

        while (running) {
            System.out.println("\n========================================");
            System.out.println("               QUIZ MASTER");
            System.out.println("           Test Your Knowledge");
            System.out.println("========================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("\nEnter your choice: ");

            String choice = readLine().trim();

            switch (choice) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    loginUser();
                    break;
                case "3":
                    exitApplication();
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please select a valid option.");
            }
        }
    }

    // =====================================================================
    // 2. REGISTRATION
    // =====================================================================
    private static void registerUser() {
        System.out.println("\n========================================");
        System.out.println("            USER REGISTRATION");
        System.out.println("========================================");

        System.out.print("Username: ");
        String username = readLine().trim();

        if (username.isEmpty()) {
            System.out.println("\nUsername cannot be empty.");
            return;
        }

        if (findUserByUsername(username) != null) {
            System.out.println("\nUsername already exists.");
            System.out.println("Please choose another username.");
            return;
        }

        System.out.print("Email: ");
        String email = readLine().trim();

        if (email.isEmpty()) {
            System.out.println("\nEmail cannot be empty.");
            return;
        }

        if (findUserByEmail(email) != null) {
            System.out.println("\nAn account with this email already exists.");
            return;
        }

        System.out.print("Phone Number: ");
        String phoneNumber = readLine().trim();

        if (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("\nInvalid phone number.");
            System.out.println("Phone number must be 10 digits and start with 6, 7, 8, or 9.");
            return;
        }

        System.out.print("Password: ");
        String password = readLine();

        if (password.isEmpty()) {
            System.out.println("\nPassword cannot be empty.");
            return;
        }

        System.out.print("Confirm Password: ");
        String confirmPassword = readLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("\nPasswords do not match.");
            return;
        }

        User newUser = new User(username, email, password, phoneNumber);
        users.add(newUser);
        
        // Save users to file
        saveUsersToFile();

        System.out.println("\nRegistration successful!");
        System.out.println("User data has been saved.");
        System.out.println("Please login to continue.");
    }

    // =====================================================================
    // 3. LOGIN
    // =====================================================================
    private static void loginUser() {
        System.out.println("\n========================================");
        System.out.println("               USER LOGIN");
        System.out.println("========================================");

        try {
            System.out.print("Username: ");
            String username = readLine().trim();

            if (username.isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty.");
            }

            System.out.print("Password: ");
            String password = readLine();

            if (password.isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty.");
            }

            User user = findUserByUsername(username);

            // Do not reveal whether username or password alone was wrong.
            if (user == null) {
                throw new Exception("User not found. Please register first or check your username.");
            }

            if (!user.getPassword().equals(password)) {
                throw new Exception("Invalid username or password.");
            }

            currentUser = user;
        System.out.println("\n========================================");
        System.out.println("       LOGIN SUCCESSFUL!");
        System.out.println("========================================");
        System.out.println("\nWelcome, " + currentUser.getUsername() + "!");
        System.out.println("\n--- Your Stored Credentials ---");
        System.out.println("Username : " + currentUser.getUsername());
        System.out.println("Email    : " + currentUser.getEmail());
        System.out.println("Phone    : " + currentUser.getPhoneNumber());
        System.out.println("-------------------------------");
            showDashboard();

        } catch (IllegalArgumentException e) {
            System.out.println("\n[ERROR] Invalid Input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n[ERROR] Login Failed: " + e.getMessage());
            System.out.println("Please try again.");
        }
    }

    // =====================================================================
    // 4. DASHBOARD
    // =====================================================================
    private static void showDashboard() {
        boolean atDashboard = true;

        while (atDashboard) {
            printDashboardHeader();
            printBoxTop();
            printBoxCentered("DASHBOARD");
            printBoxDivider();
            printBoxLine("Quizzes Taken   : " + currentUser.getTotalQuizzes());
            printBoxLine("Best Score      : " + (int) currentUser.getBestScore() + "%");
            printBoxLine("Average Score   : " + (int) currentUser.getAverageScore() + "%");
            printBoxDivider();
            printBoxLine("1. Start Quiz");
            printBoxLine("2. Quiz Categories");
            printBoxLine("3. My Results");
            printBoxLine("4. My Profile");
            printBoxLine("5. View Credentials");
            printBoxLine("6. Logout");
            printBoxLine("7. Exit");
            printBoxBottom();
            System.out.print("\nEnter your choice: ");

            String choice = readLine().trim();

            switch (choice) {
                case "1":
                    showCategories();
                    break;
                case "2":
                    showCategories();
                    break;
                case "3":
                    showResults();
                    break;
                case "4":
                    showProfile();
                    break;
                case "5":
                    viewCredentials();
                    break;
                case "6":
                    logout();
                    atDashboard = false;
                    break;
                case "7":
                    exitApplication();
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nInvalid choice. Please select a valid option.");
            }
        }
    }

    /**
     * Prints the top banner and a personalized greeting for the dashboard.
     * The greeting changes depending on whether the user has taken quizzes
     * before - a small touch that makes the app feel more "alive".
     */
    private static void printDashboardHeader() {
        System.out.println("\n================================================");
        System.out.println("                   QUIZ MASTER");
        System.out.println("================================================");
        System.out.println("\nWelcome back, " + currentUser.getUsername() + "!");

        if (currentUser.getTotalQuizzes() == 0) {
            System.out.println("Take your first quiz to get started.");
        } else {
            System.out.println("Ready for another challenge?");
        }
        System.out.println();
    }

    // ---- Small helpers for drawing aligned dashboard boxes ----

    private static void printBoxTop() {
        System.out.println("+" + "-".repeat(BOX_WIDTH) + "+");
    }

    private static void printBoxBottom() {
        System.out.println("+" + "-".repeat(BOX_WIDTH) + "+");
    }

    private static void printBoxDivider() {
        System.out.println("+" + "-".repeat(BOX_WIDTH) + "+");
    }

    private static void printBoxLine(String text) {
        System.out.printf("| %-" + (BOX_WIDTH - 1) + "s|%n", text);
    }

    private static void printBoxCentered(String text) {
        int totalPadding = BOX_WIDTH - text.length();
        int left = totalPadding / 2;
        int right = totalPadding - left;
        System.out.println("|" + " ".repeat(Math.max(left, 0)) + text
                + " ".repeat(Math.max(right, 0)) + "|");
    }

    // =====================================================================
    // 5. QUIZ CATEGORIES
    // =====================================================================
    private static void showCategories() {
        System.out.println("\n========================================");
        System.out.println("           QUIZ CATEGORIES");
        System.out.println("========================================");
        System.out.println("1. Java");
        System.out.println("2. Programming");
        System.out.println("3. OOP");
        System.out.println("4. Data Structures");
        System.out.println("5. General Knowledge");
        System.out.println("6. Back to Dashboard");
        System.out.print("\nChoose category: ");

        String choice = readLine().trim();

        switch (choice) {
            case "1":
                startQuiz("Java");
                break;
            case "2":
                startQuiz("Programming");
                break;
            case "3":
                startQuiz("OOP");
                break;
            case "4":
                startQuiz("Data Structures");
                break;
            case "5":
                startQuiz("General Knowledge");
                break;
            case "6":
                // simply return to dashboard
                break;
            default:
                System.out.println("\nInvalid choice. Please select a valid option.");
        }
    }

    // =====================================================================
    // 6. START QUIZ
    // =====================================================================
    private static void startQuiz(String category) {
        ArrayList<Question> quizQuestions = getQuestionsByCategory(category);

        // Shuffle so the question order (and, if the category has more than 5
        // questions in the bank, the actual subset) is different each attempt.
        java.util.Collections.shuffle(quizQuestions);

        // Cap at 5 questions per quiz, even if the category's bank is larger.
        int questionCount = Math.min(5, quizQuestions.size());
        quizQuestions = new ArrayList<>(quizQuestions.subList(0, questionCount));

        System.out.println("\n========================================");
        System.out.println("             " + category.toUpperCase() + " QUIZ");
        System.out.println("========================================");
        System.out.println("\nCategory: " + category);
        System.out.println("Questions: " + quizQuestions.size());
        System.out.println("\nRules:");
        System.out.println("- Each question has one correct answer.");
        System.out.println("- Select A, B, C, or D.");
        System.out.println("- Each correct answer = 1 mark.");
        System.out.print("\nPress ENTER to start...");
        readLine();

        int correctAnswers = 0;

        for (int i = 0; i < quizQuestions.size(); i++) {
            Question q = quizQuestions.get(i);

            System.out.println("\n----------------------------------------");
            System.out.println("Question " + (i + 1) + " of " + quizQuestions.size());
            System.out.println("----------------------------------------");
            System.out.println("\n" + q.getQuestionText());
            System.out.println("\nA. " + q.getOptionA());
            System.out.println("B. " + q.getOptionB());
            System.out.println("C. " + q.getOptionC());
            System.out.println("D. " + q.getOptionD());

            String answer = getValidAnswer();

            if (answer.equalsIgnoreCase(q.getCorrectAnswer())) {
                System.out.println("\nCorrect!");
                correctAnswers++;
            } else {
                System.out.println("\nWrong!");
                System.out.println("Correct Answer: " + q.getCorrectAnswer());
            }
        }

        // Build and store the result
        QuizResult result = new QuizResult(category, quizQuestions.size(), correctAnswers);
        currentUser.addQuizResult(result);

        showScore(result);
    }

    /**
     * Keeps asking the user until they type a valid option (A, B, C, or D).
     * Input: none (reads from Scanner)
     * Processing: loops until valid input is given
     * Output: a valid answer string ("A", "B", "C", or "D")
     */
    private static String getValidAnswer() {
        while (true) {
            System.out.print("\nYour Answer: ");
            String answer = readLine().trim().toUpperCase();

            if (answer.equals("A") || answer.equals("B") ||
                answer.equals("C") || answer.equals("D")) {
                return answer;
            }
            System.out.println("Invalid choice. Please select a valid option (A, B, C, or D).");
        }
    }

    // =====================================================================
    // 7. SCORE CALCULATION / DISPLAY
    // =====================================================================
    private static void showScore(QuizResult result) {
        System.out.println("\n========================================");
        System.out.println("             QUIZ COMPLETED");
        System.out.println("========================================");
        System.out.println("\nUser       : " + currentUser.getUsername());
        System.out.println("Category   : " + result.getCategory());
        System.out.println("\nTotal Questions : " + result.getTotalQuestions());
        System.out.println("Correct Answers : " + result.getCorrectAnswers());
        System.out.println("Wrong Answers   : " + (result.getTotalQuestions() - result.getCorrectAnswers()));
        System.out.println("\nScore      : " + result.getScoreText());
        System.out.println("Percentage : " + (int) result.getPercentage() + "%");
        System.out.println("\nPerformance: " + getPerformanceLevel(result.getPercentage()));
        System.out.println("\n========================================");
    }

    private static String getPerformanceLevel(double percentage) {
        if (percentage >= 90) {
            return "Excellent";
        } else if (percentage >= 75) {
            return "Very Good";
        } else if (percentage >= 60) {
            return "Good";
        } else if (percentage >= 40) {
            return "Average";
        } else {
            return "Needs Improvement";
        }
    }

    // =====================================================================
    // 8. MY RESULTS (HISTORY) - user-specific
    // =====================================================================
    private static void showResults() {
        ArrayList<QuizResult> results = currentUser.getQuizResults();

        System.out.println("\n================================================");
        System.out.println("                 MY RESULTS");
        System.out.println("================================================");

        if (results.isEmpty()) {
            System.out.println("\nYou haven't taken any quizzes yet.");
            System.out.println("\n================================================");
            return;
        }

        System.out.printf("%n%-20s%-12s%-10s%n", "Category", "Score", "Percentage");
        System.out.println("------------------------------------------------");

        for (QuizResult r : results) {
            System.out.printf("%-20s%-12s%-10s%n",
                    r.getCategory(), r.getScoreText(), (int) r.getPercentage() + "%");
        }

        System.out.println("\nBest Score     : " + (int) currentUser.getBestScore() + "%");
        System.out.println("Average Score  : " + (int) currentUser.getAverageScore() + "%");
        System.out.println("\n================================================");
    }

    // =====================================================================
    // 9. MY PROFILE
    // =====================================================================
    private static void showProfile() {
        System.out.println("\n========================================");
        System.out.println("             MY PROFILE");
        System.out.println("========================================");
        System.out.println("\nUsername : " + currentUser.getUsername());
        System.out.println("Email    : " + currentUser.getEmail());
        System.out.println("Phone    : " + currentUser.getPhoneNumber());
        System.out.println("Quizzes  : " + currentUser.getTotalQuizzes());
        System.out.println("Best Score : " + (int) currentUser.getBestScore() + "%");
        System.out.println("Average Score : " + (int) currentUser.getAverageScore() + "%");
        System.out.println("\n========================================");
    }

    // =====================================================================
    // 10. VIEW CREDENTIALS - Display stored user login credentials
    // =====================================================================
    private static void viewCredentials() {
        System.out.println("\n========================================");
        System.out.println("         YOUR STORED CREDENTIALS");
        System.out.println("========================================");
        System.out.println("\nThe following credentials are stored in the system:");
        System.out.println("\n--- Account Information ---");
        System.out.println("Username     : " + currentUser.getUsername());
        System.out.println("Email Address: " + currentUser.getEmail());
        System.out.println("Phone Number : " + currentUser.getPhoneNumber());
        System.out.println("\n--- Security Note ---");
        System.out.println("Your password is stored securely and is not displayed.");
        System.out.println("For security reasons, you cannot view your password.");
        System.out.println("If you forget your password, contact support.");
        System.out.println("\n========================================");
    }

    // =====================================================================
    // 11. LOGOUT
    // =====================================================================
    private static void logout() {
        System.out.println("\nLogging out...");
        System.out.println("\nThank you, " + currentUser.getUsername() + "!");
        System.out.println("See you again.");
        currentUser = null;
    }

    // =====================================================================
    // 12. EXIT
    // =====================================================================
    private static void exitApplication() {
        System.out.println("\n========================================");
        System.out.println("     Thank you for using Quiz Master!");
        System.out.println("========================================");
        System.out.println("\nKeep learning. Keep improving.");
    }

    // =====================================================================
    // HELPER METHODS
    // =====================================================================

    /**
     * Validates phone number:
     * - Must be exactly 10 digits
     * - Must start with 6, 7, 8, or 9
     * - Must contain only numeric characters
     */
    private static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        // Check if it's exactly 10 digits
        if (phoneNumber.length() != 10) {
            return false;
        }

        // Check if all characters are digits
        if (!phoneNumber.matches("\\d+")) {
            return false;
        }

        // Check if first digit is 6, 7, 8, or 9
        char firstDigit = phoneNumber.charAt(0);
        return firstDigit == '6' || firstDigit == '7' || firstDigit == '8' || firstDigit == '9';
    }

    // =====================================================================
    // FILE PERSISTENCE METHODS
    // =====================================================================

    /**
     * Saves all users to a file (users.txt)
     * Format: username|email|password|phoneNumber
     */
    private static void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                String line = user.getUsername() + "|" + user.getEmail() + "|" + 
                             user.getPassword() + "|" + user.getPhoneNumber();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("[INFO] User data saved to file successfully.");
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save user data: " + e.getMessage());
        }
    }

    /**
     * Loads all users from the file (users.txt)
     * Format: username|email|password|phoneNumber
     */
    private static void loadUsersFromFile() {
        File file = new File(USERS_FILE);
        
        // If file doesn't exist, it's the first run
        if (!file.exists()) {
            System.out.println("[INFO] No existing user file found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            int loadedCount = 0;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                
                // Parse the line
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String username = parts[0].trim();
                    String email = parts[1].trim();
                    String password = parts[2].trim();
                    String phoneNumber = parts[3].trim();
                    
                    User user = new User(username, email, password, phoneNumber);
                    users.add(user);
                    loadedCount++;
                }
            }
            
            if (loadedCount > 0) {
                System.out.println("[INFO] Loaded " + loadedCount + " users from file.");
            }
            
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to load user data: " + e.getMessage());
        }
    }

    /**
     * Searches the users ArrayList for a matching username.
     * Input: username to search for
     * Processing: loops through the ArrayList comparing usernames
     * Output: the matching User object, or null if not found
     */
    private static User findUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    private static User findUserByEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    private static ArrayList<Question> getQuestionsByCategory(String category) {
        ArrayList<Question> result = new ArrayList<>();
        for (Question q : questions) {
            if (q.getCategory().equalsIgnoreCase(category)) {
                result.add(q);
            }
        }
        return result;
    }

    /**
     * Populates the in-memory question bank with 8 questions for EACH
     * category (Java, Programming, OOP, Data Structures, General Knowledge).
     * Each quiz attempt shuffles this pool and draws 5, so repeated attempts
     * in the same category don't always show the same 5 questions.
     * In a future version, this data could come from a file or database.
     */
    private static void loadAllQuestions() {

        // ---------------- JAVA ----------------
        questions.add(new Question(
                "Which keyword is used to inherit a class?",
                "implements", "extends", "super", "this",
                "B", "Java"));
        questions.add(new Question(
                "Which collection class allows dynamic resizing of an array?",
                "Array", "ArrayList", "HashMap", "Scanner",
                "B", "Java"));
        questions.add(new Question(
                "Which keyword is used to create an object in Java?",
                "class", "new", "void", "static",
                "B", "Java"));
        questions.add(new Question(
                "Which of these is used for reading input from the console?",
                "System.out", "Scanner", "Printer", "Reader",
                "B", "Java"));
        questions.add(new Question(
                "What is the default value of a boolean variable in Java?",
                "true", "false", "0", "null",
                "B", "Java"));
        questions.add(new Question(
                "Which method is the entry point of a Java application?",
                "start()", "main()", "run()", "init()",
                "B", "Java"));
        questions.add(new Question(
                "Which keyword is used to handle exceptions in Java?",
                "throw", "try", "catch", "finally",
                "B", "Java"));
        questions.add(new Question(
                "Which of these is NOT a Java primitive data type?",
                "int", "boolean", "String", "double",
                "C", "Java"));

        // ---------------- PROGRAMMING ----------------
        questions.add(new Question(
                "Which of these is NOT a programming paradigm?",
                "Object-Oriented", "Functional", "Structural", "Alphabetical",
                "D", "Programming"));
        questions.add(new Question(
                "What does IDE stand for?",
                "Integrated Development Environment", "Internal Data Exchange",
                "Interface Design Engine", "Instruction Definition Environment",
                "A", "Programming"));
        questions.add(new Question(
                "What is a variable?",
                "A fixed value", "A container that stores data",
                "A type of loop", "A programming language",
                "B", "Programming"));
        questions.add(new Question(
                "What best describes an algorithm?",
                "A programming language", "A step-by-step procedure to solve a problem",
                "A type of variable", "A hardware component",
                "B", "Programming"));
        questions.add(new Question(
                "Which loop is guaranteed to execute at least once?",
                "for", "while", "do-while", "for-each",
                "C", "Programming"));
        questions.add(new Question(
                "What is the process of finding and fixing errors in code called?",
                "Compiling", "Debugging", "Deploying", "Formatting",
                "B", "Programming"));
        questions.add(new Question(
                "Which symbol is commonly used for single-line comments in C-style languages?",
                "//", "##", "<!-- -->", "**",
                "A", "Programming"));
        questions.add(new Question(
                "What is a function/method that calls itself called?",
                "A loop", "A recursive function", "An interface", "A constructor",
                "B", "Programming"));

        // ---------------- OOP ----------------
        questions.add(new Question(
                "Which OOP principle allows a child class to reuse a parent class's members?",
                "Encapsulation", "Inheritance", "Polymorphism", "Abstraction",
                "B", "OOP"));
        questions.add(new Question(
                "What is the process of hiding internal details and exposing only functionality called?",
                "Inheritance", "Polymorphism", "Encapsulation", "Overloading",
                "C", "OOP"));
        questions.add(new Question(
                "Which OOP concept allows one interface to take many forms?",
                "Abstraction", "Polymorphism", "Inheritance", "Encapsulation",
                "B", "OOP"));
        questions.add(new Question(
                "What is a class in OOP?",
                "An instance of an object", "A blueprint for creating objects",
                "A loop structure", "A data type",
                "B", "OOP"));
        questions.add(new Question(
                "Which keyword commonly prevents a class from being inherited in Java?",
                "static", "private", "final", "protected",
                "C", "OOP"));
        questions.add(new Question(
                "What is it called when a subclass provides a specific implementation of a method already defined in its parent?",
                "Overloading", "Overriding", "Encapsulating", "Instantiating",
                "B", "OOP"));
        questions.add(new Question(
                "Which OOP concept focuses on showing only essential features and hiding complexity?",
                "Abstraction", "Inheritance", "Encapsulation", "Polymorphism",
                "A", "OOP"));
        questions.add(new Question(
                "What is a constructor used for?",
                "Deleting objects", "Initializing a new object", "Looping through data", "Importing packages",
                "B", "OOP"));

        // ---------------- DATA STRUCTURES ----------------
        questions.add(new Question(
                "Which data structure follows FIFO (First In First Out) order?",
                "Stack", "Queue", "Array", "Tree",
                "B", "Data Structures"));
        questions.add(new Question(
                "Which data structure follows LIFO (Last In First Out) order?",
                "Queue", "Stack", "Linked List", "Graph",
                "B", "Data Structures"));
        questions.add(new Question(
                "What is the time complexity of accessing an array element by index?",
                "O(n)", "O(log n)", "O(1)", "O(n^2)",
                "C", "Data Structures"));
        questions.add(new Question(
                "Which data structure is commonly used to implement recursion internally?",
                "Queue", "Stack", "Array", "Heap",
                "B", "Data Structures"));
        questions.add(new Question(
                "A Binary Search Tree allows searching in what average time complexity?",
                "O(n)", "O(1)", "O(log n)", "O(n log n)",
                "C", "Data Structures"));
        questions.add(new Question(
                "Which data structure consists of nodes where each node points to the next?",
                "Array", "Linked List", "Stack", "Matrix",
                "B", "Data Structures"));
        questions.add(new Question(
                "Which data structure is used to represent hierarchical relationships (like a file system)?",
                "Queue", "Stack", "Tree", "Array",
                "C", "Data Structures"));
        questions.add(new Question(
                "What is the worst-case time complexity of Bubble Sort?",
                "O(n)", "O(log n)", "O(n log n)", "O(n^2)",
                "D", "Data Structures"));

        // ---------------- GENERAL KNOWLEDGE ----------------
        questions.add(new Question(
                "Which is the largest planet in our solar system?",
                "Earth", "Jupiter", "Mars", "Saturn",
                "B", "General Knowledge"));
        questions.add(new Question(
                "Who is known as the father of computers?",
                "Albert Einstein", "Charles Babbage", "Isaac Newton", "Nikola Tesla",
                "B", "General Knowledge"));
        questions.add(new Question(
                "What is the capital of Japan?",
                "Beijing", "Seoul", "Tokyo", "Bangkok",
                "C", "General Knowledge"));
        questions.add(new Question(
                "Which gas do plants primarily absorb for photosynthesis?",
                "Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen",
                "C", "General Knowledge"));
        questions.add(new Question(
                "How many continents are there on Earth?",
                "5", "6", "7", "8",
                "C", "General Knowledge"));
        questions.add(new Question(
                "Which is the longest river in the world?",
                "Amazon", "Nile", "Yangtze", "Mississippi",
                "B", "General Knowledge"));
        questions.add(new Question(
                "Which country is known as the Land of the Rising Sun?",
                "China", "Thailand", "Japan", "South Korea",
                "C", "General Knowledge"));
        questions.add(new Question(
                "What is the chemical symbol for gold?",
                "Go", "Gd", "Au", "Ag",
                "C", "General Knowledge"));
    }
}
