package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.userresult.dto.UserResultVisibilityTypeDto;

enum UserResultVisibilityType {
  PRIVATE,
  PUBLIC;

  UserResultVisibilityTypeDto dto() {
    return UserResultVisibilityTypeDto.valueOf(name());
  }
}
