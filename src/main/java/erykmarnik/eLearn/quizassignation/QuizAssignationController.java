package erykmarnik.eLearn.quizassignation;

import erykmarnik.eLearn.quizassignation.domain.QuizAssignationFacade;
import erykmarnik.eLearn.quizassignation.dto.CreateQuizAssignationDto;
import erykmarnik.eLearn.quizassignation.dto.DeleteQuizAssignationDto;
import erykmarnik.eLearn.quizassignation.dto.QuizAssignationDto;
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
@RequestMapping("/api/quizAssignation")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class QuizAssignationController {
  QuizAssignationFacade quizAssignationFacade;

  @Autowired
  public QuizAssignationController(QuizAssignationFacade quizAssignationFacade) {
    this.quizAssignationFacade = quizAssignationFacade;
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<QuizAssignationDto> createQuizAssignation(@RequestBody CreateQuizAssignationDto createQuizAssignation) {
    return ResponseEntity.ok(quizAssignationFacade.createQuizAssignation(createQuizAssignation));
  }

  @DeleteMapping("/delete/{quizId}/{questionId}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<Void> deleteQuizAssignation(@PathVariable UUID quizId, @PathVariable Long questionId) {
    quizAssignationFacade.deleteQuizAssignation(quizId, questionId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/getQuestions/{quizId}")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<List<Long>> getAllQuestionsAssignedToQuiz(@PathVariable UUID quizId) {
    return ResponseEntity.ok(quizAssignationFacade.findAllAssignedQuestionsToQuiz(quizId));
  }

  @GetMapping("/get/{assignationId}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<QuizAssignationDto> getQuizAssignation(@PathVariable Long assignationId) {
    return ResponseEntity.ok(quizAssignationFacade.findByQuizAssignation(assignationId));
  }

  @GetMapping("/cleanup")
  @Hidden
  void cleanup() {
    quizAssignationFacade.cleanup();
  }
}
