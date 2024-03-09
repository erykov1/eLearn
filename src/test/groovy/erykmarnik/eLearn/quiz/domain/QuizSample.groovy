package erykmarnik.eLearn.quiz.domain

import erykmarnik.eLearn.quiz.dto.CreateQuizDto
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto

import java.time.Instant

trait QuizSample {
  private static Map<String, Object> DEFAULT_QUIZ_DATA = [
      quizId: UUID.fromString("f5909396-2cf9-4a7c-9de5-606914ea6900"),
      quizName: "Java quiz",
      createdAt: Instant.now(),
      createdBy: 1L,
      quizDifficulty: QuizDifficultyDto.BEGINNER
  ] as Map<String, Object>

  QuizDto createQuiz(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_QUIZ_DATA + changes
    QuizDto.builder()
      .quizId(changesWithDefaults.quizId as UUID)
      .quizName(changesWithDefaults.quizName as String)
      .createdAt(changesWithDefaults.createdAt as Instant)
      .createdBy(changesWithDefaults.createdBy as Long)
      .quizDifficulty(changesWithDefaults.quizDifficulty as QuizDifficultyDto)
      .build()
  }

  CreateQuizDto createNewQuiz(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_QUIZ_DATA + changes
    CreateQuizDto.builder()
        .quizName(changesWithDefaults.quizName as String)
        .createdBy(changesWithDefaults.createdBy as Long)
        .quizDifficulty(changesWithDefaults.quizDifficulty as QuizDifficultyDto)
        .build()
  }

  void equalsQuizzes(List<QuizDto> result, List<QuizDto> expected) {
    def comparator = Comparator.comparing(QuizDto::getCreatedAt)
    result.sort(comparator)
    expected.sort(comparator)
    assert result.quizId == expected.quizId
    assert result.quizName == expected.quizName
    assert result.createdAt == expected.createdAt
    assert result.createdBy == expected.createdBy
    assert result.quizDifficulty == expected.quizDifficulty
  }
}