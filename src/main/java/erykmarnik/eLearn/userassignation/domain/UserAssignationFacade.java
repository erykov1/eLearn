package erykmarnik.eLearn.userassignation.domain;

import erykmarnik.eLearn.quiz.domain.QuizFacade;
import erykmarnik.eLearn.user.domain.UserFacade;
import erykmarnik.eLearn.userassignation.dto.*;
import erykmarnik.eLearn.userassignation.exception.UserAlreadyAssignedException;
import erykmarnik.eLearn.userassignation.exception.UserAssignationNotFoundException;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAssignationFacade {
  UserAssignationCreator userAssignationCreator;
  UserAssignationRepository userAssignationRepository;
  UserFacade userFacade;
  QuizFacade quizFacade;
  InstantProvider instantProvider;
  UserAssignationEventPublisher userAssignationEventPublisher;

  public UserAssignationDto createAssignation(CreateUserAssignationDto createUserAssignation) {
    userFacade.checkIfExists(createUserAssignation.getUserId());
    quizFacade.checkIfExists(createUserAssignation.getLearningObjectId());
    checkIfCanAssign(createUserAssignation.getUserId(), createUserAssignation.getLearningObjectId());
    log.info("creating assignation with user {} to object {}", createUserAssignation.getUserId(), createUserAssignation.getLearningObjectId());
    userAssignationEventPublisher.emmitUserAssignationCreatedEvent(new UserAssignationCreatedEvent(createUserAssignation.getUserId(),
        createUserAssignation.getLearningObjectId()));
    return userAssignationRepository.save(userAssignationCreator.createUserAssignation(createUserAssignation)).dto();
  }

  public UserAssignationDto changeToInProgress(Long id) {
    UserAssignation userAssignation = userAssignationRepository.findUserAssignationById(id)
        .orElseThrow(() -> new UserAssignationNotFoundException("User assignation not found"));
    log.info("changing object {} status to in progress", id);
    userAssignationEventPublisher.emmitUserStartedLearningObjectEvent(new UserStartedEvent(userAssignation.dto().getUserId(),
        userAssignation.dto().getLearningObjectId()));
    return userAssignationRepository.save(userAssignation.changeToInProgress()).dto();
  }

  public UserAssignationDto changeToCompleted(Long id) {
    UserAssignation userAssignation = userAssignationRepository.findUserAssignationById(id)
        .orElseThrow(() -> new UserAssignationNotFoundException("User assignation not found"));
    log.info("changing object {} status to completed", id);
    userAssignation = userAssignation.toBuilder()
        .userAssignationStatus(UserAssignationStatus.COMPLETED)
        .completedAt(instantProvider.now())
        .build();
    return userAssignationRepository.save(userAssignation).dto();
  }

  public List<UserAssignationDto> findAllUserAssignations() {
    return userAssignationRepository.findAll().stream()
        .map(UserAssignation::dto)
        .collect(Collectors.toList());
  }

  public void cleanup() {
    userAssignationRepository.deleteAll();
  }

  private void checkIfCanAssign(Long userId, UUID learningObjectId) {
    userAssignationRepository.findUserAssignationByUserIdAndLearningObjectId(userId, learningObjectId).ifPresent(userAssignation -> {
      if (userAssignation.dto().getUserAssignationStatus() != UserAssignationStatusDto.COMPLETED) {
        throw new UserAlreadyAssignedException("User is already assigned");
      }
    });
  }
}
