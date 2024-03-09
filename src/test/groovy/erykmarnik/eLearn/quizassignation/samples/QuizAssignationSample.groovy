package erykmarnik.eLearn.quizassignation.samples

import erykmarnik.eLearn.quizassignation.dto.CreateQuizAssignationDto
import erykmarnik.eLearn.quizassignation.dto.QuizAssignationDto

import java.time.Clock
import java.time.Instant

trait QuizAssignationSample {
  private static Map<String, Object> DEFAULT_QUIZ_ASSIGNATION_DATA = [
      assignationId: 122L,
      quizId: UUID.fromString("754c81a4-42bd-49e4-a130-2afee4dd8843"),
      questionId: 12L,
      assignedAt: Instant.now(Clock.systemUTC()),
      assignedBy: 1L
  ] as Map<String, Object>

  QuizAssignationDto createQuizAssignation(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_QUIZ_ASSIGNATION_DATA + changes
    QuizAssignationDto.builder()
      .assignationId(changesWithDefaults.assignationId as Long)
      .quizId(changesWithDefaults.quizId as UUID)
      .questionId(changesWithDefaults.questionId as Long)
      .assignedAt(changesWithDefaults.assignedAt as Instant)
      .assignedBy(changesWithDefaults.assignedBy as Long)
      .build()
  }

  CreateQuizAssignationDto createNewQuizAssignation(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_QUIZ_ASSIGNATION_DATA + changes
    CreateQuizAssignationDto.builder()
        .quizId(changesWithDefaults.quizId as UUID)
        .questionId(changesWithDefaults.questionId as Long)
        .assignedBy(changesWithDefaults.assignedBy as Long)
        .build()
  }

  void equalsQuizAssignations(List<QuizAssignationDto> result, List<QuizAssignationDto> expected) {
    def comparator = Comparator.comparing(QuizAssignationDto::getAssignedAt)
    result.sort(comparator)
    expected.sort(comparator)
    assert result.assignationId == expected.assignationId
    assert result.quizId == expected.quizId
    assert result.questionId == expected.questionId
    assert result.assignedAt == expected.assignedAt
    assert result.assignedBy == expected.assignedBy
  }
}