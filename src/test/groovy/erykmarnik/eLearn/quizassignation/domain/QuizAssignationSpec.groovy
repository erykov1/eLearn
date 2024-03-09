package erykmarnik.eLearn.quizassignation.domain

import erykmarnik.eLearn.question.domain.QuestionConfiguration
import erykmarnik.eLearn.question.domain.QuestionFacade
import erykmarnik.eLearn.question.exception.QuestionNotFoundException
import erykmarnik.eLearn.question.samples.CloseQuestionSample
import erykmarnik.eLearn.question.samples.OpenQuestionSample
import erykmarnik.eLearn.quiz.domain.QuizConfiguration
import erykmarnik.eLearn.quiz.domain.QuizCreator
import erykmarnik.eLearn.quiz.domain.QuizFacade
import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quizassignation.dto.QuizAssignationDto
import erykmarnik.eLearn.quizassignation.samples.QuizAssignationSample
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.utils.InstantProvider
import spock.lang.Specification
import erykmarnik.eLearn.quiz.exception.QuizNotFoundException

class QuizAssignationSpec extends Specification implements QuizSample, UserSample, OpenQuestionSample, CloseQuestionSample, QuizAssignationSample {
  private UUID geographyQuizId
  private long closeQuestionId
  private long openQuestionId
  InstantProvider instantProvider = new InstantProvider()
  QuizFacade quizFacade = new QuizConfiguration().quizFacade(new QuizCreator(instantProvider))
  QuestionFacade questionFacade = new QuestionConfiguration().questionFacade()
  QuizAssignationCreator quizAssignationCreator = new QuizAssignationCreator(instantProvider)
  QuizAssignationFacade quizAssignationFacade = new QuizAssignationConfiguration().quizAssignationFacade(quizFacade, questionFacade, quizAssignationCreator)

  def setup() {
    given: "there is quiz 'Java quiz'"
      geographyQuizId = quizFacade.createQuiz(createNewQuiz(quizName: "geography quiz", createdBy: 1L,
          quizDifficulty: QuizDifficultyDto.EASY
      )).quizId
    and: "there is close question"
      closeQuestionId = questionFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: "Warsaw",
          answerB: "Paris",
          answerC: "Madrid",
          answerD: "Berlin",
          correctAnswer: "Paris"
      )).questionId
    and: "there is open question"
      openQuestionId = questionFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of Poland?",
          correctAnswer: "Warsaw"
      )).questionId
  }

  def "Should get error if try to create assignation with question that does not exist"() {
    when: "makes assignation with question that does not exist"
      quizAssignationFacade.createQuizAssignation(createNewQuizAssignation(
          quizId: geographyQuizId, questionId: FAKE_QUESTION_ID, assignedBy: 1L
      ))
    then: "gets error of not existing question"
      thrown(QuestionNotFoundException)
  }

  def "Should get error if try to create assignation with quiz that does not exist"() {
    when: "makes assignation with question that does not exist"
      quizAssignationFacade.createQuizAssignation(createNewQuizAssignation(
          quizId: FAKE_QUIZ_ID, questionId: closeQuestionId, assignedBy: 1L
      ))
    then: "gets error of not existing question"
      thrown(QuizNotFoundException)
  }

  def "Should create new quiz assignation with existing quiz and question"() {
    when: "makes quiz assignation"
      QuizAssignationDto quizAssignation = quizAssignationFacade.createQuizAssignation(createNewQuizAssignation(
          quizId: geographyQuizId, questionId: openQuestionId, assignedBy: 1L
      ))
    then: "assignation is created"
      def result = quizAssignationFacade.findByQuizAssignation(quizAssignation.assignationId)
      equalsQuizAssignations([result], [createQuizAssignation(assignationId: result.assignationId, quizId: geographyQuizId, questionId: openQuestionId,
        assignedAt: result.assignedAt, assignedBy: 1L
      )])
  }

  def "Should delete quiz assignation by quiz id and question id"() {
    given: "there is one assignation"
      quizAssignationFacade.createQuizAssignation(createNewQuizAssignation(
          quizId: geographyQuizId, questionId: openQuestionId, assignedBy: 1L
      ))
    when: "deletes quiz assignation"
      quizAssignationFacade.deleteQuizAssignation(geographyQuizId, openQuestionId)
    then: "there are no assignations"
      quizAssignationFacade.findAllAssignations() == []
  }

  def "Should get error if try to delete assignation with question that does not exist"() {
    when: "deletes quiz assignation with question that does not exist"
      quizAssignationFacade.deleteQuizAssignation(geographyQuizId, FAKE_QUESTION_ID)
    then: "gets error of not existing question"
      thrown(QuestionNotFoundException)
  }

  def "Should get error if try to delete assignation with quiz that does not exist"() {
    when: "deletes quiz assignation with question that does not exist"
      quizAssignationFacade.deleteQuizAssignation(FAKE_QUIZ_ID, openQuestionId)
    then: "gets error of not existing question"
      thrown(QuizNotFoundException)
  }

  def "Should return all assignation for existing quiz"() {
    given: "there is assignation for geography quiz"
      quizAssignationFacade.createQuizAssignation(createNewQuizAssignation(
          quizId: geographyQuizId, questionId: openQuestionId, assignedBy: 1L
      ))
    when: "asks for $geographyQuizId assignations"
      def result = quizAssignationFacade.findAllAssignedQuestionsToQuiz(geographyQuizId)
    then: "return all assignations for $geographyQuizId"
      result == [openQuestionId]
  }
}
