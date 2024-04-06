package erykmarnik.eLearn.quiz.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.quiz.dto.NewQuizNameDto
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto
import erykmarnik.eLearn.user.acceptance.UserApiFacade
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.user.dto.UserDto
import spock.lang.Unroll

class QuizAcceptanceSpec extends IntegrationSpec implements UserSample, QuizSample {
  QuizApiFacade quizApiFacade
  UserApiFacade userApiFacade
  private UserDto james

  def setup() {
    quizApiFacade = new QuizApiFacade(mockMvc, objectMapper)
    userApiFacade = new UserApiFacade(mockMvc, objectMapper)
    given: "there is admin $JAMES"
      james = userApiFacade.registerAdmin(JAMES)
  }

  def cleanup() {
    userApiFacade.cleanup()
    quizApiFacade.cleanup()
  }

  def "Should create quiz"() {
    when: "$JAMES creates quiz 'Java quiz'"
      QuizDto javaQuiz = quizApiFacade.createQuiz(createNewQuiz(
          quizName: "Java quiz", createdBy: james.userId, quizDifficulty: QuizDifficultyDto.BEGINNER
      ))
    then: "quiz 'Java quiz' is created"
      def result = quizApiFacade.findById(javaQuiz.quizId)
      equalsQuizzes([result], [createQuiz(quizId: result.quizId, quizName: "Java quiz", createdAt: result.createdAt, createdBy: james.userId, quizDifficulty: QuizDifficultyDto.BEGINNER)])
  }

  @Unroll
  def "Should change quiz difficulty"() {
    given: "$JAMES creates quiz 'Java quiz'"
      QuizDto javaQuiz = quizApiFacade.createQuiz(createNewQuiz(
          quizName: "Java quiz", createdBy: james.userId, quizDifficulty: difficulty
      ))
    when: "changes 'Java quiz' difficulty"
      quizApiFacade.changeQuizDifficulty(javaQuiz.quizId, new_difficulty)
    then: "'Java quiz' difficulty is changed"
      def result = quizApiFacade.findById(javaQuiz.quizId)
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
      QuizDto javaQuiz = quizApiFacade.createQuiz(createNewQuiz(
          quizName: "Java quiz", createdBy: james.userId, quizDifficulty: QuizDifficultyDto.BEGINNER
      ))
    when: "changes quiz name"
      quizApiFacade.changeQuizName(javaQuiz.quizId, "Java quiz for beginners")
    then: "quiz name is changed"
      def result = quizApiFacade.findById(javaQuiz.quizId)
      equalsQuizzes([result], [createQuiz(quizId: result.quizId, quizName: '"Java quiz for beginners"', createdAt: result.createdAt, createdBy: james.userId,
          quizDifficulty: QuizDifficultyDto.BEGINNER)])
    when: "change again quiz name"
      quizApiFacade.changeQuizName(javaQuiz.quizId, "First experience with java for total beginners")
    then: "quiz name is changed again"
      def resultForNewQuiz = quizApiFacade.findById(javaQuiz.quizId)
      equalsQuizzes([resultForNewQuiz], [createQuiz(quizId: result.quizId, quizName: '"First experience with java for total beginners"', createdAt: result.createdAt, createdBy: james.userId,
          quizDifficulty: QuizDifficultyDto.BEGINNER)])
  }
}
