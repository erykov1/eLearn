package erykmarnik.eLearn.quiz.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizDto {
  UUID quizId;
  String quizName;
  Instant createdAt;
  Long createdBy;
  QuizDifficultyDto quizDifficulty;
}
