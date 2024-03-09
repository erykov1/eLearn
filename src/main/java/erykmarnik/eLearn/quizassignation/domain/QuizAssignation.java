package erykmarnik.eLearn.quizassignation.domain;

import erykmarnik.eLearn.quizassignation.dto.QuizAssignationDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "quiz_assignations")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class QuizAssignation {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_assignation_sequence")
  @SequenceGenerator(name = "quiz_assignation_sequence", sequenceName = "quiz_assignation_sequence", allocationSize = 1)
  Long assignationId;
  UUID quizId;
  Long questionId;
  Instant assignedAt;
  Long assignedBy;

  QuizAssignationDto dto() {
    return QuizAssignationDto.builder()
        .assignationId(assignationId)
        .quizId(quizId)
        .questionId(questionId)
        .assignedAt(assignedAt)
        .assignedBy(assignedBy)
        .build();
  }
}
