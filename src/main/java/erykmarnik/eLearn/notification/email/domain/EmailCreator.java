package erykmarnik.eLearn.notification.email.domain;

import erykmarnik.eLearn.notification.email.dto.CreateEmailDto;
import erykmarnik.eLearn.notification.email.exception.InvalidEmailDataException;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class EmailCreator {
  InstantProvider instantProvider;

  @Autowired
  public EmailCreator(InstantProvider instantProvider) {
    this.instantProvider = instantProvider;
  }

  Email createEmail(CreateEmailDto createEmail) {
    checkIfContainsInvalidData(createEmail);
    return Email.builder()
        .sendForUser(createEmail.getSendForUser())
        .sendAt(instantProvider.now())
        .build();
  }

  private void checkIfContainsInvalidData(CreateEmailDto createEmail) {
    if (createEmail.getUserEmail() == null || createEmail.getSendForUser() == null) {
      throw new InvalidEmailDataException("Invalid email data");
    }
  }
}
