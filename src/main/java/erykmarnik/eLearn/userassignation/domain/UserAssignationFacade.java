package erykmarnik.eLearn.userassignation.domain;

import erykmarnik.eLearn.quiz.domain.QuizFacade;
import erykmarnik.eLearn.user.domain.UserFacade;
import erykmarnik.eLearn.userassignation.dto.CreateUserAssignationDto;
import erykmarnik.eLearn.userassignation.dto.UserAssignationCreatedEvent;
import erykmarnik.eLearn.userassignation.dto.UserAssignationDto;
import erykmarnik.eLearn.userassignation.dto.UserStartedEvent;
import erykmarnik.eLearn.userassignation.exception.UserAlreadyAssignedException;
import erykmarnik.eLearn.userassignation.exception.UserAssignationNotFoundException;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

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
    userAssignationRepository.findUserAssignationByUserIdAndLearningObjectId(
        createUserAssignation.getUserId(),
        createUserAssignation.getLearningObjectId()).ifPresent(userAssignation -> {
      throw new UserAlreadyAssignedException("User is already assigned");
    });
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

  public void cleanup() {
    userAssignationRepository.deleteAll();
  }
}
