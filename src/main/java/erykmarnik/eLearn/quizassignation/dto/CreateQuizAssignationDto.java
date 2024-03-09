package erykmarnik.eLearn.quizassignation.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateQuizAssignationDto {
  UUID quizId;
  Long questionId;
  Long assignedBy;
}
