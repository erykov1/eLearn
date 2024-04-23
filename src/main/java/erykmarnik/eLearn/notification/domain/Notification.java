package erykmarnik.eLearn.notification.domain;

import erykmarnik.eLearn.notification.dto.NotificationDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_sequence")
  @SequenceGenerator(name = "notification_sequence", sequenceName = "notification_sequence", allocationSize = 1)
  Long notificationId;
  Instant sendWhen;
  @Enumerated(EnumType.STRING)
  NotificationType notificationType;
  Instant sendAt;
  String userMail;
  UUID learningObject;
  @Enumerated(EnumType.STRING)
  NotificationStatus notificationStatus;

  NotificationDto dto() {
    return NotificationDto.builder()
        .notificationId(notificationId)
        .sendWhen(sendWhen)
        .notificationType(notificationType.dto())
        .sendAt(sendAt)
        .userMail(userMail)
        .learningObject(learningObject)
        .notificationStatusDto(notificationStatus.statusDto())
        .build();
  }

  Notification update(NotificationDto notification) {
    return toBuilder()
        .sendAt(notification.getSendAt())
        .notificationStatus(NotificationStatus.valueOf(notification.getNotificationStatusDto().name()))
        .build();
  }
}
