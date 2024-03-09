package erykmarnik.eLearn.quiz;

import erykmarnik.eLearn.quiz.domain.QuizFacade;
import erykmarnik.eLearn.quiz.dto.CreateQuizDto;
import erykmarnik.eLearn.quiz.dto.NewQuizNameDto;
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto;
import erykmarnik.eLearn.quiz.dto.QuizDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/quiz")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class QuizController {
  QuizFacade quizFacade;

  @Autowired
  public QuizController(QuizFacade quizFacade) {
    this.quizFacade = quizFacade;
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<QuizDto> createQuiz(@RequestBody CreateQuizDto createQuiz) {
    return ResponseEntity.ok(quizFacade.createQuiz(createQuiz));
  }

  @PutMapping("/difficulty/{quizId}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<QuizDto> changeQuizDifficulty(@PathVariable("quizId") UUID quizId, @RequestBody QuizDifficultyDto quizDifficulty) {
    return ResponseEntity.ok(quizFacade.changeQuizDifficulty(quizId, quizDifficulty));
  }

  @GetMapping("/{quizId}")
  @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
  ResponseEntity<QuizDto> findById(@PathVariable("quizId") UUID quizId) {
    return ResponseEntity.ok(quizFacade.findByQuizId(quizId));
  }

  @PutMapping("/name/{quizId}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<QuizDto> changeQuizName(@PathVariable("quizId") UUID quizId, @RequestBody String quizName) {
    return ResponseEntity.ok(quizFacade.changeQuizName(quizId, quizName));
  }

  @GetMapping("/cleanup")
  @Hidden
  void cleanup() {
    quizFacade.cleanup();
  }
}
