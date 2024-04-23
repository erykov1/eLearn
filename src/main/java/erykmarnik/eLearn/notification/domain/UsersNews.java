package erykmarnik.eLearn.notification.domain;

import erykmarnik.eLearn.notification.dto.UsersNewsDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "users_news")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class UsersNews {
  @Id
  UUID usersNewsId;
  String userMail;

  UsersNewsDto dto() {
    return new UsersNewsDto(usersNewsId, userMail);
  }
}
