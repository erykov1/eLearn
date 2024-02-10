package erykmarnik.eLearn.user;

import erykmarnik.eLearn.user.domain.UserFacade;
import erykmarnik.eLearn.user.dto.CreateUserDto;
import erykmarnik.eLearn.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserController {
  UserFacade userFacade;

  @Autowired
  public UserController(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @PostMapping("/register")
  ResponseEntity<UserDto> registerStudent(@RequestBody CreateUserDto createUser) {
    return ResponseEntity.ok(userFacade.createStudent(createUser));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/register/admin")
  ResponseEntity<UserDto> registerAdmin(@RequestBody CreateUserDto createUser) {
    return ResponseEntity.ok(userFacade.createAdmin(createUser));
  }

  @GetMapping("/all")
  ResponseEntity<List<UserDto>> getAllUsers() {
    return ResponseEntity.ok(userFacade.getAllUsers());
  }

  @GetMapping("/cleanup")
  @Hidden
  void cleanup() {
    userFacade.deleteAll();
  }
}
