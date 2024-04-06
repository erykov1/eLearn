package erykmarnik.eLearn.notification.email

import erykmarnik.eLearn.notification.email.domain.EmailConfiguration
import erykmarnik.eLearn.notification.email.domain.EmailFacade
import erykmarnik.eLearn.notification.email.sample.EmailSample
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.utils.InstantProvider
import erykmarnik.eLearn.utils.samples.InstantSample
import org.springframework.mail.MailSender
import spock.lang.Specification
import erykmarnik.eLearn.notification.email.exception.InvalidEmailDataException

class EmailSpec extends Specification implements InstantSample, EmailSample, UserSample {
  InstantProvider instantProvider = new InstantProvider()
  MailSender mailSender = Mock()
  EmailFacade emailFacade = new EmailConfiguration().emailFacade(mailSender, instantProvider)

  def setup() {
    given: "current time is $NOW"
      instantProvider.useFixedClock(NOW)
  }

  def "Should send email to user"() {
    when: "send email to user $JAMIE"
      emailFacade.sendEmail(createEmailFor(JAMIE.userId, JAMIE.email))
    then: "email is send with status 'SEND'"
      def result = emailFacade.getAllEmails()
      equalsEmails(result, [createEmail(emailId: result[0].emailId, sendForUser: JAMIE.userId, sendAt: instantProvider.now()
      )])
  }

  def "Should get error if try to send message to empty email address"() {
    when: "send email to user with invalid field"
      emailFacade.sendEmail(createEmailFor(userId, email))
    then: "gets error of invalid data"
      thrown(InvalidEmailDataException)
    where:
      userId       | email
      null         | null
      JAMIE.userId | null
      null         | JAMIE.email
  }
}
