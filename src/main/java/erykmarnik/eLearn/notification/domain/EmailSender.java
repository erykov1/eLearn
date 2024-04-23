package erykmarnik.eLearn.notification.domain;

import erykmarnik.eLearn.notification.dto.NotificationDto;
import erykmarnik.eLearn.notification.dto.NotificationTypeDto;

interface EmailSender {
  boolean supports(NotificationTypeDto notificationType);
  NotificationDto sendEmail(NotificationDto notification, String learningObjectName);
}
