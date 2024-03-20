package erykmarnik.eLearn.userassignation.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.user.dto.UserDto
import erykmarnik.eLearn.userassignation.dto.UserAssignationDto
import erykmarnik.eLearn.userassignation.dto.UserAssignationStatusDto
import erykmarnik.eLearn.userassignation.samples.UserAssignationSample
import erykmarnik.eLearn.utils.samples.InstantSample

class UserAssignationAcceptanceSpec extends IntegrationSpec implements InstantSample, UserSample, QuizSample, UserAssignationSample {
  UserDto james
  UserDto adam
  QuizDto javaQuiz

  def setup() {
    given: "there is student $JAMES"
      james = api.user().registerStudent(JAMES)
    and: "there is admin $ADAM"
      adam = api.user().registerStudent(ADAM)
    and: "there is quiz $javaQuiz"
      javaQuiz = api.quiz().createQuiz(createNewQuiz(quizName: "Java Easy", createdBy: adam.userId,
          quizDifficulty: QuizDifficultyDto.EASY
      ))
    and: "current time is $NOW"
      api.timeProvider().useFixedClock(NOW)
  }

  def cleanup() {
    api.timeProvider().useSystemClock()
    api.userAssignation().cleanup()
    api.quiz().cleanup()
    api.user().cleanup()
  }

  def "Should create new user assignation"() {
    when: "$JAMES is assigned to $javaQuiz"
      UserAssignationDto jamesAssignation = api.userAssignation().createUserAssignation(
          createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    then: "assignation is created"
      equalsUserAssignation([jamesAssignation], [createUserAssignation(id: jamesAssignation.id, userId: james.userId,
          learningObjectId: javaQuiz.quizId, assignedAt: NOW, completedAt: null, userAssignationStatus: UserAssignationStatusDto.NOT_STARTED)])
  }

  def "Should change user assignation to 'IN PROGRESS'"() {
    given: "$JAMES is assigned to $javaQuiz"
      UserAssignationDto jamesAssignation = api.userAssignation().createUserAssignation(
          createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    when: "$JAMES made progress in $javaQuiz"
      UserAssignationDto jamesAssignationWithProgress = api.userAssignation().changeToInProgress(jamesAssignation.id)
    then: "assignation status changed to 'IN PROGRESS'"
      equalsUserAssignation([jamesAssignationWithProgress], [createUserAssignation(id: jamesAssignation.id, userId: james.userId,
          learningObjectId: javaQuiz.quizId, assignedAt: NOW, completedAt: null, userAssignationStatus: UserAssignationStatusDto.IN_PROGRESS)])
  }

  def "Should change user assignation to 'COMPLETED'"() {
    given: "$JAMES is assigned to $javaQuiz"
      UserAssignationDto jamesAssignation = api.userAssignation().createUserAssignation(
          createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    when: "$JAMES completed $javaQuiz"
      UserAssignationDto completedJamesAssignation = api.userAssignation().changeToCompleted(jamesAssignation.id)
    then: "assignation status changed to 'COMPLETED'"
      equalsUserAssignation([completedJamesAssignation], [createUserAssignation(id: jamesAssignation.id, userId: james.userId,
          learningObjectId: javaQuiz.quizId, assignedAt: NOW, completedAt: NOW, userAssignationStatus: UserAssignationStatusDto.COMPLETED)])
  }
}
