package erykmarnik.eLearn.userassignation.domain;


import erykmarnik.eLearn.userassignation.dto.UserAssignationDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_assignations")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserAssignation {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_assignation_sequence")
  @SequenceGenerator(name = "user_assignation_sequence", sequenceName = "user_assignation_sequence", allocationSize = 1)
  Long id;
  Long userId;
  UUID learningObjectId;
  Instant assignedAt;
  Instant completedAt;
  @Enumerated(EnumType.STRING)
  UserAssignationStatus userAssignationStatus;

  UserAssignationDto dto() {
    return UserAssignationDto.builder()
        .id(id)
        .userId(userId)
        .learningObjectId(learningObjectId)
        .assignedAt(assignedAt)
        .completedAt(completedAt)
        .userAssignationStatus(userAssignationStatus.dto())
        .build();
  }

  UserAssignation changeToInProgress() {
    return this.toBuilder()
        .userAssignationStatus(UserAssignationStatus.IN_PROGRESS)
        .build();
  }
}
