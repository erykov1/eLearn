package erykmarnik.eLearn.question.domain;

import erykmarnik.eLearn.question.dto.*;
import erykmarnik.eLearn.question.exception.QuestionNotFoundException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionFacade {
  OpenQuestionRepository openQuestionRepository;
  CloseQuestionRepository closeQuestionRepository;
  QuestionCreator questionCreator;
  QuestionEditorHandler questionEditorHandler;

  public CloseQuestionDto createCloseQuestion(CreateCloseQuestionDto createCloseQuestion) {
    log.info("creating close question");
    return closeQuestionRepository.save(questionCreator.createCloseQuestion(createCloseQuestion)).dto();
  }

  public OpenQuestionDto createOpenQuestion(CreateOpenQuestionDto createOpenQuestion) {
    log.info("creating open question");
    return openQuestionRepository.save(questionCreator.createOpenQuestion(createOpenQuestion)).dto();
  }

  public CloseQuestionDto editCloseQuestion(EditQuestionDto editCloseQuestion, Long questionId) {
    CloseQuestion closeQuestion = closeQuestionRepository.findByQuestionId(questionId)
        .orElseThrow(() -> new QuestionNotFoundException("Question not found"));
    log.info("editing close question with id {}", questionId);
    return questionEditorHandler.editQuestion(editCloseQuestion, closeQuestion).dto();
  }

  public OpenQuestionDto editOpenQuestion(EditQuestionDto editOpenQuestion, Long questionId) {
    OpenQuestion openQuestion = openQuestionRepository.findByQuestionId(questionId)
        .orElseThrow(() -> new QuestionNotFoundException("Question not found"));
    log.info("editing open question with id {}", questionId);
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

  public void checkIfExists(Long questionId) {
    Optional<OpenQuestion> openQuestion = openQuestionRepository.findByQuestionId(questionId);
    Optional<CloseQuestion> closeQuestion = closeQuestionRepository.findByQuestionId(questionId);
    if (openQuestion.isEmpty() && closeQuestion.isEmpty()) {
      throw new QuestionNotFoundException("Question not found");
    }
  }
}
