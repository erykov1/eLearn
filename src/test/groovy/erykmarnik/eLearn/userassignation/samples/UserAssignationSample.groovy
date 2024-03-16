package erykmarnik.eLearn.userassignation.samples


import erykmarnik.eLearn.userassignation.dto.CreateUserAssignationDto
import erykmarnik.eLearn.userassignation.dto.UserAssignationDto
import erykmarnik.eLearn.userassignation.dto.UserAssignationStatusDto

import java.time.Clock
import java.time.Instant

trait UserAssignationSample {
  private static Map<String, Object> DEFAULT_USER_ASSIGNATION_DATA = [
      id: 1L,
      userId: 11L,
      learningObjectId: UUID.fromString("595a3c1a-30d7-4fc9-90fd-fc75bf6e6dd2"),
      assignedAt: Instant.now(Clock.systemUTC()),
      completedAt: Instant.now(Clock.systemUTC()),
      userAssignationStatus: UserAssignationStatusDto.NOT_STARTED
  ] as Map<String, Object>

  UserAssignationDto createUserAssignation(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_USER_ASSIGNATION_DATA + changes
    return UserAssignationDto.builder()
      .id(changesWithDefaults.id as Long)
      .userId(changesWithDefaults.userId as Long)
      .learningObjectId(changesWithDefaults.learningObjectId as UUID)
      .assignedAt(changesWithDefaults.assignedAt as Instant)
      .completedAt(changesWithDefaults.completedAt as Instant)
      .userAssignationStatus(changesWithDefaults.userAssignationStatus as UserAssignationStatusDto)
      .build()
  }

  CreateUserAssignationDto createNewUserAssignation(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_USER_ASSIGNATION_DATA + changes
    return new CreateUserAssignationDto(changesWithDefaults.userId as Long, changesWithDefaults.learningObjectId as UUID)
  }

  void equalsUserAssignation(List<UserAssignationDto> result, List<UserAssignationDto> expected) {
    def comparator = Comparator.comparing(UserAssignationDto::getAssignedAt)
    result.sort(comparator)
    expected.sort(comparator)
    assert result.id == expected.id
    assert result.userId == expected.userId
    assert result.learningObjectId == expected.learningObjectId
    assert result.assignedAt == expected.assignedAt
    assert result.completedAt == expected.completedAt
    assert result.userAssignationStatus == expected.userAssignationStatus
  }
}