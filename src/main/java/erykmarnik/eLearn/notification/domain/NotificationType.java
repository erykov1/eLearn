package erykmarnik.eLearn.notification.domain;

import erykmarnik.eLearn.notification.dto.NotificationTypeDto;

enum NotificationType {
  REMINDER,
  NEWS;

  NotificationTypeDto dto() {
    return NotificationTypeDto.valueOf(name());
  }
}
