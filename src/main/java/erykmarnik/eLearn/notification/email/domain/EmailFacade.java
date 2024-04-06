package erykmarnik.eLearn.notification.email.domain;

import erykmarnik.eLearn.notification.email.dto.CreateEmailDto;
import erykmarnik.eLearn.notification.email.dto.EmailDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailFacade {
  EmailRepository emailRepository;
  MailSender mailSender;
  EmailCreator emailCreator;

  @Async
  public void sendEmail(CreateEmailDto createEmail) {
    Email email = emailCreator.createEmail(createEmail);
    try {
      log.info("sending email");
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setFrom(System.getenv("spring.mail.username"));
      mailMessage.setTo(createEmail.getUserEmail());
      mailMessage.setText(createEmail.getText());
      mailMessage.setSubject(createEmail.getSubject());
      mailSender.send(mailMessage);
      emailRepository.save(email);
    } catch (Exception e) {
      log.error("email was not send");
      e.printStackTrace();
    }
  }

  public List<EmailDto> getAllEmails() {
    return emailRepository.findAll().stream()
        .map(Email::dto)
        .collect(Collectors.toList());
  }
}
