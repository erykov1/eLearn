package erykmarnik.eLearn.notification.email.sample

import erykmarnik.eLearn.notification.email.dto.CreateEmailDto
import erykmarnik.eLearn.notification.email.dto.EmailDto
import java.time.Instant

trait EmailSample {
  private static final EMAIL_ID = 1111L
  static final String SUBJECT = "E-Learn"
  static final String TEXT = "Welcome to E-Learn"

  private static Map<String, Object> DEFAULT_EMAIL_DATA = [
      emailId: EMAIL_ID,
      sendForUser: 1L,
      sendAt: Instant.now()
  ] as Map<String, Object>

  EmailDto createEmail(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_EMAIL_DATA + changes
    return EmailDto.builder()
      .emailId(changesWithDefaults.emailId as Long)
      .sendForUser(changesWithDefaults.sendForUser as Long)
      .sendAt(changesWithDefaults.sendAt as Instant)
      .build()
  }

  void equalsEmails(List<EmailDto> result, List<EmailDto> expected) {
    def comparator = Comparator.comparing(EmailDto::getSendAt)
    result.sort(comparator)
    expected.sort(comparator)
    assert result.emailId == expected.emailId
    assert result.sendForUser == expected.sendForUser
    assert result.sendAt == expected.sendAt
  }

  CreateEmailDto createEmailFor(Long userId, String userEmail) {
    CreateEmailDto.builder()
      .sendForUser(userId)
      .userEmail(userEmail)
      .text(TEXT)
      .subject(SUBJECT)
      .build()
  }
}