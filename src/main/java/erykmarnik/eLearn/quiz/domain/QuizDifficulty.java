package erykmarnik.eLearn.quiz.domain;

import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto;

enum QuizDifficulty {
  BEGINNER,
  EASY,
  MEDIUM,
  HARD,
  EXPERT;

  QuizDifficultyDto quizDifficultyDto() {
    return QuizDifficultyDto.valueOf(name());
  }
}
