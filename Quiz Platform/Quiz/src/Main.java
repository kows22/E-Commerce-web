import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors; 

class Question {
    String question;
    String[] options;
    int correctOption;

    public Question(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }
}

public class Main {
    static final int MAX_QUESTIONS = 5;
    static final String PASSWORD = "password123";
    static final String SCORE_FILE = "scorefile.txt";

    // function to display question to the user
    static void displayQuestion(Question q) {
        System.out.println(q.question);
        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + ". " + q.options[i]);
        }
    }

    // function to check the answer
    static boolean checkAnswer(Question q, int userAnswer) {
        return userAnswer == q.correctOption;
    }

    // function to authenticate user
    static boolean authenticateUser(String username, String password) {
        return password.equals(PASSWORD);
    }

    // function to read user score from file
    static int readUserScore(String username) throws IOException {
        try {
            String content = new String(Files.readAllBytes(Paths.get(SCORE_FILE)));
            String[] lines = content.split("\n");
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return Integer.parseInt(parts[1]);
                }
            }
        } catch (NoSuchFileException e) {
            // Create the file if it doesn't exist
            Files.createFile(Paths.get(SCORE_FILE));
        }
        return 0;
    }

    // function to update user score in file
    static void updateUserScore(String username, int score) throws IOException {
        FileWriter writer = new FileWriter(SCORE_FILE, true);
        writer.write(username + "," + score + "\n");
        writer.close();
    }

    // function to display leaderboard
    static void displayLeaderboard() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(SCORE_FILE));
        Map<String, Integer> scores = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            String username = parts[0];
            int score = Integer.parseInt(parts[1]);
            scores.put(username, score);
        }
        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        System.out.println("\nTop Scores:");
        for (Map.Entry<String, Integer> entry : sortedScores) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (!authenticateUser(username, password)) {
            System.out.println("Invalid credentials. Exiting program.");
            return;
        }

        Random rand = new Random();

        Question[] originalQuestions = {
                new Question("Which bird lays the largest egg?",
                        new String[] { "Owl", "Ostrich", "Kingfisher", "Woodpecker" },
                        2),
                new Question("How many legs does a spider have?",
                        new String[] { "7", "8", "6", "5" },
                        2),
                new Question("Where does the President of the United States "
                        + "live while in office?",
                        new String[] { "The White House", "The Parliament",
                                "House of Commons", "Washington DC" },
                        1),
                new Question("Which state is famous for Hollywood?",
                        new String[] { "Sydney", "California", "New York", "Paris" },
                        2),
                new Question("What is a group of lions called?",
                        new String[] { "Drift", "Pride", "Flock", "Drove" },
                        2)
        };

        Question[] questions = originalQuestions.clone();
        int numQuestions = MAX_QUESTIONS;
        int score = 0;
        System.out.println("Hola! Here's your Quiz Game!\n");

        for (int i = 0; i < MAX_QUESTIONS; i++) {
            int randomIndex = rand.nextInt(numQuestions);
            Question currentQuestion = questions[randomIndex];
            displayQuestion(currentQuestion);

            int userAnswer;
            System.out.print("Enter your answer (1-4): ");
            userAnswer = scanner.nextInt();

            if (userAnswer >= 1 && userAnswer <= 4) {
                if (checkAnswer(currentQuestion, userAnswer)) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Incorrect. The correct answer is "
                            + currentQuestion.correctOption + ". "
                            + currentQuestion.options[currentQuestion.correctOption - 1]);
                }
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }

            questions[randomIndex] = questions[numQuestions - 1];
            numQuestions--;
        }
        System.out.println("Well Done Champ !!!! Quiz completed! Your score: " + score + "/" + MAX_QUESTIONS);
        try {
            int userScore = readUserScore(username);
            if (score > userScore) {
                updateUserScore(username, score);
            }
            displayLeaderboard();
        } catch (IOException e) {
            System.out.println("Error updating user score: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
}