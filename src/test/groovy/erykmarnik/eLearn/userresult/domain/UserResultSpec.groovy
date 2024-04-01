package erykmarnik.eLearn.userresult.domain

import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.userassignation.dto.UserAssignationCreatedEvent
import erykmarnik.eLearn.userassignation.dto.UserStartedEvent
import erykmarnik.eLearn.userresult.dto.ResultProgressChangedDto
import erykmarnik.eLearn.userresult.dto.ResultTypeDto
import erykmarnik.eLearn.userresult.samples.UserResultSample
import erykmarnik.eLearn.utils.InstantProvider
import erykmarnik.eLearn.utils.samples.InstantSample
import spock.lang.Specification

class UserResultSpec extends Specification implements InstantSample, UserResultSample, UserSample, QuizSample {
  InstantProvider instantProvider = new InstantProvider()
  UserResultFacade userResultFacade = new UserResultConfiguration().userResultFacade(instantProvider)

  def setup() {
    given: "current time is $NOW"
      instantProvider.useFixedClock(NOW)
  }

  def "Should create user result"() {
    when: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
    then: "result is created with result equals 0, completedAt equals null and startedAt equals null"
      def result = userResultFacade.findAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 0, learningObjectId: QUIZ_ID, completedAt: null,
          userId: USER_ID, startedAt: null)])
  }

  def "Should update user result with start date when user starts learning object"() {
    given: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
    when: "user starts learning object"
      userResultFacade.onUserResultLearningObjectStart(new UserStartedEvent(USER_ID, QUIZ_ID))
    then: "user result contains started date"
      def result = userResultFacade.findAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 0, learningObjectId: QUIZ_ID, completedAt: null,
          userId: USER_ID, startedAt: NOW)])
  }

  def "Should update user result with result when user starts learning object and want to save it"() {
    given: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
      def userResultId = userResultFacade.findAllUserResults()[0].id
    and: "user starts learning object"
      userResultFacade.onUserResultLearningObjectStart(new UserStartedEvent(USER_ID, QUIZ_ID))
    when: "user saves progress"
      userResultFacade.onUserResultProgressChangedSave(userResultId, new ResultProgressChangedDto(COMPLETED_RESULT, ResultTypeDto.IN_PROGRESS))
    then: "user result contains started date"
    def result = userResultFacade.findAllUserResults()
    equalsUserResult(result, [createUserResult(id: result[0].id, result: COMPLETED_RESULT, learningObjectId: QUIZ_ID,
        userId: USER_ID, startedAt: NOW)])
  }

  def "Should update user result with completed date when user completes learning object"() {
    given: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
      def userResultId = userResultFacade.findAllUserResults()[0].id
    and: "user starts learning object"
      userResultFacade.onUserResultLearningObjectStart(new UserStartedEvent(USER_ID, QUIZ_ID))
    when: "user completes learning object"
      userResultFacade.onUserResultProgressChangedSave(userResultId, new ResultProgressChangedDto(COMPLETED_RESULT, ResultTypeDto.COMPLETED))
    then: "user result contains started date and completed date with result"
      def result = userResultFacade.findAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: COMPLETED_RESULT, learningObjectId: QUIZ_ID, completedAt: NOW,
          userId: USER_ID, startedAt: NOW)])
  }

  def "Should update user result with completed date when user completes learning object again"() {
    given: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
      def userResultId = userResultFacade.findAllUserResults()[0].id
    and: "user starts learning object"
      userResultFacade.onUserResultLearningObjectStart(new UserStartedEvent(USER_ID, QUIZ_ID))
    and: "user completes learning object"
      userResultFacade.onUserResultProgressChangedSave(userResultId, new ResultProgressChangedDto(COMPLETED_RESULT, ResultTypeDto.COMPLETED))
    and: "current time is $WEEK_LATER"
      instantProvider.useFixedClock(WEEK_LATER)
    when: "user completes learning object again"
      userResultFacade.onUserResultProgressChangedSave(userResultId, new ResultProgressChangedDto(COMPLETED_RESULT, ResultTypeDto.COMPLETED))
    then: "user result contains started date and new completed date with result"
      def result = userResultFacade.findAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: COMPLETED_RESULT, learningObjectId: QUIZ_ID, completedAt: WEEK_LATER,
          userId: USER_ID, startedAt: NOW)])
  }

  def "Should not update user result with start date when user starts learning object again"() {
    given: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
    and: "user starts learning object"
      userResultFacade.onUserResultLearningObjectStart(new UserStartedEvent(USER_ID, QUIZ_ID))
    when: "user starts learning object week later"
      instantProvider.useFixedClock(WEEK_LATER)
      userResultFacade.onUserResultLearningObjectStart(new UserStartedEvent(USER_ID, QUIZ_ID))
    then: "user result contains started date"
      def result = userResultFacade.findAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 0, learningObjectId: QUIZ_ID, completedAt: null,
        userId: USER_ID, startedAt: NOW)])
  }
}
