package erykmarnik.eLearn.userresult.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ResultTypeDto {
  IN_PROGRESS,
  COMPLETED
}
