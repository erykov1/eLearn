package erykmarnik.eLearn.user.domain;

import erykmarnik.eLearn.user.dto.UserDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
  Long userId;
  String username;
  String password;
  String email;
  @Enumerated(EnumType.STRING)
  UserRole userRole;

  UserDto dto() {
    return UserDto.builder()
        .userId(userId)
        .username(username)
        .password(password)
        .email(email)
        .userRole(userRole.dto())
        .build();
  }
}
