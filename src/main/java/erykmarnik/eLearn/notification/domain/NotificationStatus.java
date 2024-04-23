package erykmarnik.eLearn.notification.domain;

import erykmarnik.eLearn.notification.dto.NotificationStatusDto;

enum NotificationStatus {
  PENDING,
  SEND,
  ERROR;

  NotificationStatusDto statusDto() {
    return NotificationStatusDto.valueOf(name());
  }
}
