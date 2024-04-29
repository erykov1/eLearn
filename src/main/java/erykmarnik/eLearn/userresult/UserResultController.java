package erykmarnik.eLearn.userresult;

import erykmarnik.eLearn.userresult.domain.UserResultFacade;
import erykmarnik.eLearn.userresult.dto.PageInfoDto;
import erykmarnik.eLearn.userresult.dto.ResultProgressChangedDto;
import erykmarnik.eLearn.userresult.dto.UserResultDto;
import erykmarnik.eLearn.userresult.dto.UserResultVisibilityTypeDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @PutMapping("/visibility/{id}")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<UserResultDto> changeUserResultVisibility(@PathVariable("id") UUID id, @RequestBody UserResultVisibilityTypeDto userResultVisibilityType) {
    return ResponseEntity.ok(userResultFacade.changeUserResultVisibility(id, userResultVisibilityType));
  }

  @GetMapping("/public")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<Page<UserResultDto>> getPublicUsersResults(@ModelAttribute PageInfoDto pageable) {
    return ResponseEntity.ok(userResultFacade.getPublicUserResults(pageable));
  }

  @Hidden
  @GetMapping("/cleanup")
  @PreAuthorize("haRole('ADMIN')")
  void cleanup() {
    userResultFacade.cleanup();
  }
}
