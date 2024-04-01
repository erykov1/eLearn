package erykmarnik.eLearn.userresult.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Builder
@EqualsAndHashCode
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserResultDto {
  UUID id;
  Integer result;
  UUID learningObjectId;
  Instant completedAt;
  Long userId;
  Instant startedAt;
}
