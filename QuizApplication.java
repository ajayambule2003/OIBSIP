
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApplication {

    static class Question {
        String question; 
        String[] options;  
        char correctAnswer;

        Question(String question, String[] options, char correctAnswer) {
            this.question = question;
            this.options =  options;
            this.correctAnswer = correctAnswer; 
        } 
    }

    static class Quiz {
        private Question[] questions;
        private int score = 0;
        private int currentQuestionIndex = 0;
        private boolean answerSubmitted = false;
        private char userAnswer;
        private Timer timer;

        Quiz(Question[] questions) {
            this.questions = questions;
        }

        public void start() {
            Scanner scanner = new Scanner(System.in);
            while (currentQuestionIndex < questions.length) {
                displayQuestion(questions[currentQuestionIndex]);
                startTimer();

                System.out.print("Enter your answer (A/B/C/D): ");
                userAnswer = scanner.next().toUpperCase().charAt(0);
                answerSubmitted = true;

                if (userAnswer == questions[currentQuestionIndex].correctAnswer) {
                    score++;
                }
                currentQuestionIndex++;
                stopTimer();
            }
            displayResult();
            scanner.close();
        }

        private void displayQuestion(Question question) {
            System.out.println(question.question);
            for (int i = 0; i < question.options.length; i++) {
                System.out.println((char) ('A' + i) + ": " + question.options[i]);
            }
        }

        private void startTimer() {
            answerSubmitted = false;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!answerSubmitted) {
                        System.out.println("\nTime is up!");
                        currentQuestionIndex++;
                    }
                }
            }, 10000); // 10 seconds for each question
        }

        private void stopTimer() {
            timer.cancel();
        }

        private void displayResult() {
            System.out.println("\nQuiz Completed !");
            System.out.println("Your score: " + score + "/" + questions.length);
            System.out.println("Summary of answers:");
            for (int i = 0; i < questions.length; i++) {
                System.out.println(
                        "Q" + (i + 1) + ": " + (userAnswer == questions[i].correctAnswer ? "Correct" : "Incorrect"));
            }
        }
    }

    public static void main(String[] args) {
        Question[] questions = new Question[] {
                new Question("What is the capital of France?", new String[] { "Paris", "London", "Berlin", "Madrid" },
                        'A'),
                new Question("What is 2 + 2?", new String[] { "3", "4", "5", "6" }, 'B'),
                new Question("Which planet is known as the Red Planet?",
                        new String[] { "Earth", "Mars", "Jupiter", "Saturn" }, 'B'),
                new Question("Who wrote 'To be, or not to be'?",
                        new String[] { "Shakespeare", "Hemingway", "Fitzgerald", "Tolkien" }, 'A')
        };

        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}
