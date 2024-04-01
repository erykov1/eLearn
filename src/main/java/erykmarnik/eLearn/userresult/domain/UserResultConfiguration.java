package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.utils.InstantProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserResultConfiguration {
  @Bean
  UserResultFacade userResultFacade(InstantProvider instantProvider, UserResultRepository userResultRepository) {
    return UserResultFacade.builder()
        .instantProvider(instantProvider)
        .userResultCreator(new UserResultCreator())
        .userResultRepository(userResultRepository)
        .progressHandler(new UserResultProgressHandler(instantProvider))
        .build();
  }

  UserResultFacade userResultFacade(InstantProvider instantProvider) {
    return UserResultFacade.builder()
        .instantProvider(instantProvider)
        .userResultCreator(new UserResultCreator())
        .userResultRepository(new InMemoryUserResultRepository())
        .progressHandler(new UserResultProgressHandler(instantProvider))
        .build();
  }
}
