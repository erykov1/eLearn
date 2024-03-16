package erykmarnik.eLearn.userassignation.domain;

import erykmarnik.eLearn.userassignation.dto.CreateUserAssignationDto;
import erykmarnik.eLearn.utils.InstantProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class UserAssignationCreator {
  InstantProvider instantProvider;

  @Autowired
  public UserAssignationCreator(InstantProvider instantProvider) {
    this.instantProvider = instantProvider;
  }

  UserAssignation createUserAssignation(CreateUserAssignationDto createUserAssignationDto) {
    return UserAssignation.builder()
        .userId(createUserAssignationDto.getUserId())
        .learningObjectId(createUserAssignationDto.getLearningObjectId())
        .assignedAt(instantProvider.now())
        .userAssignationStatus(UserAssignationStatus.NOT_STARTED)
        .build();
  }
}
