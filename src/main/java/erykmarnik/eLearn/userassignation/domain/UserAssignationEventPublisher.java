package erykmarnik.eLearn.userassignation.domain;

import erykmarnik.eLearn.userassignation.dto.UserAssignationCreatedEvent;
import erykmarnik.eLearn.userassignation.dto.UserStartedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAssignationEventPublisher {
  ApplicationEventPublisher applicationEventPublisher;

  public void emmitUserAssignationCreatedEvent(UserAssignationCreatedEvent event) {
    applicationEventPublisher.publishEvent(event);
  }

  public void emmitUserStartedLearningObjectEvent(UserStartedEvent event) {
    applicationEventPublisher.publishEvent(event);
  }
}
