package erykmarnik.eLearn.userassignation;

import erykmarnik.eLearn.quizassignation.dto.CreateQuizAssignationDto;
import erykmarnik.eLearn.userassignation.domain.UserAssignationFacade;
import erykmarnik.eLearn.userassignation.dto.CreateUserAssignationDto;
import erykmarnik.eLearn.userassignation.dto.UserAssignationDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userAssignation")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserAssignationController {
  UserAssignationFacade userAssignationFacade;

  @Autowired
  UserAssignationController(UserAssignationFacade userAssignationFacade) {
    this.userAssignationFacade = userAssignationFacade;
  }

  @PostMapping("/create")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<UserAssignationDto> createUserAssignation(@RequestBody CreateUserAssignationDto createUserAssignation) {
    return ResponseEntity.ok(userAssignationFacade.createAssignation(createUserAssignation));
  }

  @PutMapping("/inProgress/{id}")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<UserAssignationDto> changeToInProgress(@PathVariable Long id) {
    return ResponseEntity.ok(userAssignationFacade.changeToInProgress(id));
  }

  @PutMapping("/completed/{id}")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<UserAssignationDto> changeToCompleted(@PathVariable Long id) {
    return ResponseEntity.ok(userAssignationFacade.changeToCompleted(id));
  }

  @GetMapping("/cleanup")
  @Hidden
  void cleanup() {
    userAssignationFacade.cleanup();
  }
}
