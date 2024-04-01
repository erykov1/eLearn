package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.userresult.dto.ResultTypeDto;
import erykmarnik.eLearn.userresult.dto.UserResultDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserResultInProgressHandler implements UserResultHandler {

  @Override
  public boolean supports(ResultTypeDto resultType) {
    return resultType.equals(ResultTypeDto.IN_PROGRESS);
  }

  @Override
  public UserResult saveProgress(UserResult userResult, Integer result) {
    return userResult.toBuilder()
        .result(result)
        .build();
  }
}
