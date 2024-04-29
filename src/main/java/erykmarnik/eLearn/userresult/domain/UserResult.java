package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.userresult.dto.UserResultDto;
import erykmarnik.eLearn.userresult.dto.UserResultVisibilityTypeDto;
import jakarta.persistence.*;
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
  @Enumerated(EnumType.STRING)
  UserResultVisibilityType userResultVisibilityType;

  UserResultDto dto() {
    return UserResultDto.builder()
        .id(id)
        .result(result)
        .learningObjectId(learningObjectId)
        .completedAt(completedAt)
        .userId(userId)
        .startedAt(startedAt)
        .userResultVisibilityType(userResultVisibilityType.dto())
        .build();
  }

  UserResult changeVisibility(UserResultVisibilityTypeDto userResultVisibilityType) {
    return toBuilder()
        .userResultVisibilityType(UserResultVisibilityType.valueOf(userResultVisibilityType.name()))
        .build();
  }
}
