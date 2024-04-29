package erykmarnik.eLearn.userresult.samples

import erykmarnik.eLearn.userresult.dto.UserResultDto
import erykmarnik.eLearn.userresult.dto.UserResultVisibilityTypeDto
import erykmarnik.eLearn.utils.samples.InstantSample

import java.time.Instant

trait UserResultSample extends InstantSample {
  static final Integer COMPLETED_RESULT = 20

  private static Map<String, Object> USER_RESULT_DEFAULT_DATA = [
      id: UUID.fromString("47691fe7-7e01-448e-88eb-7804f1148103"),
      result: 15f,
      learningObjectId: UUID.fromString("5d7dcac2-abad-42f6-b4f4-4a7219f4e3c4"),
      completedAt: NOW_SEC,
      userId: 11L,
      startedAt: NOW_SEC,
      userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE
  ] as Map<String, Object>

  UserResultDto createUserResult(Map<String, Object> changes = [:]) {
    def changesWithDefaults = USER_RESULT_DEFAULT_DATA + changes
    UserResultDto.builder()
      .id(changesWithDefaults.id as UUID)
      .result(changesWithDefaults.result as Integer)
      .learningObjectId(changesWithDefaults.learningObjectId as UUID)
      .completedAt(changesWithDefaults.completedAt as Instant)
      .userId(changesWithDefaults.userId as Long)
      .startedAt(changesWithDefaults.startedAt as Instant)
      .userResultVisibilityType(changesWithDefaults.userResultVisibilityType as UserResultVisibilityTypeDto)
      .build()
  }

  void equalsUserResult(List<UserResultDto> result, List<UserResultDto> expected) {
    def comparator = Comparator.comparing(UserResultDto::getStartedAt)
    result.sort(comparator)
    expected.sort(comparator)
    assert result.id == expected.id
    assert result.result == expected.result
    assert result.learningObjectId == expected.learningObjectId
    assert result.completedAt == expected.completedAt
    assert result.userId == expected.userId
    assert result.startedAt == expected.startedAt
    assert result.userResultVisibilityType == expected.userResultVisibilityType
  }
}