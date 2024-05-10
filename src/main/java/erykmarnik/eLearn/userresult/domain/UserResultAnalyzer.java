package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.userresult.dto.UserResultSummaryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserResultAnalyzer {
  List<UserResultSummaryDto> getUserResultsSummary(List<UserResult> userResults) {
    return userResults.stream()
        .map(userResult -> UserResultSummaryDto.builder()
            .timeSpent(calculateTimeSpent(userResult))
            .startedAt(userResult.dto().getStartedAt())
            .completedAt(userResult.dto().getCompletedAt())
            .result(userResult.dto().getResult())
            .learningObjectId(userResult.dto().getLearningObjectId())
            .build()
        )
        .collect(Collectors.toList());
  }

  private long calculateTimeSpent(UserResult userResult) {
    if (userResult.dto().getCompletedAt() == null || userResult.dto().getStartedAt() == null) {
      return 0L;
    }
    return Duration.between(userResult.dto().getStartedAt(), userResult.dto().getCompletedAt()).toMinutes();
  }
}
