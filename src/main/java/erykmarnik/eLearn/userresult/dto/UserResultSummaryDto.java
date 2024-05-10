package erykmarnik.eLearn.userresult.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResultSummaryDto {
  Long timeSpent;
  Instant startedAt;
  Instant completedAt;
  Integer result;
  UUID learningObjectId;
}
