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
        .questionContent(editQuestion.getEditedValues().get("questionContent"))
        .correctAnswer(editQuestion.getEditedValues().get("correctAnswer"))
        .answerA(editQuestion.getEditedValues().get("answerA"))
        .answerB(editQuestion.getEditedValues().get("answerB"))
        .answerC(editQuestion.getEditedValues().get("answerC"))
        .answerD(editQuestion.getEditedValues().get("answerD"))
        .build();
    return (CloseQuestion) question;
  }
}
