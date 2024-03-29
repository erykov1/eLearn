package erykmarnik.eLearn.quiz.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateQuizDto {
  String quizName;
  Long createdBy;
  QuizDifficultyDto quizDifficulty;
}
