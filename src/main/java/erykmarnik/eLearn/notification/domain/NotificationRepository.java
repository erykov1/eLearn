package erykmarnik.eLearn.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface NotificationRepository extends JpaRepository<Notification, Long> {
  @Query("SELECT n FROM Notification n WHERE n.notificationStatus = PENDING AND n.notificationType = NEWS")
  List<Notification> findAllPendingNews();

  @Query("SELECT n FROM Notification n WHERE n.notificationStatus = PENDING AND n.notificationType = REMINDER")
  List<Notification> findAllPendingReminders();
}
