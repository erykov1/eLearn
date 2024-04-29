package erykmarnik.eLearn.userresult.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

interface UserResultRepository extends JpaRepository<UserResult, UUID> {
  Optional<UserResult> findUserResultById(UUID id);
  Optional<UserResult> findUserResultByUserIdAndLearningObjectId(Long userId, UUID learningObjectId);

  @Query("SELECT ur FROM UserResult ur WHERE ur.userResultVisibilityType = :userResultVisibilityType")
  Page<UserResult> findAllUserResultsByUserResultVisibilityType(UserResultVisibilityType userResultVisibilityType, Pageable pageable);
}
