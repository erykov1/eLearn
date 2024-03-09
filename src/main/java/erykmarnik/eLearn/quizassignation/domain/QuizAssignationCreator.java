package erykmarnik.eLearn.quizassignation.domain;

import erykmarnik.eLearn.quizassignation.dto.CreateQuizAssignationDto;
import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
class QuizAssignationCreator {
  InstantProvider instantProvider;

  @Autowired
  public QuizAssignationCreator(InstantProvider instantProvider) {
    this.instantProvider = instantProvider;
  }

  QuizAssignation createQuizAssignation(CreateQuizAssignationDto createQuizAssignation) {
    return QuizAssignation.builder()
        .quizId(createQuizAssignation.getQuizId())
        .questionId(createQuizAssignation.getQuestionId())
        .assignedAt(instantProvider.now())
        .assignedBy(createQuizAssignation.getAssignedBy())
        .build();
  }
}
