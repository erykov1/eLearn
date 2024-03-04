package erykmarnik.eLearn.question.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class QuestionConfiguration {
  @Bean
  QuestionFacade questionFacade(OpenQuestionRepository openQuestionRepository, CloseQuestionRepository closeQuestionRepository) {
    return QuestionFacade.builder()
        .questionCreator(new QuestionCreator())
        .openQuestionRepository(openQuestionRepository)
        .closeQuestionRepository(closeQuestionRepository)
        .questionEditorHandler(new QuestionEditorHandler())
        .build();
  }

  QuestionFacade questionFacade() {
    return QuestionFacade.builder()
        .questionCreator(new QuestionCreator())
        .openQuestionRepository(new InMemoryOpenQuestionRepository())
        .closeQuestionRepository(new InMemoryCloseQuestionRepository())
        .questionEditorHandler(new QuestionEditorHandler())
        .build();
  }
}
