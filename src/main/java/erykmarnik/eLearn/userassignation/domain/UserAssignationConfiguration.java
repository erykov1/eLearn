package erykmarnik.eLearn.userassignation.domain;

import erykmarnik.eLearn.quiz.domain.QuizFacade;
import erykmarnik.eLearn.user.domain.UserFacade;
import erykmarnik.eLearn.utils.InstantProvider;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserAssignationConfiguration {
  @Bean
  UserAssignationFacade userAssignationFacade(InstantProvider instantProvider, UserAssignationRepository userAssignationRepository,
                                              UserFacade userFacade, QuizFacade quizFacade, ApplicationEventPublisher applicationEventPublisher) {
    return UserAssignationFacade.builder()
        .userAssignationCreator(new UserAssignationCreator(instantProvider))
        .userAssignationRepository(userAssignationRepository)
        .userFacade(userFacade)
        .quizFacade(quizFacade)
        .instantProvider(instantProvider)
        .userAssignationEventPublisher(new UserAssignationEventPublisher(applicationEventPublisher))
        .build();
  }

  UserAssignationFacade userAssignationFacade(InstantProvider instantProvider, UserFacade userFacade, QuizFacade quizFacade,
                                              ApplicationEventPublisher applicationEventPublisher) {
    return UserAssignationFacade.builder()
        .userAssignationCreator(new UserAssignationCreator(instantProvider))
        .userAssignationRepository(new InMemoryUserAssignationRepository())
        .userFacade(userFacade)
        .quizFacade(quizFacade)
        .instantProvider(instantProvider)
        .userAssignationEventPublisher(new UserAssignationEventPublisher(applicationEventPublisher))
        .build();
  }
}
