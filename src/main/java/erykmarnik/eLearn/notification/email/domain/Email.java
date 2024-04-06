package erykmarnik.eLearn.notification.email.domain;

import erykmarnik.eLearn.notification.email.dto.EmailDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name = "send_emails")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Email {
  Long emailId;
  Long sendForUser;
  Instant sendAt;

  EmailDto dto() {
    return EmailDto.builder()
        .emailId(emailId)
        .sendForUser(sendForUser)
        .sendAt(sendAt)
        .build();
  }
}
