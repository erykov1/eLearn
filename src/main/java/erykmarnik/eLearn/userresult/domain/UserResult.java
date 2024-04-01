package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.userresult.dto.UserResultDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_results")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserResult {
  @Id
  UUID id;
  Integer result;
  UUID learningObjectId;
  Instant completedAt;
  Long userId;
  Instant startedAt;

  UserResultDto dto() {
    return UserResultDto.builder()
        .id(id)
        .result(result)
        .learningObjectId(learningObjectId)
        .completedAt(completedAt)
        .userId(userId)
        .startedAt(startedAt)
        .build();
  }
}
