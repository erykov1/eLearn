package erykmarnik.eLearn.notification.domain;

import erykmarnik.eLearn.notification.dto.CreateNotificationDto;

class NotificationCreator {
  static Notification createNotification(CreateNotificationDto createNotification) {
    return Notification.builder()
        .notificationType(NotificationType.valueOf(createNotification.getNotificationType().name()))
        .sendWhen(createNotification.getSendWhen())
        .userMail(createNotification.getUserMail())
        .learningObject(createNotification.getLearningObject())
        .notificationStatus(NotificationStatus.PENDING)
        .build();
  }
}
