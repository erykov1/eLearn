package erykmarnik.eLearn.user.domain;

import erykmarnik.eLearn.user.dto.CreateUserDto;
import erykmarnik.eLearn.user.dto.UserDto;
import erykmarnik.eLearn.user.exception.AlreadyTakenException;
import erykmarnik.eLearn.user.exception.NotExistingUserException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserFacade {
  UserRepository userRepository;
  BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserDto createStudent(CreateUserDto createUser) {
    checkIfEmailIsTaken(createUser.getEmail());
    checkIfUsernameIsTaken(createUser.getUsername());
    User saveUser = User.builder()
        .username(createUser.getUsername())
        .password(bCryptPasswordEncoder.encode(createUser.getPassword()))
        .email(createUser.getEmail())
        .userRole(UserRole.STUDENT)
        .build();
    return userRepository.save(saveUser).dto();
  }

  public UserDto createAdmin(CreateUserDto createAdmin) {
    checkIfEmailIsTaken(createAdmin.getEmail());
    checkIfUsernameIsTaken(createAdmin.getUsername());
    User saveAdmin = User.builder()
        .username(createAdmin.getUsername())
        .password(bCryptPasswordEncoder.encode(createAdmin.getPassword()))
        .email(createAdmin.getEmail())
        .userRole(UserRole.ADMIN)
        .build();
    return userRepository.save(saveAdmin).dto();
  }

  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream()
        .map(User::dto)
        .collect(Collectors.toList());
  }

  public UserDto findUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .map(User::dto)
        .orElseThrow(() -> new NotExistingUserException("User does not exist"));
  }

  private void checkIfUsernameIsTaken(String username) {
    userRepository.findByUsername(username)
        .ifPresent(user -> {
          throw new AlreadyTakenException("Username is already taken");
        });
  }

  private void checkIfEmailIsTaken(String email) {
    userRepository.findByEmail(email)
        .ifPresent(user -> {
          throw new AlreadyTakenException("Email is already taken");
        });
  }

  public void deleteAll() {
    userRepository.deleteAll();
  }
}
