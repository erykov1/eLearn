package erykmarnik.eLearn.userassignation.exception;

public class UserAlreadyAssignedException extends RuntimeException {
  public UserAlreadyAssignedException(String message) {
    super(message);
  }
}
