package erykmarnik.eLearn.userresult;

import erykmarnik.eLearn.userresult.domain.UserResultFacade;
import erykmarnik.eLearn.userresult.dto.ResultProgressChangedDto;
import erykmarnik.eLearn.userresult.dto.UserResultDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/userResult")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserResultController {
  UserResultFacade userResultFacade;

  @Autowired
  public UserResultController(UserResultFacade userResultFacade) {
    this.userResultFacade = userResultFacade;
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<UserResultDto> saveUserResult(@PathVariable("id") UUID id, @RequestBody ResultProgressChangedDto resultProgressChanged) {
    return ResponseEntity.ok(userResultFacade.onUserResultProgressChangedSave(id, resultProgressChanged));
  }

  @GetMapping("/all")
  @PreAuthorize("haRole('ADMIN')")
  @Hidden
  ResponseEntity<List<UserResultDto>> getUserResults() {
    return ResponseEntity.ok(userResultFacade.findAllUserResults());
  }

  @Hidden
  @GetMapping("/cleanup")
  @PreAuthorize("haRole('ADMIN')")
  void cleanup() {
    userResultFacade.cleanup();
  }
}
