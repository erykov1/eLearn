package erykmarnik.eLearn.userresult.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface UserResultRepository extends JpaRepository<UserResult, UUID> {
  Optional<UserResult> findUserResultById(UUID id);
  Optional<UserResult> findUserResultByUserIdAndLearningObjectId(Long userId, UUID learningObjectId);
}
