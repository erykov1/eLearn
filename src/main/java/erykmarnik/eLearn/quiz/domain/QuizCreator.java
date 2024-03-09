package erykmarnik.eLearn.quiz.domain;

import erykmarnik.eLearn.quiz.dto.CreateQuizDto;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class QuizCreator {
  InstantProvider instantProvider;

  @Autowired
  QuizCreator(InstantProvider instantProvider) {
    this.instantProvider = instantProvider;
  }

  Quiz createQuiz(CreateQuizDto createQuiz) {
    return Quiz.builder()
        .quizId(UUID.randomUUID())
        .quizName(createQuiz.getQuizName())
        .createdAt(instantProvider.now())
        .createdBy(createQuiz.getCreatedBy())
        .quizDifficulty(QuizDifficulty.valueOf(createQuiz.getQuizDifficulty().name()))
        .build();
  }
}
