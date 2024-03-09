package erykmarnik.eLearn.quizassignation.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

interface QuizAssignationRepository extends JpaRepository<QuizAssignation, Long> {
  @Query("SELECT qa FROM QuizAssignation qa WHERE qa.quizId = :quizId AND qa.questionId = :questionId")
  List<QuizAssignation> getByQuizIdAndQuestionId(@Param("quizId") UUID quizId, @Param("questionId") Long questionId);
}
