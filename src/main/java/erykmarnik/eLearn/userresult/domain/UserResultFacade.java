package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.userassignation.dto.UserAssignationCreatedEvent;
import erykmarnik.eLearn.userassignation.dto.UserStartedEvent;
import erykmarnik.eLearn.userresult.dto.ResultProgressChangedDto;
import erykmarnik.eLearn.userresult.dto.UserResultDto;
import erykmarnik.eLearn.userresult.exception.UserResultNotFoundException;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserResultFacade {
  UserResultRepository userResultRepository;
  UserResultCreator userResultCreator;
  InstantProvider instantProvider;
  UserResultProgressHandler progressHandler;

  @EventListener
  public void onUserResultInitialization(UserAssignationCreatedEvent event) {
    log.info("initializing user result");
    userResultRepository.save(userResultCreator.createUserResult(event));
  }

  public UserResultDto onUserResultProgressChangedSave(UUID id, ResultProgressChangedDto resultProgressChanged) {
    UserResult userResult = userResultRepository.findUserResultById(id)
        .orElseThrow(() -> new UserResultNotFoundException("User result not found"));
    log.info("saving user progress with id {}", id);
    return userResultRepository.save(progressHandler.changeProgress(userResult, resultProgressChanged.getResult(),
        resultProgressChanged.getResultType())).dto();
  }

  @EventListener
  public void onUserResultLearningObjectStart(UserStartedEvent event) {
    UserResult userResult = userResultRepository.findUserResultByUserIdAndLearningObjectId(event.getUserId(), event.getLearningObjectId())
        .orElseThrow(() -> new UserResultNotFoundException("User result not found"));
    log.info("user {} has started learning object {}", event.getUserId(), event.getLearningObjectId());
    userResultRepository.save(updateUserResultStartedAt(userResult)).dto();
  }

  public List<UserResultDto> findAllUserResults() {
    log.info("finding all user results");
    return userResultRepository.findAll().stream()
        .map(UserResult::dto)
        .collect(Collectors.toList());
  }

  public void cleanup() {
    userResultRepository.deleteAll();
  }

  private UserResult updateUserResultStartedAt(UserResult userResult) {
    if(userResult.dto().getStartedAt() == null) {
      log.info("updating user result started at");
      return userResult.toBuilder()
          .startedAt(instantProvider.now())
          .build();
    } else {
      log.info("not updating user result started at");
      return userResult;
    }
  }
}
