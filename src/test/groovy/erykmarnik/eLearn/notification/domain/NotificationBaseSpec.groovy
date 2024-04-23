package erykmarnik.eLearn.notification.domain

import erykmarnik.eLearn.notification.sample.CreatedLearningObjectSample
import erykmarnik.eLearn.notification.sample.NotificationSample
import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.utils.InstantProvider
import erykmarnik.eLearn.utils.samples.InstantSample
import org.springframework.mail.MailSender
import spock.lang.Specification

class NotificationBaseSpec extends Specification implements NotificationSample, InstantSample, QuizSample, CreatedLearningObjectSample {
  MailSender mailSender = Mock()
  InstantProvider instantProvider = new InstantProvider()
  MailSenderHandler mailSenderHandler = new MailSenderHandler(instantProvider, mailSender)
  NotificationFacade notificationFacade = new NotificationConfiguration().notificationFacade(mailSenderHandler, instantProvider)
}
