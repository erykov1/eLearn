package erykmarnik.eLearn.userresult.samples

import erykmarnik.eLearn.userresult.dto.UserResultSummaryDto

import java.time.Instant

trait UserResultSummarySample {
  private static Map<String, Object> DEFAULT_USER_RESULT_SUMMARY_DATA = [
      timeSpent: 1l,
      startedAt: Instant.now(),
      completedAt: Instant.now(),
      result: 15,
      learningObjectId: UUID.fromString("f70e5e71-d475-44a4-87f4-94f785dd6cdc")
  ] as Map<String, Object>

  UserResultSummaryDto createUserResultSummary(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_USER_RESULT_SUMMARY_DATA + changes
    UserResultSummaryDto.builder()
      .timeSpent(changesWithDefaults.timeSpent as Long)
      .startedAt(changesWithDefaults.startedAt as Instant)
      .completedAt(changesWithDefaults.completedAt as Instant)
      .result(changesWithDefaults.result as Integer)
      .learningObjectId(changesWithDefaults.learningObjectId as UUID)
      .build()
  }

  void equalsUserResultSummary(List<UserResultSummaryDto> result, List<UserResultSummaryDto> expected) {
    def comparator = Comparator.comparing(UserResultSummaryDto::getStartedAt)
    result.sort(comparator)
    expected.sort(comparator)
    assert result.timeSpent == expected.timeSpent
    assert result.startedAt == expected.startedAt
    assert result.completedAt == expected.completedAt
    assert result.result == expected.result
    assert result.learningObjectId == expected.learningObjectId
  }
}