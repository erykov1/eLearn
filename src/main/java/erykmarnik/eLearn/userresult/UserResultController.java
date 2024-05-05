package erykmarnik.eLearn.userresult;

import erykmarnik.eLearn.userresult.domain.UserResultFacade;
import erykmarnik.eLearn.userresult.dto.PageInfoDto;
import erykmarnik.eLearn.userresult.dto.ResultProgressChangedDto;
import erykmarnik.eLearn.userresult.dto.UserResultDto;
import erykmarnik.eLearn.userresult.dto.UserResultVisibilityTypeDto;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
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

  @GetMapping("/export/{userId}")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<Void> exportToCsv(@PathVariable("userId") Long userId, HttpServletResponse httpServletResponse) throws IOException {
    httpServletResponse.setContentType("text/csv");
    httpServletResponse.addHeader("Content-Disposition", "attachment; filename=\"userResults.csv\"");
    userResultFacade.exportToCsv(userId, httpServletResponse.getOutputStream());
    return ResponseEntity.ok().build();
  }

  @Hidden
  @GetMapping("/cleanup")
  @PreAuthorize("haRole('ADMIN')")
  void cleanup() {
    userResultFacade.cleanup();
  }
}
