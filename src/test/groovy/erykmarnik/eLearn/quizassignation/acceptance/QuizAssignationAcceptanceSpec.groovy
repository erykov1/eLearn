package erykmarnik.eLearn.quizassignation.acceptance

import erykmarnik.eLearn.integration.ELearnApi
import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.question.acceptance.QuestionApiFacade
import erykmarnik.eLearn.question.samples.CloseQuestionSample
import erykmarnik.eLearn.question.samples.OpenQuestionSample
import erykmarnik.eLearn.quiz.acceptance.QuizApiFacade
import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quizassignation.dto.QuizAssignationDto
import erykmarnik.eLearn.quizassignation.samples.QuizAssignationSample
import erykmarnik.eLearn.user.acceptance.UserApiFacade
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.user.dto.UserDto

class QuizAssignationAcceptanceSpec extends IntegrationSpec implements QuizAssignationSample, QuizSample, CloseQuestionSample,
    OpenQuestionSample, UserSample {
  QuestionApiFacade questionApiFacade
  UserApiFacade userApiFacade
  QuizApiFacade quizApiFacade
  QuizAssignationApiFacade quizAssignationApiFacade
  private UserDto james
  private Long closeQuestionId
  private Long openQuestionId
  private UUID geographyQuizId

  def setup() {
    questionApiFacade = new QuestionApiFacade(mockMvc, objectMapper)
    userApiFacade = new UserApiFacade(mockMvc, objectMapper)
    quizApiFacade = new QuizApiFacade(mockMvc, objectMapper)
    quizAssignationApiFacade = new QuizAssignationApiFacade(mockMvc, objectMapper)
    given: "there is admin $JAMES"
      james = userApiFacade.registerAdmin(JAMES)
    and: "there is close question $closeQuestionId"
      closeQuestionId = questionApiFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: "Warsaw",
          answerB: "Paris",
          answerC: "Madrid",
          answerD: "Berlin",
          correctAnswer: "Paris"
      )).questionId
    and: "there is open question $openQuestionId"
      openQuestionId = questionApiFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of Poland?",
          correctAnswer: "Warsaw"
      )).questionId
    and: "there is geography quiz $geographyQuizId"
      geographyQuizId = quizApiFacade.createQuiz(createNewQuiz(
          quizName: "geography quiz", createdBy: james.userId, quizDifficulty: QuizDifficultyDto.EASY
      )).quizId
  }

  def "Should create new quiz assignation"() {
    when: "creates new quiz assignation"
      QuizAssignationDto quizAssignation = quizAssignationApiFacade.createQuizAssignation(createNewQuizAssignation(
          quizId: geographyQuizId, questionId: closeQuestionId, assignedBy: james.userId
      ))
    then: "assignation is created"
      def result = quizAssignationApiFacade.getQuizAssignation(quizAssignation.assignationId)
      equalsQuizAssignations([result], [createQuizAssignation(assignationId: result.assignationId, quizId: geographyQuizId, questionId: closeQuestionId,
          assignedAt: result.assignedAt, assignedBy: james.userId
      )])
  }

  def "Should delete quiz assignation by quiz id and question id"() {
    given: "there is quiz assignation"
      quizAssignationApiFacade.createQuizAssignation(createNewQuizAssignation(
            quizId: geographyQuizId, questionId: closeQuestionId, assignedBy: james.userId
      ))
    and: "there is another quiz assignation"
      quizAssignationApiFacade.createQuizAssignation(createNewQuizAssignation(
          quizId: geographyQuizId, questionId: openQuestionId, assignedBy: james.userId
      ))
    when: "deletes quiz assignation with $closeQuestionId"
      quizAssignationApiFacade.deleteQuizAssignation(geographyQuizId, closeQuestionId)
    then: "there is only assignation to $geographyQuizId with $openQuestionId"
      quizAssignationApiFacade.getAllQuestionsAssignedToQuiz(geographyQuizId) == [openQuestionId]
    when: "deletes quiz assignation with $openQuestionId"
      quizAssignationApiFacade.deleteQuizAssignation(geographyQuizId, openQuestionId)
    then: "there is no assignation to $geographyQuizId"
      quizAssignationApiFacade.getAllQuestionsAssignedToQuiz(geographyQuizId) == []
  }

  def cleanup() {
    quizAssignationApiFacade.cleanup()
    userApiFacade.cleanup()
    quizApiFacade.cleanup()
    questionApiFacade.cleanup()
  }
}
