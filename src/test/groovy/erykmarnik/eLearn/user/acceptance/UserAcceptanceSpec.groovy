package erykmarnik.eLearn.user.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.user.dto.UserDto
import erykmarnik.eLearn.user.dto.UserRoleDto

class UserAcceptanceSpec extends IntegrationSpec implements UserSample {
  UserApiFacade userApiFacade

  def setup() {
    userApiFacade = new UserApiFacade(mockMvc, objectMapper)
  }

  def "Should create new student"() {
    when: "creates new student"
      UserDto result = userApiFacade.registerStudent(createNewUser(username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com"))
    then: "student is created"
      equalsUser([result], [createUser(userId: result.userId, username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com")])
  }

  def "Should create new admin"() {
    when: "creates new admin user"
      UserDto result = userApiFacade.registerAdmin(createNewUser(username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com"))
    then: "admin is created"
      equalsUser([result], [createUser(userId: result.userId, username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com", userRole: UserRoleDto.ADMIN)])
  }

  def cleanup() {
    userApiFacade.cleanup()
  }
}
