package erykmarnik.eLearn.question.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CloseQuestionRepository extends JpaRepository<CloseQuestion, Long> {
  Optional<CloseQuestion> findByQuestionId(Long questionId);
}
