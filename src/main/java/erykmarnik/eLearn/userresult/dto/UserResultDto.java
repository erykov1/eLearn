package erykmarnik.eLearn.userresult.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Builder
@EqualsAndHashCode
@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserResultDto {
  UUID id;
  Integer result;
  UUID learningObjectId;
  Instant completedAt;
  Long userId;
  Instant startedAt;
  UserResultVisibilityTypeDto userResultVisibilityType;
}
