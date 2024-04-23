package erykmarnik.eLearn.notification.domain;

import erykmarnik.eLearn.utils.InstantProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NotificationConfiguration {
  @Bean
  NotificationFacade notificationFacade(MailSenderHandler mailSenderHandler, NotificationRepository notificationRepository,
                                        CreatedLearningObjectRepository createdLearningObjectRepository,
                                        UsersNewsRepository usersNewsRepository, InstantProvider instantProvider) {
    return NotificationFacade.builder()
        .mailSenderHandler(mailSenderHandler)
        .instantProvider(instantProvider)
        .notificationRepository(notificationRepository)
        .createdLearningObjectRepository(createdLearningObjectRepository)
        .usersNewsRepository(usersNewsRepository)
        .build();
  }

  NotificationFacade notificationFacade(MailSenderHandler mailSenderHandler, InstantProvider instantProvider) {
    return NotificationFacade.builder()
        .mailSenderHandler(mailSenderHandler)
        .instantProvider(instantProvider)
        .notificationRepository(new InMemoryNotificationRepository())
        .createdLearningObjectRepository(new InMemoryCreatedLearningObjectRepository())
        .usersNewsRepository(new InMemoryUsersNewsRepository())
        .build();
  }
}
