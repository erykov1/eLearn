package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.userresult.dto.ResultTypeDto;
import erykmarnik.eLearn.userresult.dto.UserResultDto;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserResultCompleteHandler implements UserResultHandler {
  InstantProvider instantProvider;

  @Autowired
  UserResultCompleteHandler(InstantProvider instantProvider) {
    this.instantProvider = instantProvider;
  }

  @Override
  public boolean supports(ResultTypeDto resultType) {
    return resultType.equals(ResultTypeDto.COMPLETED);
  }

  @Override
  public UserResult saveProgress(UserResult userResult, Integer result) {
    return userResult.toBuilder()
        .completedAt(instantProvider.now())
        .result(result)
        .build();
  }
}
