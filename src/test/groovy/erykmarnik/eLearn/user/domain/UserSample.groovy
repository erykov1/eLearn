package erykmarnik.eLearn.user.domain

import erykmarnik.eLearn.user.dto.CreateUserDto
import erykmarnik.eLearn.user.dto.UserDto
import erykmarnik.eLearn.user.dto.UserRoleDto
import erykmarnik.eLearn.userassignation.dto.CreateUserAssignationDto

trait UserSample {
  static final Long FAKE_USER_ID = 11L
  static final Long USER_ID = 1L

  private static Map<String, Object> DEFAULT_USER_DATA = [
      userId: USER_ID,
      username: 'janeDoe',
      password: 'janeDoePswd',
      email: 'janeDoe@gmail.com',
      userRole: UserRoleDto.STUDENT
  ] as Map<String, Object>

  UserDto createUser(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_USER_DATA + changes
    UserDto.builder()
        .userId(changesWithDefaults.userId as Long)
        .username(changesWithDefaults.username as String)
        .password(changesWithDefaults.password as String)
        .email(changesWithDefaults.email as String)
        .userRole(changesWithDefaults.userRole as UserRoleDto)
        .build()
  }

  CreateUserDto createNewUser(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_USER_DATA + changes
    CreateUserDto.builder()
        .username(changesWithDefaults.username as String)
        .password(changesWithDefaults.password as String)
        .email(changesWithDefaults.email as String)
        .build()
  }

  void equalsUser(List<UserDto> result, List<UserDto> expected) {
    def comparator = Comparator.comparing(UserDto::getUsername)
    result.sort(comparator)
    expected.sort(comparator)
    assert result.userId == expected.userId
    assert result.username == expected.username
    assert result.userRole == expected.userRole
    assert result.email == expected.email
  }

  static final CreateUserDto ADAM = CreateUserDto.builder()
      .username("adamSmith")
      .password("adamPswd")
      .email("adam@gmail.com")
      .build()


  static final CreateUserDto JAMES = CreateUserDto.builder()
      .username("jamesSmith")
      .password("jamesPswd")
      .email("james@gmail.com")
      .build()
}