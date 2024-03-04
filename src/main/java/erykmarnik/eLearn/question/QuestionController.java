package erykmarnik.eLearn.question;

import erykmarnik.eLearn.question.domain.QuestionFacade;
import erykmarnik.eLearn.question.dto.*;
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
@RequestMapping("/api/question")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class QuestionController {
  QuestionFacade questionFacade;

  @Autowired
  public QuestionController(QuestionFacade questionFacade) {
    this.questionFacade = questionFacade;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/create/closeQuestion")
  ResponseEntity<CloseQuestionDto> createCloseQuestion(@RequestBody CreateCloseQuestionDto createCloseQuestion) {
    return ResponseEntity.ok(questionFacade.createCloseQuestion(createCloseQuestion));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/create/openQuestion")
  ResponseEntity<OpenQuestionDto> createOpenQuestion(@RequestBody CreateOpenQuestionDto createOpenQuestion) {
    return ResponseEntity.ok(questionFacade.createOpenQuestion(createOpenQuestion));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/edit/closeQuestion/{questionId}")
  ResponseEntity<CloseQuestionDto> editCloseQuestion(@RequestBody EditQuestionDto editQuestion, @PathVariable Long questionId) {
    return ResponseEntity.ok(questionFacade.editCloseQuestion(editQuestion, questionId));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/edit/openQuestion/{questionId}")
  ResponseEntity<OpenQuestionDto> editOpenQuestion(@RequestBody EditQuestionDto editQuestion, @PathVariable Long questionId) {
    return ResponseEntity.ok(questionFacade.editOpenQuestion(editQuestion, questionId));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/closeQuestion/{questionId}")
  ResponseEntity<CloseQuestionDto> getCloseQuestion(@PathVariable Long questionId) {
    return ResponseEntity.ok(questionFacade.getCloseQuestion(questionId));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/openQuestion/{questionId}")
  ResponseEntity<OpenQuestionDto> getOpenQuestion(@PathVariable Long questionId) {
    return ResponseEntity.ok(questionFacade.getOpenQuestion(questionId));
  }

  @GetMapping("/cleanup")
  @Hidden
  void cleanup() {
    questionFacade.deleteAll();
  }
}
