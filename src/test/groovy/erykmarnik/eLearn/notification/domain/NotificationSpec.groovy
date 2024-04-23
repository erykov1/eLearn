package erykmarnik.eLearn.notification.domain

import erykmarnik.eLearn.notification.dto.NotificationStatusDto
import erykmarnik.eLearn.notification.dto.NotificationTypeDto
import erykmarnik.eLearn.notification.dto.UsersNewsDto


class NotificationSpec extends NotificationBaseSpec {
  def setup() {
    given: "current time is $NOW"
      instantProvider.useFixedClock(NOW)
  }

  def "Should send notification if there is new learning object"() {
    given: "jane enrolled in news"
      notificationFacade.enrollUserForNews("jane@gmail.com")
    and: "admin creates new learning object"
      notificationFacade.onCreatedLearningObject(createLearningObjectEvent(learningObjectName: "Java quiz"))
    when: "sending all pending news to user"
      notificationFacade.sendAllPendingNews()
    then: "notification is send to user"
      def result = notificationFacade.findNotifications()
      equalsNotifications(result, [createNotification(notificationId: result[0].notificationId, sendWhen: null, sendAt: NOW, notificationType: NotificationTypeDto.NEWS, userMail: "jane@gmail.com",
        learningObject: CREATED_LEARNING_OBJECT_ID, notificationStatusDto: NotificationStatusDto.SEND
      )])
  }

  def "Should not send notification if there are no new learning object"() {
    given: "jane enrolled in news"
      notificationFacade.enrollUserForNews("jane@gmail.com")
    expect: "there is no send notifications to user"
      notificationFacade.findNotifications() == []
  }

  def "Should send reminder"() {
    given: "admin creates new learning object"
      notificationFacade.onCreatedLearningObject(createLearningObjectEvent(learningObjectName: "Java quiz"))
    and: "jane enrolled in reminder for learning object"
      notificationFacade.createNotification(createNewNotification(sendWhen: NOW, notificationType: NotificationTypeDto.REMINDER,
          userMail: "jane@gmail.com", learningObject: CREATED_LEARNING_OBJECT_ID))
    when: "sending all pending reminders"
      notificationFacade.sendReminders()
    then: "reminder is sent"
      def result = notificationFacade.findNotifications()
      equalsNotifications(result, [createNotification(notificationId: result[0].notificationId, sendWhen: NOW, notificationType: NotificationTypeDto.REMINDER,
        sendAt: NOW, userMail: "jane@gmail.com", learningObject: CREATED_LEARNING_OBJECT_ID, notificationStatusDto: NotificationStatusDto.SEND
      )])
  }

  def "Should not send reminder if reminder was sent already"() {
    given: "admin creates new learning object"
      notificationFacade.onCreatedLearningObject(createLearningObjectEvent(learningObjectName: "Java quiz"))
    and: "jane enrolled in reminder for learning object"
      notificationFacade.createNotification(createNewNotification(sendWhen: NOW, notificationType: NotificationTypeDto.REMINDER,
          userMail: "jane@gmail.com", learningObject: CREATED_LEARNING_OBJECT_ID))
    and: "send pending reminders"
      notificationFacade.sendReminders()
    when: "send pending reminders again"
      notificationFacade.sendReminders()
    then: "there was only one reminder sent"
      def result = notificationFacade.findNotifications()
      equalsNotifications(result, [createNotification(notificationId: result[0].notificationId, sendWhen: NOW, notificationType: NotificationTypeDto.REMINDER,
          sendAt: NOW, userMail: "jane@gmail.com", learningObject: CREATED_LEARNING_OBJECT_ID, notificationStatusDto: NotificationStatusDto.SEND
      )])
  }

  def "Should not send news if news were sent already"() {
    given: "jane enrolled in news"
      notificationFacade.enrollUserForNews("jane@gmail.com")
    and: "admin creates new learning object"
      notificationFacade.onCreatedLearningObject(createLearningObjectEvent(learningObjectName: "Java quiz"))
    and: "send pending news"
      notificationFacade.sendAllPendingNews()
    when: "sending pending news again"
      notificationFacade.sendAllPendingNews()
    then: "there was only one news sent"
      def result = notificationFacade.findNotifications()
      equalsNotifications(result, [createNotification(notificationId: result[0].notificationId, sendWhen: null, sendAt: NOW, notificationType: NotificationTypeDto.NEWS, userMail: "jane@gmail.com",
          learningObject: CREATED_LEARNING_OBJECT_ID, notificationStatusDto: NotificationStatusDto.SEND
      )])
  }

  def "Should not send notification if user unrolled from news"() {
    given: "jane enrolled in news"
      UsersNewsDto janeNews = notificationFacade.enrollUserForNews("jane@gmail.com")
    and: "jane unrolled from news"
      notificationFacade.unrollUserFromNews(janeNews.usersNewsId)
    and: "admin creates new learning object"
      notificationFacade.onCreatedLearningObject(createLearningObjectEvent(learningObjectName: "Java quiz"))
    when: "sending all pending news to user"
      notificationFacade.sendAllPendingNews()
    then: "there are no send news"
      notificationFacade.findNotifications() == []
  }

  def "Should send notification before user unroll from news"() {
    given: "jane enrolled in news"
      UsersNewsDto janeNews = notificationFacade.enrollUserForNews("jane@gmail.com")
    and: "admin creates new learning object"
      notificationFacade.onCreatedLearningObject(createLearningObjectEvent(learningObjectName: "Java quiz"))
    and: "send all pending news to user"
      notificationFacade.sendAllPendingNews()
    when: "jane unrolled from news"
      notificationFacade.unrollUserFromNews(janeNews.usersNewsId)
    then: "notification is send to user"
      def result = notificationFacade.findNotifications()
      equalsNotifications(result, [createNotification(notificationId: result[0].notificationId, sendWhen: null, sendAt: NOW, notificationType: NotificationTypeDto.NEWS, userMail: "jane@gmail.com",
          learningObject: CREATED_LEARNING_OBJECT_ID, notificationStatusDto: NotificationStatusDto.SEND
      )])
  }
}
