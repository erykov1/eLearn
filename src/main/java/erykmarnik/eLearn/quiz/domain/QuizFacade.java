package erykmarnik.eLearn.quiz.domain;

import erykmarnik.eLearn.quiz.dto.CreateQuizDto;
import erykmarnik.eLearn.quiz.dto.NewQuizNameDto;
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto;
import erykmarnik.eLearn.quiz.dto.QuizDto;
import erykmarnik.eLearn.quiz.exception.QuizNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizFacade {
  QuizRepository quizRepository;
  QuizCreator quizCreator;

  @Autowired
  public QuizFacade(QuizRepository quizRepository, QuizCreator quizCreator) {
    this.quizRepository = quizRepository;
    this.quizCreator = quizCreator;
  }

  public QuizDto createQuiz(CreateQuizDto createQuiz) {
    log.info("saving new quiz {}", createQuiz.getQuizName());
    return quizRepository.save(quizCreator.createQuiz(createQuiz)).dto();
  }

  public QuizDto changeQuizDifficulty(UUID quizId, QuizDifficultyDto quizDifficulty) {
    Quiz quiz = quizRepository.findByQuizId(quizId).orElseThrow(() -> new QuizNotFoundException("Quiz not found"));
    log.info("changing quiz difficulty from {} to {}", quiz.dto().getQuizDifficulty().name(), quizDifficulty.name());
    return quizRepository.save(quiz.changeQuizDifficulty(quizDifficulty)).dto();
  }

  public QuizDto findByQuizId(UUID quizId) {
    log.info("finding quiz with id {}", quizId);
    return quizRepository.findByQuizId(quizId).orElseThrow(() -> new QuizNotFoundException("Quiz not found")).dto();
  }

  public QuizDto changeQuizName(UUID quizId, String quizName) {
    Quiz quiz = quizRepository.findByQuizId(quizId).orElseThrow(() -> new QuizNotFoundException("Quiz not found"));
    log.info("changing quiz {} to {}", quiz.dto().getQuizName(), quizName);
    quiz = quiz.toBuilder()
        .quizName(quizName)
        .build();
    return quizRepository.save(quiz).dto();
  }

  public List<QuizDto> getAllQuizzes() {
    return quizRepository.findAll().stream()
        .map(Quiz::dto)
        .collect(Collectors.toList());
  }

  public void deleteQuizById(UUID quizId) {
    log.info("deleting quiz with id {}", quizId);
    quizRepository.findByQuizId(quizId).orElseThrow(() -> new QuizNotFoundException("Quiz not found"));
    quizRepository.deleteById(quizId);
  }

  public void cleanup() {
    quizRepository.deleteAll();
  }
}
