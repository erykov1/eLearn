package erykmarnik.eLearn.user.exception;

public class NotExistingUserException extends RuntimeException {
  public NotExistingUserException(String message) {
    super(message);
  }
}
