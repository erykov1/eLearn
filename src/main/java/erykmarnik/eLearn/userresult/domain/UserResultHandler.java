package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.userresult.dto.ResultTypeDto;

interface UserResultHandler {
  boolean supports(ResultTypeDto resultType);
  UserResult saveProgress(UserResult userResult, Integer result);
}
