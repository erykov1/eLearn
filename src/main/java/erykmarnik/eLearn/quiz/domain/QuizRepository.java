package erykmarnik.eLearn.quiz.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface QuizRepository extends JpaRepository<Quiz, UUID> {
  Optional<Quiz> findByQuizId(UUID quizId);
}
