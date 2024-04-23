package erykmarnik.eLearn.notification.domain;

import erykmarnik.eLearn.notification.dto.NotificationDto;
import erykmarnik.eLearn.notification.dto.NotificationStatusDto;
import erykmarnik.eLearn.notification.dto.NotificationTypeDto;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class MailReminderSender implements EmailSender {
  private final static String SUBJECT = "Reminder for you";
  private final static String TEXT = "Hello! Remember to start you learning object : ";
  MailSender mailSender;
  InstantProvider instantProvider;

  @Autowired
  public MailReminderSender(MailSender mailSender, InstantProvider instantProvider) {
    this.mailSender = mailSender;
    this.instantProvider = instantProvider;
  }

  @Override
  public boolean supports(NotificationTypeDto notificationType) {
    return notificationType.equals(NotificationTypeDto.REMINDER);
  }

  @Override
  @Async
  public NotificationDto sendEmail(NotificationDto notification, String learningObjectName) {
    try {
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setFrom(System.getenv("spring.mail.username"));
      mailMessage.setTo(notification.getUserMail());
      mailMessage.setText(TEXT + learningObjectName);
      mailMessage.setSubject(SUBJECT);
      mailSender.send(mailMessage);
      notification = notification.toBuilder()
          .sendAt(instantProvider.now())
          .notificationStatusDto(NotificationStatusDto.SEND)
          .build();
    } catch (Exception e) {
      notification = notification.toBuilder()
          .notificationStatusDto(NotificationStatusDto.ERROR)
          .build();
    }
    return notification;
  }
}
