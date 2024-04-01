package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.userresult.dto.ResultTypeDto;
import erykmarnik.eLearn.userresult.exception.InvalidUserResultTypeException;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserResultProgressHandler {
  InstantProvider instantProvider;
  List<UserResultHandler> userResultHandlers;

  @Autowired
  public UserResultProgressHandler(InstantProvider instantProvider) {
    this.instantProvider = instantProvider;
    this.userResultHandlers = List.of(new UserResultInProgressHandler(), new UserResultCompleteHandler(instantProvider));
  }

  UserResult changeProgress(UserResult userResult, Integer result, ResultTypeDto resultType) {
    UserResultHandler resultHandler = userResultHandlers.stream()
        .filter(handler -> handler.supports(resultType))
        .findFirst()
        .orElseThrow(() -> new InvalidUserResultTypeException("Invalid result type"));
    return resultHandler.saveProgress(userResult, result);
  }
}
