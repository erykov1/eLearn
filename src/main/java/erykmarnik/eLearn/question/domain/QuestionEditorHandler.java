package erykmarnik.eLearn.question.domain;

import erykmarnik.eLearn.question.dto.EditQuestionDto;
import erykmarnik.eLearn.question.exception.InvalidFieldException;

import java.util.List;

class QuestionEditorHandler {
  private static final List<QuestionEditor> questionEditors = List.of(new CloseQuestionEditorHandler(),
      new OpenQuestionEditorHandler());

  <T extends Question> T editQuestion(EditQuestionDto editQuestion, T question) {
    QuestionEditor questionEditor = questionEditors.stream()
        .filter(editor -> editor.supports(editQuestion.getEditQuestionType()))
        .findFirst().orElseThrow(() -> new InvalidFieldException("invalid type to process"));
    return questionEditor.editQuestion(editQuestion, question);
  }
}
