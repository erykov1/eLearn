package erykmarnik.eLearn.notification.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationDto {
  Long notificationId;
  Instant sendWhen;
  NotificationTypeDto notificationType;
  Instant sendAt;
  String userMail;
  UUID learningObject;
  NotificationStatusDto notificationStatusDto;
}
