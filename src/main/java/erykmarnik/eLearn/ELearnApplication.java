package erykmarnik.eLearn;

import erykmarnik.eLearn.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class ELearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(ELearnApplication.class, args);
	}

}
