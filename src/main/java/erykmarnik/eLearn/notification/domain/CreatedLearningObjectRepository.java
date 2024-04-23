package erykmarnik.eLearn.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

interface CreatedLearningObjectRepository extends JpaRepository<CreatedLearningObject, UUID> {
}
