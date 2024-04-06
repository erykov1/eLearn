package erykmarnik.eLearn.notification.email.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface EmailRepository extends JpaRepository<Email, Long> {
}
