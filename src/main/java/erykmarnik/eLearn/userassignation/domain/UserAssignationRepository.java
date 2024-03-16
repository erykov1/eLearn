package erykmarnik.eLearn.userassignation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface UserAssignationRepository extends JpaRepository<UserAssignation, Long> {
  Optional<UserAssignation> findUserAssignationById(Long id);
  Optional<UserAssignation> findUserAssignationByUserIdAndLearningObjectId(Long userId, UUID learningObjectId);
}
