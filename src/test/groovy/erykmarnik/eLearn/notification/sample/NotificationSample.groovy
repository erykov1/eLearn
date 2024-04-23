package erykmarnik.eLearn.notification.sample

import erykmarnik.eLearn.notification.dto.CreateNotificationDto
import erykmarnik.eLearn.notification.dto.NotificationDto
import erykmarnik.eLearn.notification.dto.NotificationStatusDto
import erykmarnik.eLearn.notification.dto.NotificationTypeDto

import java.time.Instant

trait NotificationSample {
  private static final NOTIFICATION_ID = 1234L

  private static Map<String, Object> DEFAULT_NOTIFICATION_DATA = [
      notificationId    : NOTIFICATION_ID,
      sendWhen          : Instant.now(),
      notificationType  : NotificationTypeDto.NEWS,
      sendAt            : Instant.now(),
      userMail          : "user@gmail.com",
      learningObject    : UUID.fromString("3930fc0b-c13c-4835-8bba-47143821ce85"),
      notificationStatus: NotificationStatusDto.SEND
  ] as Map<String, Object>

  CreateNotificationDto createNewNotification(Map<String, Object> changes = [:]) {
    def changedWithDefaults = DEFAULT_NOTIFICATION_DATA + changes
    CreateNotificationDto.builder()
      .sendWhen(changedWithDefaults.sendWhen as Instant)
      .notificationType(changedWithDefaults.notificationType as NotificationTypeDto)
      .userMail(changedWithDefaults.userMail as String)
      .learningObject(changedWithDefaults.learningObject as UUID)
      .build()
  }

  NotificationDto createNotification(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_NOTIFICATION_DATA + changes
    NotificationDto.builder()
      .notificationId(changesWithDefaults.notificationId as Long)
      .sendWhen(changesWithDefaults.sendWhen as Instant)
      .notificationType(changesWithDefaults.notificationType as NotificationTypeDto)
      .sendAt(changesWithDefaults.sendAt as Instant)
      .userMail(changesWithDefaults.userMail as String)
      .learningObject(changesWithDefaults.learningObject as UUID)
      .notificationStatusDto(changesWithDefaults.notificationStatus as NotificationStatusDto)
      .build()
  }

  void equalsNotifications(List<NotificationDto> result, List<NotificationDto> expected) {
    def comparator = Comparator.comparing(NotificationDto::getSendAt)
    result.sort(comparator)
    expected.sort(comparator)
    assert result.notificationId == expected.notificationId
    assert result.sendWhen == expected.sendWhen
    assert result.notificationType == expected.notificationType
    assert result.sendAt == expected.sendAt
    assert result.userMail == expected.userMail
    assert result.learningObject == expected.learningObject
    assert result.notificationStatusDto == expected.notificationStatusDto
  }
}