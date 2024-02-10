package erykmarnik.eLearn.user.domain

import erykmarnik.eLearn.user.dto.UserDto
import spock.lang.Specification
import spock.lang.Unroll
import erykmarnik.eLearn.user.exception.NotExistingUserException
import erykmarnik.eLearn.user.exception.AlreadyTakenException

class UserSpec extends Specification implements UserSample {
  UserFacade userFacade = new UserConfiguration().userFacade()

  def "Should create new user"() {
    when: "creates new user"
      UserDto result = userFacade.createStudent(createNewUser(username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com"))
    then: "user is created"
      equalsUser([result], [createUser(userId: result.userId, username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com")])
  }

  def "Should get all users"() {
    given: "there is user janeDoe"
      UserDto janeDoe = userFacade.createStudent(createNewUser(username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com"))
    and: "there is user mikeSmith"
      UserDto mikeSmith = userFacade.createStudent(createNewUser(username: "mikeSmith", password: "mikeSmithPswd", email: "mikeSmith@gmail.com"))
    when: "asks for all users"
      List<UserDto> users = userFacade.getAllUsers()
    then: "gets all existing users"
      equalsUser(users, [createUser(userId: janeDoe.userId, username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com"),
      createUser(userId: mikeSmith.userId, username: "mikeSmith", password: "mikeSmithPswd", email: "mikeSmith@gmail.com")])
  }

  @Unroll
  def "Should find user by username"() {
    given: "there is user janeDoe"
      userFacade.createStudent(createNewUser(username: USERNAME, password: PASSWORD, email: EMAIL))
    when: "asks for user with $USERNAME"
      UserDto user = userFacade.findUserByUsername(USERNAME)
    then: "get user with such username"
      equalsUser([user], [createUser(userId: user.userId, username: USERNAME, password: PASSWORD, email: EMAIL)])
    where:
      USERNAME    | PASSWORD        | EMAIL
      "janeDoe"   | "janeDoePswd"   | "janeDoe@gmail.com"
      "mikeSmith" | "mikeSmithPswd" | "mikeSmith@gmail.com"
      "johnSmith" | "johnSmithPswd" | "johnSmith@gmail.com"
  }

  def "Should get error if try to find user that does not exist"() {
    when: "asks for user that does not exist"
      userFacade.findUserByUsername("notExistingUser")
    then: "get error of not existing user"
      thrown(NotExistingUserException)
  }

  def "Should not create user if there is user with such username or email"() {
    given: "there is user janeDoe"
      userFacade.createStudent(createNewUser(username: "janeDoe", password: "userPswd", email: "jane@gmail.com"))
    when: "creates user with the same email or username"
      userFacade.createStudent(createNewUser(username: NEW_USER_USERNAME, password: "userPswd", email: NEW_USER_EMAIL))
    then: "gets error of already taken username or email"
      thrown(AlreadyTakenException)
    where:
      NEW_USER_USERNAME | NEW_USER_EMAIL
      "janeDoe"         | "jane@gmail.com"
      "janeDoe123"      | "jane@gmail.com"
      "janeDoe"         | "janeDoe@gmail.com"
  }
}
