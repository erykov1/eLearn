package erykmarnik.eLearn.question.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface OpenQuestionRepository extends JpaRepository<OpenQuestion, Long> {
  Optional<OpenQuestion> findByQuestionId(Long questionId);
}
