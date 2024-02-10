package erykmarnik.eLearn.user.domain;

import erykmarnik.eLearn.user.dto.UserRoleDto;

enum UserRole {
  ADMIN,
  STUDENT;

  UserRoleDto dto() {
    return UserRoleDto.valueOf(name());
  }
}
