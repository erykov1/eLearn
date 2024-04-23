package erykmarnik.eLearn.notification.scheduler;

import erykmarnik.eLearn.notification.domain.NotificationFacade;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationScheduler {
  NotificationFacade notificationFacade;
  Environment environment;

  @Autowired
  public NotificationScheduler(NotificationFacade notificationFacade, Environment environment) {
    this.notificationFacade = notificationFacade;
    this.environment = environment;
    if (!isEnabled()) {
      log.warn("Notification scheduler is disabled");
    } else {
      log.warn("Notification scheduler is enabled");
    }
  }

  @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 120, initialDelay = 30)
  public void execute() {
    if (isEnabled()) {
      notificationFacade.sendReminders();
      notificationFacade.sendAllPendingNews();
    }
  }

  private boolean isEnabled() {
    return environment.getProperty("notification.scheduler.enabled", Boolean.class, true);
  }
}
