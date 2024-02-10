package erykmarnik.eLearn.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
class UserConfiguration {
  UserFacade userFacade() {
    return new UserFacade(new InMemoryUserRepository(), new BCryptPasswordEncoder());
  }

  @Bean
  UserFacade userFacade(UserRepository userRepository) {
    return new UserFacade(userRepository, new BCryptPasswordEncoder());
  }
}
