package erykmarnik.eLearn.notification.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateNotificationDto {
  NotificationTypeDto notificationType;
  Instant sendWhen;
  String userMail;
  UUID learningObject;
}
