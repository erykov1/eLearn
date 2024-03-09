package erykmarnik.eLearn.quiz.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class QuizConfiguration {
  @Bean
  QuizFacade quizFacade(QuizRepository quizRepository, QuizCreator quizCreator) {
    return new QuizFacade(quizRepository, quizCreator);
  }

  QuizFacade quizFacade(QuizCreator quizCreator) {
    return new QuizFacade(new InMemoryQuizRepository(), quizCreator);
  }
}
