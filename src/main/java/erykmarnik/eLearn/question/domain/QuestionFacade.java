package erykmarnik.eLearn.question.domain;

import erykmarnik.eLearn.question.dto.*;
import erykmarnik.eLearn.question.exception.QuestionNotFoundException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionFacade {
  OpenQuestionRepository openQuestionRepository;
  CloseQuestionRepository closeQuestionRepository;
  QuestionCreator questionCreator;
  QuestionEditorHandler questionEditorHandler;

  public CloseQuestionDto createCloseQuestion(CreateCloseQuestionDto createCloseQuestion) {
    return closeQuestionRepository.save(questionCreator.createCloseQuestion(createCloseQuestion)).dto();
  }

  public OpenQuestionDto createOpenQuestion(CreateOpenQuestionDto createOpenQuestion) {
    return openQuestionRepository.save(questionCreator.createOpenQuestion(createOpenQuestion)).dto();
  }

  public CloseQuestionDto editCloseQuestion(EditQuestionDto editCloseQuestion, Long questionId) {
    CloseQuestion closeQuestion = closeQuestionRepository.findByQuestionId(questionId)
        .orElseThrow(() -> new QuestionNotFoundException("Question not found"));
    return questionEditorHandler.editQuestion(editCloseQuestion, closeQuestion).dto();
  }

  public OpenQuestionDto editOpenQuestion(EditQuestionDto editOpenQuestion, Long questionId) {
    OpenQuestion openQuestion = openQuestionRepository.findByQuestionId(questionId)
        .orElseThrow(() -> new QuestionNotFoundException("Question not found"));
    return questionEditorHandler.editQuestion(editOpenQuestion, openQuestion).dto();
  }

  public void deleteAll() {
    openQuestionRepository.deleteAll();
    closeQuestionRepository.deleteAll();
  }

  public CloseQuestionDto getCloseQuestion(Long questionId) {
    return closeQuestionRepository.findByQuestionId(questionId)
        .orElseThrow(() -> new QuestionNotFoundException("Question not found")).dto();
  }

  public OpenQuestionDto getOpenQuestion(Long questionId) {
    return openQuestionRepository.findByQuestionId(questionId)
        .orElseThrow(() -> new QuestionNotFoundException("Question not found")).dto();
  }
}
