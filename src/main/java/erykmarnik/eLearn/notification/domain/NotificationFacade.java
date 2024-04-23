package erykmarnik.eLearn.notification.domain;

import erykmarnik.eLearn.notification.dto.*;
import erykmarnik.eLearn.notification.exception.LearningObjectNotFoundException;
import erykmarnik.eLearn.notification.exception.NotificationNotFoundException;
import erykmarnik.eLearn.quiz.dto.ChangedDifficultyEvent;
import erykmarnik.eLearn.quiz.dto.ChangedLearningObjectNameEvent;
import erykmarnik.eLearn.quiz.dto.CreatedLearningObjectEvent;
import erykmarnik.eLearn.quiz.exception.QuizNotFoundException;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationFacade {
  MailSenderHandler mailSenderHandler;
  NotificationRepository notificationRepository;
  CreatedLearningObjectRepository createdLearningObjectRepository;
  InstantProvider instantProvider;
  UsersNewsRepository usersNewsRepository;
  private static final Integer TIME_TO_SEND = 5;

  public NotificationDto createNotification(CreateNotificationDto createNotification) {
    log.info("creating notification with type " + createNotification.getNotificationType());
    return notificationRepository.save(NotificationCreator.createNotification(createNotification)).dto();
  }

  public UsersNewsDto enrollUserForNews(String userMail) {
    log.info("User with email {} is enrolled to news", userMail);
    return usersNewsRepository.save(UsersNewsCreator.createUsersNews(userMail)).dto();
  }

  public void unrollUserFromNews(UUID userNewsId) {
    log.info("User unrolled from news {}", userNewsId);
    usersNewsRepository.deleteById(userNewsId);
  }

  public void sendAllPendingNews() {
    List<NotificationDto> pendingNews = notificationRepository.findAllPendingNews().stream()
        .map(Notification::dto)
        .toList();
    log.info("sending all pending news: {}", pendingNews.size());
    pendingNews.forEach(news -> sendNotification(news, getLearningObjectNameFromNotification(news)));
  }

  public void sendReminders() {
    List<NotificationDto> reminders = notificationRepository.findAllPendingReminders()
        .stream()
        .map(Notification::dto)
        .toList();
    reminders.forEach(notification -> {
      long minutesUntilSend = Duration.between(instantProvider.now(), notification.getSendWhen()).toMinutes();
      if (minutesUntilSend <= TIME_TO_SEND) {
        sendNotification(notification, getLearningObjectNameFromNotification(notification));
      }
    });
  }

  public List<NotificationDto> findNotifications() {
    return notificationRepository.findAll().stream()
        .map(Notification::dto)
        .collect(Collectors.toList());
  }

  public List<NotificationDto> findNotificationsWithStatus(NotificationStatusDto status) {
    return notificationRepository.findAll().stream()
        .filter(notification -> notification.dto().getNotificationStatusDto().equals(status))
        .map(Notification::dto)
        .collect(Collectors.toList());
  }

  @EventListener
  public void onCreatedLearningObject(CreatedLearningObjectEvent createdLearningObjectEvent) {
    CreatedLearningObject createdLearningObject = CreatedLearningObject.builder()
        .learningObjectId(createdLearningObjectEvent.getLearningObjectId())
        .learningObjectName(createdLearningObjectEvent.getLearningObjectName())
        .learningObjectType(LearningObjectType.valueOf(createdLearningObjectEvent.getLearningObjectType().name()))
        .build();
    log.info("Creating news with new learning object to send {}", createdLearningObject.getLearningObjectId());
    onNewLearningObjectNotifications(createdLearningObject);
    createdLearningObjectRepository.save(createdLearningObject);
  }

  @EventListener
  public void onChangedDifficulty(ChangedDifficultyEvent changedDifficultyEvent) {
    CreatedLearningObject learningObject = createdLearningObjectRepository.findById(changedDifficultyEvent.getLearningObjectId())
       .orElseThrow(() -> new LearningObjectNotFoundException("Learning object not found"));
    createdLearningObjectRepository.save(learningObject.changeDifficulty(LearningObjectType.valueOf(changedDifficultyEvent.getNewDifficulty().name())));
  }

  @EventListener
  public void onChangedLearningObjectName(ChangedLearningObjectNameEvent learningObjectNameEvent) {
    CreatedLearningObject learningObject = createdLearningObjectRepository.findById(learningObjectNameEvent.getLearningObjectId())
        .orElseThrow(() -> new LearningObjectNotFoundException("Learning object not found"));
    createdLearningObjectRepository.save(learningObject.changeLearningObjectName(learningObjectNameEvent.getNewLearningObjectName()));
  }

  private void sendNotification(NotificationDto notification, String learningObjectName) {
    log.info("sending notification with type " + notification.getNotificationType());
    Notification notificationEntity = notificationRepository.findById(notification.getNotificationId())
        .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));
    NotificationDto sendNotification = mailSenderHandler.sendNotification(notification, learningObjectName);
    notificationRepository.save(notificationEntity.update(sendNotification));
  }

  private String getLearningObjectNameFromNotification(NotificationDto notification) {
    return createdLearningObjectRepository.findById(notification.getLearningObject())
        .map(CreatedLearningObject::getLearningObjectName)
        .orElseThrow(() -> new QuizNotFoundException("Learning object not found"));
  }

  private void onNewLearningObjectNotifications(CreatedLearningObject createdLearningObject) {
    List<Notification> createdNotifications = getAllUsersNews()
        .stream()
        .map(notification -> NotificationCreator.createNotification(
            CreateNotificationDto.builder()
                .learningObject(createdLearningObject.getLearningObjectId())
                .notificationType(NotificationTypeDto.NEWS)
                .userMail(notification.dto().getUserMail())
                .build()
        )).toList();
    notificationRepository.saveAll(createdNotifications);
  }

  private List<UsersNews> getAllUsersNews() {
    return usersNewsRepository.findAll();
  }

  public void cleanup() {
    usersNewsRepository.deleteAll();
    notificationRepository.deleteAll();
  }
}
