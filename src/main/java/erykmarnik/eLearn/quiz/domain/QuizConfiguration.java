package erykmarnik.eLearn.quiz.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class QuizConfiguration {
  @Bean
  QuizFacade quizFacade(QuizRepository quizRepository, QuizCreator quizCreator, ApplicationEventPublisher applicationEventPublisher) {
    return new QuizFacade(quizRepository, quizCreator, new QuizEventPublisher(applicationEventPublisher));
  }

  QuizFacade quizFacade(QuizCreator quizCreator, ApplicationEventPublisher applicationEventPublisher) {
    return new QuizFacade(new InMemoryQuizRepository(), quizCreator, new QuizEventPublisher(applicationEventPublisher));
  }
}
