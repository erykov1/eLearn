package erykmarnik.eLearn.quizassignation.domain;

import erykmarnik.eLearn.question.domain.QuestionFacade;
import erykmarnik.eLearn.quiz.domain.QuizFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class QuizAssignationConfiguration {
  @Bean
  QuizAssignationFacade quizAssignationFacade(QuizAssignationRepository quizAssignationRepository, QuizFacade quizFacade, QuestionFacade questionFacade,
                        QuizAssignationCreator quizAssignationCreator) {
    return QuizAssignationFacade.builder()
        .quizAssignationRepository(quizAssignationRepository)
        .quizFacade(quizFacade)
        .questionFacade(questionFacade)
        .quizAssignationCreator(quizAssignationCreator)
        .build();
  }

  QuizAssignationFacade quizAssignationFacade(QuizFacade quizFacade, QuestionFacade questionFacade, QuizAssignationCreator quizAssignationCreator) {
    return QuizAssignationFacade.builder()
        .quizAssignationRepository(new InMemoryQuizAssignationRepository())
        .quizFacade(quizFacade)
        .questionFacade(questionFacade)
        .quizAssignationCreator(quizAssignationCreator)
        .build();
  }
}
