package erykmarnik.eLearn.quiz.domain

import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto
import erykmarnik.eLearn.quiz.exception.QuizNotFoundException
import erykmarnik.eLearn.utils.InstantProvider
import spock.lang.Specification
import spock.lang.Unroll

class QuizSpec extends Specification implements QuizSample {
  InstantProvider instantProvider = new InstantProvider()
  QuizFacade quizFacade = new QuizConfiguration().quizFacade(new QuizCreator(instantProvider))

  def "Should create new quiz"() {
    when: "creates new quiz"
      quizFacade.createQuiz(createNewQuiz(quizName: "Java Easy", createdBy: 1L,
          quizDifficulty: QuizDifficultyDto.EASY
      ))
    then: "quiz is created"
      def result = quizFacade.getAllQuizzes()
      equalsQuizzes(result, [createQuiz(quizId: result[0].quizId, quizName: "Java Easy", createdAt: result[0].createdAt,
          createdBy: 1L, quizDifficulty: QuizDifficultyDto.EASY)])
  }

  def "Should change quiz name"() {
    given: "there is quiz"
      QuizDto quiz = quizFacade.createQuiz(createNewQuiz(quizName: "Java Easy", createdBy: 1L,
          quizDifficulty: QuizDifficultyDto.EASY
      ))
    when: "changes quiz name"
      quizFacade.changeQuizName(quiz.quizId, "Java Easy Mode")
    then: "quiz name is changed"
      def result = quizFacade.getAllQuizzes()
      equalsQuizzes(result, [createQuiz(quizId: result[0].quizId, quizName: "Java Easy Mode", createdAt: result[0].createdAt,
          createdBy: 1L, quizDifficulty: QuizDifficultyDto.EASY)])
  }

  def "Should delete quiz"() {
    given: "there is quiz"
      QuizDto javaQuiz = quizFacade.createQuiz(createNewQuiz(quizName: "Java quiz", createdBy: 1L,
          quizDifficulty: QuizDifficultyDto.EASY
      ))
    and: "there is another quiz"
      QuizDto pythonQuiz = quizFacade.createQuiz(createNewQuiz(quizName: "Python quiz", createdBy: 1L,
          quizDifficulty: QuizDifficultyDto.BEGINNER
      ))
    when: "deletes java quiz"
      quizFacade.deleteQuizById(javaQuiz.quizId)
    then: "there is only python quiz"
      def result = quizFacade.getAllQuizzes()
      equalsQuizzes(result, [createQuiz(quizId: pythonQuiz.quizId, quizName: "Python quiz", createdAt: result[0].createdAt,
          createdBy: 1L, quizDifficulty: QuizDifficultyDto.BEGINNER)])
  }

  def "Should gets error if try to delete quiz that does not exist"() {
    when: "try to delete quiz that does not exist"
      quizFacade.deleteQuizById(UUID.randomUUID())
    then: "gets error of not existing quiz"
      thrown(QuizNotFoundException)
  }

  def "Should gets error if try to get quiz that does not exist"() {
    when: "asks for quiz that does not exist"
      quizFacade.findByQuizId(UUID.randomUUID())
    then: "gets error of not existing quiz"
      thrown(QuizNotFoundException)
  }

  @Unroll
  def "Should change quiz difficulty"() {
    given: "there is quiz with $difficulty"
      QuizDto javaQuiz = quizFacade.createQuiz(createNewQuiz(quizName: "Java quiz", createdBy: 1L,
          quizDifficulty: difficulty
      ))
    when: "change quiz $difficulty with $new_difficulty"
      quizFacade.changeQuizDifficulty(javaQuiz.quizId, new_difficulty)
    then: "quiz difficulty is changed to $new_difficulty"
      def result = quizFacade.getAllQuizzes()
      equalsQuizzes(result, [createQuiz(quizId: javaQuiz.quizId, quizName: "Java quiz", createdAt: result[0].createdAt,
          createdBy: 1L, quizDifficulty: new_difficulty)])
    where:
      difficulty                 | new_difficulty
      QuizDifficultyDto.BEGINNER | QuizDifficultyDto.EASY
      QuizDifficultyDto.EASY     | QuizDifficultyDto.MEDIUM
      QuizDifficultyDto.MEDIUM   | QuizDifficultyDto.HARD
      QuizDifficultyDto.HARD     | QuizDifficultyDto.EXPERT
      QuizDifficultyDto.EXPERT   | QuizDifficultyDto.HARD
      QuizDifficultyDto.HARD     | QuizDifficultyDto.MEDIUM
      QuizDifficultyDto.MEDIUM   | QuizDifficultyDto.EASY
      QuizDifficultyDto.EASY     | QuizDifficultyDto.BEGINNER
  }

  def "Should get error if try to change quiz name that does not exist"() {
    when: "try to change quiz name for quiz that does not exist"
      quizFacade.changeQuizName(UUID.randomUUID(), "new Java Quiz")
    then: "gets error of not existing quiz"
      thrown(QuizNotFoundException)
  }
}
