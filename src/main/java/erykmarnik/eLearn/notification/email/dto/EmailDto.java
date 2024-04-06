package erykmarnik.eLearn.notification.email.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailDto {
  Long emailId;
  Long sendForUser;
  Instant sendAt;
}
