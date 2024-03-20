package erykmarnik.eLearn.userassignation.domain;

import erykmarnik.eLearn.userassignation.dto.UserAssignationStatusDto;

enum UserAssignationStatus {
  NOT_STARTED,
  IN_PROGRESS,
  COMPLETED;

  UserAssignationStatusDto dto() {
    return UserAssignationStatusDto.valueOf(name());
  }
}
