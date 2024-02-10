package erykmarnik.eLearn.user.domain

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.user.dto.UserDto
import erykmarnik.eLearn.user.dto.UserRoleDto

class UserAcceptanceSpec extends IntegrationSpec implements UserSample {
  def "Should create new student"() {
    when: "creates new student"
      UserDto result = api.user().registerStudent(createNewUser(username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com"))
    then: "student is created"
      equalsUser([result], [createUser(userId: result.userId, username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com")])
  }

  def "Should create new admin"() {
    when: "creates new admin user"
      UserDto result = api.user().registerAdmin(createNewUser(username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com"))
    then: "admin is created"
      equalsUser([result], [createUser(userId: result.userId, username: "janeDoe", password: "janeDoePswd", email: "janeDoe@gmail.com", userRole: UserRoleDto.ADMIN)])
  }

  def cleanup() {
    api.user().cleanup()
  }
}
