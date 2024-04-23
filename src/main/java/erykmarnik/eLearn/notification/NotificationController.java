package erykmarnik.eLearn.notification;

import erykmarnik.eLearn.notification.domain.NotificationFacade;
import erykmarnik.eLearn.notification.dto.*;
import erykmarnik.eLearn.notification.scheduler.NotificationScheduler;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notification")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class NotificationController {
  NotificationFacade notificationFacade;
  NotificationScheduler notificationScheduler;

  @Autowired
  public NotificationController(NotificationFacade notificationFacade, NotificationScheduler notificationScheduler) {
    this.notificationFacade = notificationFacade;
    this.notificationScheduler = notificationScheduler;
  }

  @PostMapping("/enroll")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<UsersNewsDto> enrollForNews(@RequestBody EnrollForNewsDto enrollForNews) {
    return ResponseEntity.ok(notificationFacade.enrollUserForNews(enrollForNews.getUserMail()));
  }

  @PostMapping("/reminder")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<NotificationDto> enrollForReminder(@RequestBody CreateNotificationDto notification) {
    return ResponseEntity.ok(notificationFacade.createNotification(notification));
  }

  @GetMapping("/all/{status}")
  @Hidden
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<List<NotificationDto>> getAllNotificationsWithStatus(@PathVariable NotificationStatusDto status) {
    return ResponseEntity.ok(notificationFacade.findNotificationsWithStatus(status));
  }

  @DeleteMapping("/unroll/{userNewsId}")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<Void> unrollFromNews(@PathVariable UUID userNewsId) {
    notificationFacade.unrollUserFromNews(userNewsId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/execute")
  @Hidden
  @PreAuthorize("hasRole('ADMIN')")
  void executeScheduler() {
    notificationScheduler.execute();
  }

  @GetMapping("/cleanup")
  @Hidden
  @PreAuthorize("hasRole('ADMIN')")
  void cleanup() {
    notificationFacade.cleanup();
  }
}
