package erykmarnik.eLearn.userresult.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.user.dto.UserDto
import erykmarnik.eLearn.userassignation.dto.UserAssignationDto
import erykmarnik.eLearn.userassignation.samples.UserAssignationSample
import erykmarnik.eLearn.userresult.dto.ResultProgressChangedDto
import erykmarnik.eLearn.userresult.dto.ResultTypeDto
import erykmarnik.eLearn.userresult.dto.UserResultDto
import erykmarnik.eLearn.userresult.samples.UserResultSample
import erykmarnik.eLearn.utils.samples.InstantSample

class UserResultAcceptanceSpec extends IntegrationSpec implements UserResultSample, InstantSample, UserSample, QuizSample,
    UserAssignationSample {
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
    api.user().cleanup()
    api.userAssignation().cleanup()
    api.userResult().cleanup()
    api.timeProvider().useSystemClock()
  }

  def "Should create new user result when user is assigned to learning object"() {
    when: "$JAMES is assigned to quiz $javaQuiz"
      api.userAssignation().createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    then: "there is new user result for $JAMES"
      List<UserResultDto> result = api.userResult().getAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 0, learningObjectId: javaQuiz.quizId, completedAt: null,
          userId: james.userId, startedAt: null)])
  }

  def "Should update user result with start date when user starts learning object"() {
    given: "$JAMES is assigned to quiz $javaQuiz"
      UserAssignationDto assignation =
          api.userAssignation().createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    when: "$JAMES starts learning object"
      api.userAssignation().changeToInProgress(assignation.id)
    then: "user result contains started date"
      List<UserResultDto> result = api.userResult().getAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 0, learningObjectId: javaQuiz.quizId, completedAt: null,
          userId: james.userId, startedAt: NOW)])
  }

  def "Should update user result with result when user starts learning object and want to save it"() {
    given: "$JAMES is assigned to quiz $javaQuiz"
      UserAssignationDto assignation =
          api.userAssignation().createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    and: "$JAMES starts learning object"
      api.userAssignation().changeToInProgress(assignation.id)
    when: "$JAMES makes some progress and save it"
      api.userResult().saveUserResultProgressChanged(api.userResult().getAllUserResults()[0].id, new ResultProgressChangedDto(12, ResultTypeDto.IN_PROGRESS))
    then: "user result contains started date and result"
      List<UserResultDto> result = api.userResult().getAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 12, learningObjectId: javaQuiz.quizId, completedAt: null,
          userId: james.userId, startedAt: NOW)])
  }

  def "Should update user result with completed date when user completes learning object"() {
    given: "$JAMES is assigned to quiz $javaQuiz"
      UserAssignationDto assignation =
        api.userAssignation().createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    and: "$JAMES starts learning object"
      api.userAssignation().changeToInProgress(assignation.id)
    when: "$JAMES completes learning object"
      api.userResult().saveUserResultProgressChanged(api.userResult().getAllUserResults()[0].id, new ResultProgressChangedDto(16, ResultTypeDto.COMPLETED))
    then: "user result contains started date, result and completed date"
      List<UserResultDto> result = api.userResult().getAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 16, learningObjectId: javaQuiz.quizId, completedAt: NOW,
          userId: james.userId, startedAt: NOW)])
  }

  def "Should update user result with completed date when user completes learning object again"() {
    given: "$JAMES is assigned to quiz $javaQuiz"
      UserAssignationDto assignation =
        api.userAssignation().createUserAssignation(createNewUserAssignation(userId: james.userId, learningObjectId: javaQuiz.quizId))
    and: "$JAMES starts learning object"
      api.userAssignation().changeToInProgress(assignation.id)
    and: "$JAMES completes learning object"
      api.userResult().saveUserResultProgressChanged(api.userResult().getAllUserResults()[0].id, new ResultProgressChangedDto(16, ResultTypeDto.COMPLETED))
    when: "$JAMES completes learning object again $WEEK_LATER"
      api.timeProvider().useFixedClock(WEEK_LATER)
      api.userResult().saveUserResultProgressChanged(api.userResult().getAllUserResults()[0].id, new ResultProgressChangedDto(16, ResultTypeDto.COMPLETED))
    then: "user result contains started date, result and completed date with time $WEEK_LATER"
      List<UserResultDto> result = api.userResult().getAllUserResults()
      equalsUserResult(result, [createUserResult(id: result[0].id, result: 16, learningObjectId: javaQuiz.quizId, completedAt: WEEK_LATER,
          userId: james.userId, startedAt: NOW)])
  }
}
