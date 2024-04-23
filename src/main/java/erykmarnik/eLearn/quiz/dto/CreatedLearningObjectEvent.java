package erykmarnik.eLearn.quiz.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreatedLearningObjectEvent {
  UUID learningObjectId;
  String learningObjectName;
  QuizDifficultyDto learningObjectType;
}
