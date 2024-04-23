package erykmarnik.eLearn.notification.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.notification.dto.EnrollForNewsDto
import erykmarnik.eLearn.notification.dto.NotificationStatusDto
import erykmarnik.eLearn.notification.dto.NotificationTypeDto
import erykmarnik.eLearn.notification.dto.UsersNewsDto
import erykmarnik.eLearn.notification.sample.CreatedLearningObjectSample
import erykmarnik.eLearn.notification.sample.NotificationSample
import erykmarnik.eLearn.quiz.acceptance.QuizApiFacade
import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto
import erykmarnik.eLearn.user.acceptance.UserApiFacade
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.user.dto.UserDto
import erykmarnik.eLearn.utils.TimeProviderApiFacade
import erykmarnik.eLearn.utils.samples.InstantSample

class NotificationAcceptanceSpec extends IntegrationSpec implements UserSample, QuizSample, InstantSample, CreatedLearningObjectSample,
    NotificationSample {
  private UserDto jane
  private UserDto carl
  private QuizDto javaQuiz
  NotificationApiFacade notificationApiFacade
  UserApiFacade userApiFacade
  QuizApiFacade quizApiFacade
  TimeProviderApiFacade timeProviderApiFacade

  def setup() {
    notificationApiFacade = new NotificationApiFacade(mockMvc, objectMapper)
    userApiFacade = new UserApiFacade(mockMvc, objectMapper)
    quizApiFacade = new QuizApiFacade(mockMvc, objectMapper)
    timeProviderApiFacade = new TimeProviderApiFacade(mockMvc, objectMapper)

    given: "there is admin $carl and student $jane"
      carl = userApiFacade.registerAdmin(createNewUser(username: "carl", password: "carlPswd", email: "carl@gmail.com"))
      jane = userApiFacade.registerStudent(createNewUser(username: "jane", password: "janePswd", email: "jane@gmail.com"))
    and: "current time is $NOW"
      timeProviderApiFacade.useFixedClock(NOW)
  }

  def "Should send notification if there is new learning object"() {
    given: "$jane enrolled in news"
      notificationApiFacade.enrollForNews(new EnrollForNewsDto(jane.email))
    and: "$carl creates new learning object $javaQuiz"
      javaQuiz = quizApiFacade.createQuiz(createNewQuiz(quizName: "java quiz", createdBy: carl.userId, quizDifficulty: QuizDifficultyDto.BEGINNER))
    when: "sending all pending news to user"
      notificationApiFacade.executeScheduler()
    then: "notification is send to $jane about new learning object"
      def result = notificationApiFacade.getAllNotificationsWithStatus(NotificationStatusDto.SEND)
      equalsNotifications(result, [createNotification(notificationId: result[0].notificationId, sendWhen: null, notificationType: NotificationTypeDto.NEWS,
        sendAt: NOW, userMail: "jane@gmail.com", learningObject: javaQuiz.quizId, notificationStatusDto: NotificationStatusDto.SEND
      )])
  }

  def "Should not send notification if there is no new learning object"() {
    given: "$jane enrolled in news"
      notificationApiFacade.enrollForNews(new EnrollForNewsDto(jane.email))
    when: "sending all pending news to user"
      notificationApiFacade.executeScheduler()
    then: "notification is send to $jane about new learning object"
      notificationApiFacade.getAllNotificationsWithStatus(NotificationStatusDto.SEND) == []
  }

  def "Should send one reminder when current time is in time compartment"() {
    given: "$carl creates new learning object $javaQuiz"
      javaQuiz = quizApiFacade.createQuiz(createNewQuiz(quizName: "java quiz", createdBy: carl.userId, quizDifficulty: QuizDifficultyDto.BEGINNER))
    and: "$jane enroll for reminder to be send $WEEK_LATER"
      notificationApiFacade.createNotification(createNewNotification(sendWhen: WEEK_LATER, notificationType: NotificationTypeDto.REMINDER,
        learningObject: javaQuiz.quizId, userMail: "jane@gmail.com"
      ))
    when: "sending all pending reminders to user $WEEK_LATER"
      timeProviderApiFacade.useFixedClock(WEEK_LATER)
      notificationApiFacade.executeScheduler()
    then: "notification is send to $jane about new learning object"
      def result = notificationApiFacade.getAllNotificationsWithStatus(NotificationStatusDto.SEND)
      equalsNotifications(result, [createNotification(notificationId: result[0].notificationId, sendWhen: WEEK_LATER, notificationType: NotificationTypeDto.REMINDER,
        sendAt: WEEK_LATER, userMail: "jane@gmail.com", learningObject: javaQuiz.quizId, notificationStatusDto: NotificationStatusDto.SEND
      )])
  }

  def "Should not send reminder if current time is not in time compartment"() {
    given: "$carl creates new learning object $javaQuiz"
      javaQuiz = quizApiFacade.createQuiz(createNewQuiz(quizName: "java quiz", createdBy: carl.userId, quizDifficulty: QuizDifficultyDto.BEGINNER))
    and: "$jane enroll for reminder to be send $WEEK_LATER"
      notificationApiFacade.createNotification(createNewNotification(sendWhen: WEEK_LATER, notificationType: NotificationTypeDto.REMINDER,
        learningObject: javaQuiz.quizId, userMail: "jane@gmail.com"
      ))
    when: "sending all pending reminders to users"
      notificationApiFacade.executeScheduler()
    then: "reminder is not send to $jane"
      notificationApiFacade.getAllNotificationsWithStatus(NotificationStatusDto.SEND) == []
  }

  def "Should not send notification if user unroll from news"() {
    given: "$jane enrolled in news"
      UsersNewsDto enrolledUser = notificationApiFacade.enrollForNews(new EnrollForNewsDto(jane.email))
    and: "$jane unroll from news"
      notificationApiFacade.unrollFromNews(enrolledUser.getUsersNewsId())
    when: "sending all pending news to user"
      notificationApiFacade.executeScheduler()
    then: "notification is not send to $jane about new learning object"
      notificationApiFacade.getAllNotificationsWithStatus(NotificationStatusDto.SEND) == []
    and: "there are no pending notifications"
      notificationApiFacade.getAllNotificationsWithStatus(NotificationStatusDto.PENDING) == []
  }

  def "Should send notification before user unroll from news"() {
    given: "$jane enrolled in news"
      UsersNewsDto enrolledUser = notificationApiFacade.enrollForNews(new EnrollForNewsDto(jane.email))
    and: "$carl creates new learning object $javaQuiz"
      javaQuiz = quizApiFacade.createQuiz(createNewQuiz(quizName: "java quiz", createdBy: carl.userId, quizDifficulty: QuizDifficultyDto.BEGINNER))
    and: "sending all pending news to user"
      notificationApiFacade.executeScheduler()
    when: "$jane unrolls from news"
      notificationApiFacade.unrollFromNews(enrolledUser.getUsersNewsId())
    then: "notification is send to $jane about new learning object"
      def result = notificationApiFacade.getAllNotificationsWithStatus(NotificationStatusDto.SEND)
      equalsNotifications(result, [createNotification(notificationId: result[0].notificationId, sendWhen: null, notificationType: NotificationTypeDto.NEWS,
        sendAt: NOW, userMail: "jane@gmail.com", learningObject: javaQuiz.quizId, notificationStatusDto: NotificationStatusDto.SEND
      )])
  }

  def cleanup() {
    notificationApiFacade.cleanup()
    userApiFacade.cleanup()
    quizApiFacade.cleanup()
    timeProviderApiFacade.useSystemClock()
  }
}
