package erykmarnik.eLearn.notification.email.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateEmailDto {
  Long sendForUser;
  String userEmail;
  String text;
  String subject;
}
