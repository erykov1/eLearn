package erykmarnik.eLearn.question.domain;

import erykmarnik.eLearn.question.dto.EditQuestionDto;
import erykmarnik.eLearn.question.dto.EditQuestionType;

interface QuestionEditor {
  boolean supports(EditQuestionType questionType);
  <T extends Question> T editQuestion(EditQuestionDto editQuestion, T question);
}
