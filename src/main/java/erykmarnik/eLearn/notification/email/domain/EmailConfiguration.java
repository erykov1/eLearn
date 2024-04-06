package erykmarnik.eLearn.notification.email.domain;

import erykmarnik.eLearn.utils.InstantProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

@Configuration
class EmailConfiguration {
  @Bean
  EmailFacade emailFacade(EmailRepository emailRepository, MailSender mailSender, InstantProvider instantProvider) {
    return EmailFacade.builder()
        .emailRepository(emailRepository)
        .mailSender(mailSender)
        .emailCreator(new EmailCreator(instantProvider))
        .build();
  }

  EmailFacade emailFacade(MailSender mailSender, InstantProvider instantProvider) {
    return EmailFacade.builder()
        .emailRepository(new InMemoryEmailRepository())
        .mailSender(mailSender)
        .emailCreator(new EmailCreator(instantProvider))
        .build();
  }
}
