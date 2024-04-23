package erykmarnik.eLearn.notification.domain;

import erykmarnik.eLearn.notification.dto.NotificationDto;
import erykmarnik.eLearn.notification.exception.InvalidTypeException;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class MailSenderHandler {
  private final List<EmailSender> mailSenderHandlers;

  @Autowired
  public MailSenderHandler(InstantProvider instantProvider, MailSender mailSender) {
    this.mailSenderHandlers = List.of(new MailReminderSender(mailSender, instantProvider), new MailNewsSender(mailSender, instantProvider));
  }

  NotificationDto sendNotification(NotificationDto notification, String learningObjectName) {
    EmailSender emailSenderHandler = mailSenderHandlers.stream()
        .filter(handler -> handler.supports(notification.getNotificationType()))
        .findFirst()
        .orElseThrow(() -> new InvalidTypeException("Invalid type to process"));
    return emailSenderHandler.sendEmail(notification, learningObjectName);
  }
}
