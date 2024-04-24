package erykmarnik.eLearn.notification.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollForNewsDto {
  String userMail;

  @JsonCreator
  public EnrollForNewsDto(@JsonProperty("userMail") String userMail) {
    this.userMail = userMail;
  }
}
