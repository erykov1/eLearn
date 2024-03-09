package erykmarnik.eLearn.quizassignation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeleteQuizAssignationDto {
  UUID quizId;
  Long questionId;
}
