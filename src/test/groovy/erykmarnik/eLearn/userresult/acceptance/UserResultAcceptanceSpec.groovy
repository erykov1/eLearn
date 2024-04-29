package erykmarnik.eLearn.userresult.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.quiz.acceptance.QuizApiFacade
import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto
import erykmarnik.eLearn.user.acceptance.UserApiFacade
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.user.dto.UserDto
import erykmarnik.eLearn.userassignation.acceptance.UserAssignationApiFacade
import erykmarnik.eLearn.userassignation.dto.UserAssignationDto
import erykmarnik.eLearn.userassignation.samples.UserAssignationSample
import erykmarnik.eLearn.userresult.dto.PageInfoDto
import erykmarnik.eLearn.userresult.dto.ResultProgressChangedDto
import erykmarnik.eLearn.userresult.dto.ResultTypeDto
import erykmarnik.eLearn.userresult.dto.UserResultDto
import erykmarnik.eLearn.userresult.dto.UserResultVisibilityTypeDto
import erykmarnik.eLearn.userresult.samples.UserResultSample
import erykmarnik.eLearn.utils.TimeProviderApiFacade
import erykmarnik.eLearn.utils.samples.InstantSample
import org.springdoc.core.converters.models.Pageable
import org.springframework.data.domain.PageRequest

class UserResultAcceptanceSpec extends IntegrationSpec implements UserResultSample, InstantSample, UserSample, QuizSample,
    UserAssignationSample {
  UserApiFacade userApiFacade
  QuizApiFacade quizApiFacade
  UserResultApiFacade userResultApiFacade
  TimeProviderApiFacade timeProviderApiFacade
  UserAssignationApiFacade userAssignationApiFacade
  UserDto james
  UserDto adam
  QuizDto javaQuiz

  def setup() {
    userApiFacade = new UserApiFacade(mockMvc, objectMapper)
    quizApiFacade = new QuizApiFacade(mockMvc, objectMapper)
    userResultApiFacade = new UserResultApiFacade(mockMvc, objectMapper)
    timeProviderApiFacade = new TimeProviderApiFacade(mockMvc, objectMapper)
    userAssignationApiFacade = new UserAssignationApiFacade(mockMvc, objectMapper)
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
    userApiFacade.cleanup()
    userAssignationApiFacade.cleanup()
    userResultApiFacade.cleanup()
    timeProviderApiFacade.useSystemClock()
  }

  def "Should create new user result when user is assigned to learning object"() {
    when: "$JAMES is assigned to quiz $javaQuiz"
      userAssignationApiFacade.createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    then: "there is new user result for $JAMES"
      List<UserResultDto> result = userResultApiFacade.getAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 0, learningObjectId: javaQuiz.quizId, completedAt: null,
          userId: james.userId, startedAt: null, userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE)])
  }

  def "Should update user result with start date when user starts learning object"() {
    given: "$JAMES is assigned to quiz $javaQuiz"
      UserAssignationDto assignation =
          userAssignationApiFacade.createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    when: "$JAMES starts learning object"
      userAssignationApiFacade.changeToInProgress(assignation.id)
    then: "user result contains started date"
      List<UserResultDto> result = userResultApiFacade.getAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 0, learningObjectId: javaQuiz.quizId, completedAt: null,
          userId: james.userId, startedAt: NOW, userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE)])
  }

  def "Should update user result with result when user starts learning object and want to save it"() {
    given: "$JAMES is assigned to quiz $javaQuiz"
      UserAssignationDto assignation =
          userAssignationApiFacade.createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    and: "$JAMES starts learning object"
      userAssignationApiFacade.changeToInProgress(assignation.id)
    when: "$JAMES makes some progress and save it"
      userResultApiFacade.saveUserResultProgressChanged(userResultApiFacade.getAllUserResults()[0].id, new ResultProgressChangedDto(12, ResultTypeDto.IN_PROGRESS))
    then: "user result contains started date and result"
      List<UserResultDto> result = userResultApiFacade.getAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 12, learningObjectId: javaQuiz.quizId, completedAt: null,
          userId: james.userId, startedAt: NOW, userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE)])
  }

  def "Should update user result with completed date when user completes learning object"() {
    given: "$JAMES is assigned to quiz $javaQuiz"
      UserAssignationDto assignation =
        userAssignationApiFacade.createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    and: "$JAMES starts learning object"
      userAssignationApiFacade.changeToInProgress(assignation.id)
    when: "$JAMES completes learning object"
      userResultApiFacade.saveUserResultProgressChanged(userResultApiFacade.getAllUserResults()[0].id, new ResultProgressChangedDto(16, ResultTypeDto.COMPLETED))
    then: "user result contains started date, result and completed date"
      List<UserResultDto> result = userResultApiFacade.getAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 16, learningObjectId: javaQuiz.quizId, completedAt: NOW,
          userId: james.userId, startedAt: NOW, userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE)])
  }

  def "Should update user result with completed date when user completes learning object again"() {
    given: "$JAMES is assigned to quiz $javaQuiz"
      UserAssignationDto assignation =
        userAssignationApiFacade.createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    and: "$JAMES starts learning object"
      userAssignationApiFacade.changeToInProgress(assignation.id)
    and: "$JAMES completes learning object"
      userResultApiFacade.saveUserResultProgressChanged(userResultApiFacade.getAllUserResults()[0].id, new ResultProgressChangedDto(16, ResultTypeDto.COMPLETED))
    when: "$JAMES completes learning object again $WEEK_LATER"
      timeProviderApiFacade.useFixedClock(WEEK_LATER)
      userResultApiFacade.saveUserResultProgressChanged(userResultApiFacade.getAllUserResults()[0].id, new ResultProgressChangedDto(16, ResultTypeDto.COMPLETED))
    then: "user result contains started date, result and completed date with time $WEEK_LATER"
      List<UserResultDto> result = userResultApiFacade.getAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 16, learningObjectId: javaQuiz.quizId, completedAt: WEEK_LATER,
          userId: james.userId, startedAt: NOW, userResultVisibilityType: UserResultVisibilityTypeDto.PRIVATE)])
  }

  def "Should not get user result if it is private"() {
    given: "$JAMES is assigned to quiz $javaQuiz"
      userAssignationApiFacade.createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    expect: "there are no public user results"
      userResultApiFacade.getPublicUserResults(PageInfoDto.DEFAULT) == []
  }

  def "Should get user result it user change result visibility to public"() {
    given: "$JAMES is assigned to quiz $javaQuiz"
      userAssignationApiFacade.createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    when: "$JAMES changes result visibility to public"
      userResultApiFacade.changeUserResultVisibility(userResultApiFacade.getAllUserResults()[0].id, UserResultVisibilityTypeDto.PUBLIC)
    then: "gets $JAMES result as public result"
      List<UserResultDto> result = userResultApiFacade.getPublicUserResults(PageInfoDto.DEFAULT)
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 0, learningObjectId: javaQuiz.quizId, completedAt: null,
          userId: james.userId, startedAt: null, userResultVisibilityType: UserResultVisibilityTypeDto.PUBLIC)])
    when: "$JAMES changes result visibility to private"
      userResultApiFacade.changeUserResultVisibility(userResultApiFacade.getAllUserResults()[0].id, UserResultVisibilityTypeDto.PRIVATE)
    then: "there are no public results"
      userResultApiFacade.getPublicUserResults(PageInfoDto.DEFAULT) == []
  }
}
