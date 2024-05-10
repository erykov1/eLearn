package erykmarnik.eLearn.userresult.domain;

import erykmarnik.eLearn.userassignation.dto.UserAssignationCreatedEvent;
import erykmarnik.eLearn.userassignation.dto.UserStartedEvent;
import erykmarnik.eLearn.userresult.dto.*;
import erykmarnik.eLearn.userresult.exception.UserResultNotFoundException;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

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

  public UserResultDto changeUserResultVisibility(UUID id, UserResultVisibilityTypeDto userResultVisibilityType) {
    UserResult userResult = userResultRepository.findUserResultById(id).orElseThrow(() ->
        new UserResultNotFoundException("User result not found"));
    log.info("changing user result visibility with id {}", id);
    return userResultRepository.save(userResult.changeVisibility(userResultVisibilityType)).dto();
  }

  public Page<UserResultDto> getPublicUserResults(PageInfoDto pageable) {
    requireNonNull(pageable, "pageable must not be null");
    log.info("getting public user results");
    PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    return userResultRepository.findAllUserResultsByUserResultVisibilityType(UserResultVisibilityType.PUBLIC, pageRequest).map(UserResult::dto);
  }

  public void cleanup() {
    userResultRepository.deleteAll();
  }

  public void exportToCsv(Long userId, OutputStream outputStream) {
    List<UserResultDto> userResults = userResultRepository.findAllUserResultsByUserId(userId).stream()
        .map(UserResult::dto)
        .toList();
    log.info("Exporting {} user results to csv", userResults.size());
    new UserResultCsvExporter(outputStream).exportToCsv(userResults);
  }

  public List<UserResultSummaryDto> getPublicUserResultsSummary(UUID learningObjectId) {
    List<UserResult> publicUserResults = getPublicUserResultsForLearningObject(learningObjectId);
    log.info("getting all public users results summary {}", publicUserResults.size());
    return new UserResultAnalyzer().getUserResultsSummary(publicUserResults);
  }

  public List<UserResultSummaryDto> getUserResultsSummary(Long userId) {
    List<UserResult> userResults = userResultRepository.findAllUserResultsByUserId(userId);
    log.info("getting all user results summary {}", userResults.size());
    return new UserResultAnalyzer().getUserResultsSummary(userResults);
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

  private List<UserResult> getPublicUserResultsForLearningObject(UUID learningObjectId) {
    return userResultRepository.findAll().stream()
        .filter(userResult -> userResult.dto().getUserResultVisibilityType().equals(UserResultVisibilityTypeDto.PUBLIC))
        .filter(userResult -> userResult.dto().getLearningObjectId().equals(learningObjectId))
        .collect(Collectors.toList());
  }
}
