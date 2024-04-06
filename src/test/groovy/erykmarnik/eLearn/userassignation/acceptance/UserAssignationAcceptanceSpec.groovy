package erykmarnik.eLearn.userassignation.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.quiz.acceptance.QuizApiFacade
import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto
import erykmarnik.eLearn.user.acceptance.UserApiFacade
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.user.dto.UserDto
import erykmarnik.eLearn.userassignation.dto.UserAssignationDto
import erykmarnik.eLearn.userassignation.dto.UserAssignationStatusDto
import erykmarnik.eLearn.userassignation.samples.UserAssignationSample
import erykmarnik.eLearn.utils.TimeProviderApiFacade
import erykmarnik.eLearn.utils.samples.InstantSample

class UserAssignationAcceptanceSpec extends IntegrationSpec implements InstantSample, UserSample, QuizSample, UserAssignationSample {
  UserApiFacade userApiFacade
  UserAssignationApiFacade userAssignationApiFacade
  QuizApiFacade quizApiFacade
  TimeProviderApiFacade timeProviderApiFacade
  UserDto james
  UserDto adam
  QuizDto javaQuiz

  def setup() {
    userApiFacade = new UserApiFacade(mockMvc, objectMapper)
    userAssignationApiFacade = new UserAssignationApiFacade(mockMvc, objectMapper)
    quizApiFacade = new QuizApiFacade(mockMvc, objectMapper)
    timeProviderApiFacade = new TimeProviderApiFacade(mockMvc, objectMapper)
    given: "there is student $JAMES"
      james = userApiFacade.registerStudent(JAMES)
    and: "there is admin $ADAM"
      adam = userApiFacade.registerStudent(ADAM)
    and: "there is quiz $javaQuiz"
      javaQuiz = quizApiFacade.createQuiz(createNewQuiz(quizName: "Java Easy", createdBy: adam.userId,
          quizDifficulty: QuizDifficultyDto.EASY
      ))
    and: "current time is $NOW"
      timeProviderApiFacade.useFixedClock(NOW)
  }

  def cleanup() {
    timeProviderApiFacade.useSystemClock()
    userAssignationApiFacade.cleanup()
    quizApiFacade.cleanup()
    userApiFacade.cleanup()
  }

  def "Should create new user assignation"() {
    when: "$JAMES is assigned to $javaQuiz"
      UserAssignationDto jamesAssignation = userAssignationApiFacade.createUserAssignation(
          createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    then: "assignation is created"
      equalsUserAssignation([jamesAssignation], [createUserAssignation(id: jamesAssignation.id, userId: james.userId,
          learningObjectId: javaQuiz.quizId, assignedAt: NOW, completedAt: null, userAssignationStatus: UserAssignationStatusDto.NOT_STARTED)])
  }

  def "Should change user assignation to 'IN PROGRESS'"() {
    given: "$JAMES is assigned to $javaQuiz"
      UserAssignationDto jamesAssignation = userAssignationApiFacade.createUserAssignation(
          createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    when: "$JAMES made progress in $javaQuiz"
      UserAssignationDto jamesAssignationWithProgress = userAssignationApiFacade.changeToInProgress(jamesAssignation.id)
    then: "assignation status changed to 'IN PROGRESS'"
      equalsUserAssignation([jamesAssignationWithProgress], [createUserAssignation(id: jamesAssignation.id, userId: james.userId,
          learningObjectId: javaQuiz.quizId, assignedAt: NOW, completedAt: null, userAssignationStatus: UserAssignationStatusDto.IN_PROGRESS)])
  }

  def "Should change user assignation to 'COMPLETED'"() {
    given: "$JAMES is assigned to $javaQuiz"
      UserAssignationDto jamesAssignation = userAssignationApiFacade.createUserAssignation(
          createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    when: "$JAMES completed $javaQuiz"
      UserAssignationDto completedJamesAssignation = userAssignationApiFacade.changeToCompleted(jamesAssignation.id)
    then: "assignation status changed to 'COMPLETED'"
      equalsUserAssignation([completedJamesAssignation], [createUserAssignation(id: jamesAssignation.id, userId: james.userId,
          learningObjectId: javaQuiz.quizId, assignedAt: NOW, completedAt: NOW, userAssignationStatus: UserAssignationStatusDto.COMPLETED)])
  }
}
