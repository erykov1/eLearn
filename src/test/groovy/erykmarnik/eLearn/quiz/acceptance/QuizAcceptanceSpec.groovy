package erykmarnik.eLearn.quiz.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.quiz.dto.NewQuizNameDto
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.user.dto.UserDto
import spock.lang.Unroll

class QuizAcceptanceSpec extends IntegrationSpec implements UserSample, QuizSample {
  private UserDto james

  def setup() {
    given: "there is admin $JAMES"
      james = api.user().registerAdmin(JAMES)
  }

  def cleanup() {
    api.user().cleanup()
    api.quiz().cleanup()
  }

  def "Should create quiz"() {
    when: "$JAMES creates quiz 'Java quiz'"
      QuizDto javaQuiz = api.quiz().createQuiz(createNewQuiz(
          quizName: "Java quiz", createdBy: james.userId, quizDifficulty: QuizDifficultyDto.BEGINNER
      ))
    then: "quiz 'Java quiz' is created"
      def result = api.quiz().findById(javaQuiz.quizId)
      equalsQuizzes([result], [createQuiz(quizId: result.quizId, quizName: "Java quiz", createdAt: result.createdAt, createdBy: james.userId, quizDifficulty: QuizDifficultyDto.BEGINNER)])
  }

  @Unroll
  def "Should change quiz difficulty"() {
    given: "$JAMES creates quiz 'Java quiz'"
      QuizDto javaQuiz = api.quiz().createQuiz(createNewQuiz(
          quizName: "Java quiz", createdBy: james.userId, quizDifficulty: difficulty
      ))
    when: "changes 'Java quiz' difficulty"
      api.quiz().changeQuizDifficulty(javaQuiz.quizId, new_difficulty)
    then: "'Java quiz' difficulty is changed"
    def result = api.quiz().findById(javaQuiz.quizId)
    equalsQuizzes([result], [createQuiz(quizId: result.quizId, quizName: "Java quiz", createdAt: result.createdAt, createdBy: james.userId,
        quizDifficulty: new_difficulty)])
    where:
      difficulty                 | new_difficulty
      QuizDifficultyDto.BEGINNER | QuizDifficultyDto.EASY
      QuizDifficultyDto.EASY     | QuizDifficultyDto.MEDIUM
      QuizDifficultyDto.MEDIUM   | QuizDifficultyDto.HARD
      QuizDifficultyDto.HARD     | QuizDifficultyDto.EXPERT
  }

  def "Should change quiz name"() {
    given: "$JAMES creates quiz 'Java quiz'"
      QuizDto javaQuiz = api.quiz().createQuiz(createNewQuiz(
          quizName: "Java quiz", createdBy: james.userId, quizDifficulty: QuizDifficultyDto.BEGINNER
      ))
    when: "changes quiz name"
      api.quiz().changeQuizName(javaQuiz.quizId, "Java quiz for beginners")
    then: "quiz name is changed"
      def result = api.quiz().findById(javaQuiz.quizId)
      equalsQuizzes([result], [createQuiz(quizId: result.quizId, quizName: '"Java quiz for beginners"', createdAt: result.createdAt, createdBy: james.userId,
          quizDifficulty: QuizDifficultyDto.BEGINNER)])
    when: "change again quiz name"
      api.quiz().changeQuizName(javaQuiz.quizId, "First experience with java for total beginners")
    then: "quiz name is changed again"
      def resultForNewQuiz = api.quiz().findById(javaQuiz.quizId)
      equalsQuizzes([resultForNewQuiz], [createQuiz(quizId: result.quizId, quizName: '"First experience with java for total beginners"', createdAt: result.createdAt, createdBy: james.userId,
          quizDifficulty: QuizDifficultyDto.BEGINNER)])
  }
}
