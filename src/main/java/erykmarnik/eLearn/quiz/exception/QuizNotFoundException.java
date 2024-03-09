package erykmarnik.eLearn.quiz.exception;

public class QuizNotFoundException extends RuntimeException {
  public QuizNotFoundException(String message) {
    super(message);
  }
}
