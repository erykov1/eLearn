package erykmarnik.eLearn.notification.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Getter
@Table(name = "created_learning_objects")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class CreatedLearningObject {
  @Id
  UUID learningObjectId;
  String learningObjectName;
  @Enumerated(EnumType.STRING)
  LearningObjectType learningObjectType;

  CreatedLearningObject changeDifficulty(LearningObjectType learningObjectType) {
    return toBuilder()
        .learningObjectType(learningObjectType)
        .build();
  }

  CreatedLearningObject changeLearningObjectName(String learningObjectName) {
    return toBuilder()
        .learningObjectName(learningObjectName)
        .build();
  }
}
