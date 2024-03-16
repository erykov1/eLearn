package erykmarnik.eLearn.userassignation.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAssignationDto {
  Long id;
  Long userId;
  UUID learningObjectId;
  Instant assignedAt;
  Instant completedAt;
  UserAssignationStatusDto userAssignationStatus;
}
