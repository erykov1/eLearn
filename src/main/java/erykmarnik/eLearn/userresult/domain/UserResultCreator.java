package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.userassignation.dto.UserAssignationCreatedEvent;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.util.UUID;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserResultCreator {
  static Integer EMPTY_RESULT = 0;

  UserResult createUserResult(UserAssignationCreatedEvent event) {
    return UserResult.builder()
        .id(UUID.randomUUID())
        .result(EMPTY_RESULT)
        .learningObjectId(event.getLearningObjectId())
        .userId(event.getUserId())
        .userResultVisibilityType(UserResultVisibilityType.PRIVATE)
        .build();
  }


}
