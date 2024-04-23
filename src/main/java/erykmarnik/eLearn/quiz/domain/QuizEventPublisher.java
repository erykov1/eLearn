package erykmarnik.eLearn.quiz.domain;

import erykmarnik.eLearn.quiz.dto.ChangedDifficultyEvent;
import erykmarnik.eLearn.quiz.dto.ChangedLearningObjectNameEvent;
import erykmarnik.eLearn.quiz.dto.CreatedLearningObjectEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class QuizEventPublisher {
  ApplicationEventPublisher applicationEventPublisher;

  public void emmitLearningObject(CreatedLearningObjectEvent createdLearningObjectEvent) {
    applicationEventPublisher.publishEvent(createdLearningObjectEvent);
  }

  public void emmitChangedDifficulty(ChangedDifficultyEvent changedDifficultyEvent) {
    applicationEventPublisher.publishEvent(changedDifficultyEvent);
  }

  public void emmitChangeQuizName(ChangedLearningObjectNameEvent changedLearningObjectNameEvent) {
    applicationEventPublisher.publishEvent(changedLearningObjectNameEvent);
  }
}
