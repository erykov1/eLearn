package erykmarnik.eLearn.question.domain;

import erykmarnik.eLearn.question.dto.EditQuestionDto;
import erykmarnik.eLearn.question.dto.EditQuestionType;

class CloseQuestionEditorHandler implements QuestionEditor {
  @Override
  public boolean supports(EditQuestionType questionType) {
    return questionType.equals(EditQuestionType.CLOSE_QUESTION);
  }

  @Override
  public CloseQuestion editQuestion(EditQuestionDto editQuestion, Question question) {
    question = ((CloseQuestion) question).toBuilder()
        .questionContent(editQuestion.getEditedValues().getOrDefault("questionContent", question.getQuestionContent()))
        .correctAnswer(editQuestion.getEditedValues().getOrDefault("correctAnswer", question.getCorrectAnswer()))
        .answerA(editQuestion.getEditedValues().get("answerA"))
        .answerB(editQuestion.getEditedValues().get("answerB"))
        .answerC(editQuestion.getEditedValues().get("answerC"))
        .answerD(editQuestion.getEditedValues().get("answerD"))
        .imageLink(editQuestion.getEditedValues().getOrDefault("imageLink", question.getImageLink()))
        .mediaLink(editQuestion.getEditedValues().getOrDefault("mediaLink", question.getMediaLink()))
        .build();
    return (CloseQuestion) question;
  }
}
