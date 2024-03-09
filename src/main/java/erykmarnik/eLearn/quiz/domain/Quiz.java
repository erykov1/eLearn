package erykmarnik.eLearn.quiz.domain;

import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto;
import erykmarnik.eLearn.quiz.dto.QuizDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "quizzes")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Quiz {
  @Id
  UUID quizId;
  String quizName;
  Instant createdAt;
  Long createdBy;
  @Enumerated(EnumType.STRING)
  QuizDifficulty quizDifficulty;

  QuizDto dto() {
    return QuizDto.builder()
        .quizId(quizId)
        .quizName(quizName)
        .createdAt(createdAt)
        .createdBy(createdBy)
        .quizDifficulty(quizDifficulty.quizDifficultyDto())
        .build();
  }

  Quiz changeQuizDifficulty(QuizDifficultyDto quizDifficulty) {
    return this.toBuilder()
        .quizDifficulty(QuizDifficulty.valueOf(quizDifficulty.name()))
        .build();
  }
}
