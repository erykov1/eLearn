package erykmarnik.eLearn;

import erykmarnik.eLearn.security.RsaKeyProperties;
import erykmarnik.eLearn.user.domain.UserFacade;
import erykmarnik.eLearn.user.dto.CreateUserDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableScheduling
public class ELearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(ELearnApplication.class, args);
	}

}
