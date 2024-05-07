package erykmarnik.eLearn.question.domain;

import erykmarnik.eLearn.question.dto.EditQuestionDto;
import erykmarnik.eLearn.question.dto.EditQuestionType;

class OpenQuestionEditorHandler implements QuestionEditor {
  @Override
  public boolean supports(EditQuestionType questionType) {
    return questionType.equals(EditQuestionType.OPEN_QUESTION);
  }

  @Override
  public OpenQuestion editQuestion(EditQuestionDto editQuestion, Question question) {
    question = ((OpenQuestion) question).toBuilder()
        .questionContent(editQuestion.getEditedValues().getOrDefault("questionContent", question.getQuestionContent()))
        .correctAnswer(editQuestion.getEditedValues().getOrDefault("correctAnswer", question.getCorrectAnswer()))
        .imageLink(editQuestion.getEditedValues().getOrDefault("imageLink", question.getImageLink()))
        .mediaLink(editQuestion.getEditedValues().getOrDefault("mediaLink", question.getMediaLink()))
        .build();
    return (OpenQuestion) question;
  }
}
