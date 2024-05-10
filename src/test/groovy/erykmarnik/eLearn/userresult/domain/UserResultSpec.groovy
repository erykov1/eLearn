package erykmarnik.eLearn.userresult.domain

import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.userassignation.dto.UserAssignationCreatedEvent
import erykmarnik.eLearn.userassignation.dto.UserStartedEvent
import erykmarnik.eLearn.userresult.dto.PageInfoDto
import erykmarnik.eLearn.userresult.dto.ResultProgressChangedDto
import erykmarnik.eLearn.userresult.dto.ResultTypeDto
import erykmarnik.eLearn.userresult.dto.UserResultSummaryDto
import erykmarnik.eLearn.userresult.dto.UserResultVisibilityTypeDto
import erykmarnik.eLearn.userresult.exception.UserResultNotFoundException
import erykmarnik.eLearn.userresult.samples.UserResultSample
import erykmarnik.eLearn.userresult.samples.UserResultSummarySample
import erykmarnik.eLearn.utils.InstantProvider
import erykmarnik.eLearn.utils.samples.InstantSample
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class UserResultSpec extends Specification implements InstantSample, UserResultSample, UserSample, QuizSample, UserResultSummarySample {
  InstantProvider instantProvider = new InstantProvider()
  UserResultFacade userResultFacade = new UserResultConfiguration().userResultFacade(instantProvider)

  def setup() {
    given: "current time is $NOW"
      instantProvider.useFixedClock(NOW)
  }

  def "Should create user result"() {
    when: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
    then: "result is initialized"
      def result = userResultFacade.findAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 0, learningObjectId: QUIZ_ID, completedAt: null,
          userId: USER_ID, startedAt: null, userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE)])
  }

  def "Should update user result with start date when user starts learning object"() {
    given: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
    when: "user starts learning object"
      userResultFacade.onUserResultLearningObjectStart(new UserStartedEvent(USER_ID, QUIZ_ID))
    then: "user result contains started date"
      def result = userResultFacade.findAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 0, learningObjectId: QUIZ_ID, completedAt: null,
          userId: USER_ID, startedAt: NOW, userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE)])
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
        userId: USER_ID, startedAt: NOW, userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE)])
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
          userId: USER_ID, startedAt: NOW, userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE)])
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
          userId: USER_ID, startedAt: NOW, userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE)])
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
        userId: USER_ID, startedAt: NOW, userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE)])
  }

  def "Should not get user result if it has private visibility"() {
    given: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
    expect: "there are no public user results"
      userResultFacade.getPublicUserResults(PageInfoDto.DEFAULT).toList() == []
  }

  def "Should change user result visibility"() {
    given: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
    when: "user changes user result visibility"
      userResultFacade.changeUserResultVisibility(userResultFacade.findAllUserResults()[0].id, UserResultVisibilityTypeDto.PUBLIC)
    then: "gets user result with public visibility"
      def result = userResultFacade.getPublicUserResults(PageInfoDto.DEFAULT)
      equalsUserResult(result as List, [createUserResult(id: result[0].id, result: 0, learningObjectId: QUIZ_ID, completedAt: null,
          userId: USER_ID, startedAt: null, userResultVisibilityType: UserResultVisibilityTypeDto.PUBLIC)])
  }

  def "Should get error if try to change user result visibility that does not exist"() {
    when: "changes user result visibility that does not exist"
      userResultFacade.changeUserResultVisibility(UUID.randomUUID(), UserResultVisibilityTypeDto.PUBLIC)
    then: "gets error of not found user result"
      thrown(UserResultNotFoundException)
  }

  def "Should not get user result if user changes visibility to private"() {
    given: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
    when: "user changes user result visibility"
      userResultFacade.changeUserResultVisibility(userResultFacade.findAllUserResults()[0].id, UserResultVisibilityTypeDto.PUBLIC)
    then: "gets user result with public visibility"
      def result = userResultFacade.getPublicUserResults(PageInfoDto.DEFAULT)
      equalsUserResult(result as List, [createUserResult(id: result[0].id, result: 0, learningObjectId: QUIZ_ID, completedAt: null,
          userId: USER_ID, startedAt: null, userResultVisibilityType: UserResultVisibilityTypeDto.PUBLIC)])
    when: "user changed user result visibility to private"
      userResultFacade.changeUserResultVisibility(userResultFacade.findAllUserResults()[0].id, UserResultVisibilityTypeDto.PRIVATE)
    then: "there are no public user results"
      userResultFacade.getPublicUserResults(PageInfoDto.DEFAULT).toList() == []
  }

  def "Should get user result summary"() {
    given: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
    when: "asks for user result summary"
      def result = userResultFacade.getUserResultsSummary(USER_ID)
    then: "gets user result summary"
      equalsUserResultSummary(result, [createUserResultSummary(result: 0, timeSpent: 0, startedAt: null, completedAt: null, learningObjectId: QUIZ_ID)])
  }

  def "Should not get any user result summary if user has no results"() {
    when: "asks for user result summary"
      def result = userResultFacade.getUserResultsSummary(USER_ID)
    then: "there are no results"
      result == []
  }

  def "Should not get user result summary if user does not exist"() {
    when: "asks for user result summary that does not exist"
      def result = userResultFacade.getUserResultsSummary(FAKE_USER_ID)
    then: "gets no results"
      result == []
  }

  def "Should get user result summary if user result is public"() {
    given: "creates new user result"
      userResultFacade.onUserResultInitialization(new UserAssignationCreatedEvent(USER_ID, QUIZ_ID))
    when: "user change result to public"
      userResultFacade.changeUserResultVisibility(userResultFacade.findAllUserResults()[0].id, UserResultVisibilityTypeDto.PUBLIC)
    then: "gets no results"
      equalsUserResultSummary(userResultFacade.getPublicUserResultsSummary(QUIZ_ID),
          [createUserResultSummary(result: 0, timeSpent: 0, startedAt: null, completedAt: null, learningObjectId: QUIZ_ID)])
    when: "user change result to private"
      userResultFacade.changeUserResultVisibility(userResultFacade.findAllUserResults()[0].id, UserResultVisibilityTypeDto.PRIVATE)
    then: "there are no results summary"
      userResultFacade.getPublicUserResultsSummary(QUIZ_ID) == []
  }
}
