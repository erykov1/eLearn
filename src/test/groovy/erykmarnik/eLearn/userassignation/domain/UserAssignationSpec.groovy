package erykmarnik.eLearn.userassignation.domain

import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto
import erykmarnik.eLearn.quiz.exception.QuizNotFoundException
import erykmarnik.eLearn.user.dto.UserDto
import erykmarnik.eLearn.user.exception.NotExistingUserException
import erykmarnik.eLearn.userassignation.dto.UserAssignationDto
import erykmarnik.eLearn.userassignation.dto.UserAssignationStatusDto
import erykmarnik.eLearn.userassignation.exception.UserAlreadyAssignedException

class UserAssignationSpec extends UserAssignationBaseSpec {
  UserDto james
  QuizDto javaQuiz

  def setup() {
    given: "there is quiz 'Java quiz'"
      javaQuiz = quizFacade.createQuiz(createNewQuiz(quizName: "Java Easy", createdBy: 1L,
          quizDifficulty: QuizDifficultyDto.EASY
      ))
    and: "there is student $JAMES"
      james = userFacade.createStudent(JAMES)
    and: "current time is $NOW"
      instantProvider.useFixedClock(NOW)
  }

  def "Should create new user assignation with 'NOT_STARTED' status"() {
    when: "$JAMES is assigned to $javaQuiz"
      UserAssignationDto jamesAssignation = userAssignationFacade.createAssignation(
          createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    then: "assignation is created with 'NOT_STARTED' status"
      equalsUserAssignation([jamesAssignation], [createUserAssignation(id: jamesAssignation.id, userId: james.userId,
          learningObjectId: javaQuiz.quizId, assignedAt: NOW, completedAt: null, userAssignationStatus: UserAssignationStatusDto.NOT_STARTED)])
  }

  def "Should not create new user assignation to the same quiz if there is already assigned user"() {
    given: "there is user assignation"
      userAssignationFacade.createAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    when: "creates new assignation to the same quiz"
      userAssignationFacade.createAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    then: "gets error of already assigned user"
      thrown(UserAlreadyAssignedException)
  }

  def "Should create new user assignation to the same quiz if earlier user assignation has status 'COMPLETED'"() {
    given: "there is user assignation"
      def jamesAssignation = userAssignationFacade.createAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    and: "user completes quiz"
      userAssignationFacade.changeToCompleted(jamesAssignation.id)
    when: "creates new assignation to the same quiz $WEEK_LATER"
      instantProvider.useFixedClock(WEEK_LATER)
      def newJamesAssignation = userAssignationFacade.createAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    then: "user has two assignations"
    equalsUserAssignation(userAssignationFacade.findAllUserAssignations(), [createUserAssignation(id: jamesAssignation.id, userId: james.userId,
        learningObjectId: javaQuiz.quizId, assignedAt: NOW, completedAt: NOW, userAssignationStatus: UserAssignationStatusDto.COMPLETED),
        createUserAssignation(id: newJamesAssignation.id, userId: james.userId, learningObjectId: javaQuiz.quizId, assignedAt: WEEK_LATER,
            completedAt: null, userAssignationStatus: UserAssignationStatusDto.NOT_STARTED)
    ])
  }

  def "Should not create user assignation to quiz that does not exist"() {
    when: "creates new assignation to quiz that does not exist"
      userAssignationFacade.createAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: FAKE_QUIZ_ID))
    then: "gets error of not existing quiz"
      thrown(QuizNotFoundException)
  }

  def "Should not create user assignation to quiz if there is not existing user"() {
    when: "creates new assignation with user that does not exist"
      userAssignationFacade.createAssignation(createNewUserAssignation(userId: FAKE_USER_ID, learningObjectId: javaQuiz.quizId))
    then: "gets error of not existing user"
      thrown(NotExistingUserException)
  }

  def "Should change status to 'IN_PROGRESS' if user has made some progress"() {
    given: "creates new user assignation"
      UserAssignationDto jamesAssignation = userAssignationFacade.createAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    when: "$JAMES made progress in $javaQuiz"
      UserAssignationDto jamesAssignationWithProgress = userAssignationFacade.changeToInProgress(jamesAssignation.id)
    then: "user assignation status changed to 'IN_PROGRESS'"
      equalsUserAssignation([jamesAssignationWithProgress], [createUserAssignation(id: jamesAssignation.id, userId: james.userId,
          learningObjectId: javaQuiz.quizId, assignedAt: NOW, completedAt: null, userAssignationStatus: UserAssignationStatusDto.IN_PROGRESS)])
  }

  def "Should change status to 'COMPLETED' if user has completed learning object"() {
    given: "creates new user assignation"
      UserAssignationDto jamesAssignation = userAssignationFacade.createAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    when: "$JAMES completed $javaQuiz"
      UserAssignationDto completedJamesAssignation = userAssignationFacade.changeToCompleted(jamesAssignation.id)
    then: "user assignation status changed to 'COMPLETED'"
      equalsUserAssignation([completedJamesAssignation], [createUserAssignation(id: jamesAssignation.id, userId: james.userId,
          learningObjectId: javaQuiz.quizId, assignedAt: NOW, completedAt: NOW, userAssignationStatus: UserAssignationStatusDto.COMPLETED)])
  }
}
