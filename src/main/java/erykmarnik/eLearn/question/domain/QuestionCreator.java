package erykmarnik.eLearn.question.domain;

import erykmarnik.eLearn.question.dto.CreateCloseQuestionDto;
import erykmarnik.eLearn.question.dto.CreateOpenQuestionDto;
import erykmarnik.eLearn.question.exception.DuplicateAnswerValueException;

import java.util.HashSet;
import java.util.Set;

class QuestionCreator {

  CloseQuestion createCloseQuestion(CreateCloseQuestionDto createCloseQuestion) {
    checkIfContainsDuplicates(createCloseQuestion);
    return CloseQuestion.builder()
        .questionContent(createCloseQuestion.getQuestionContent())
        .answerA(createCloseQuestion.getAnswerA())
        .answerB(createCloseQuestion.getAnswerB())
        .answerC(createCloseQuestion.getAnswerC())
        .answerD(createCloseQuestion.getAnswerD())
        .correctAnswer(createCloseQuestion.getCorrectAnswer())
        .build();
  }

  OpenQuestion createOpenQuestion(CreateOpenQuestionDto createOpenQuestion) {
    return OpenQuestion.builder()
        .questionContent(createOpenQuestion.getQuestionContent())
        .correctAnswer(createOpenQuestion.getCorrectAnswer())
        .build();
  }

  private void checkIfContainsDuplicates(CreateCloseQuestionDto createCloseQuestion) {
    Set<String> answers = new HashSet<>();
    answers.add(createCloseQuestion.getAnswerA());
    answers.add(createCloseQuestion.getAnswerB());
    answers.add(createCloseQuestion.getAnswerC());
    answers.add(createCloseQuestion.getAnswerD());
    if (answers.size() != 4) {
      throw new DuplicateAnswerValueException("Answers contains the same value");
    }
  }
}
