package erykmarnik.eLearn.question.samples

import erykmarnik.eLearn.question.dto.CloseQuestionDto
import erykmarnik.eLearn.question.dto.CreateCloseQuestionDto

trait CloseQuestionSample {
  private static final IMAGE_LINK = "https://images.pexels.com/photos/699466/pexels-photo-699466.jpeg"

  private static Map<String, Object> DEFAULT_CLOSE_QUESTION_DATA = [
      questionId: 11L,
      questionContent: "close question content",
      answerA: "answer a",
      answerB: "answer b",
      answerC: "answer c",
      answerD: "answer d",
      correctAnswer: "answer a",
      "imageLink": IMAGE_LINK
  ] as Map<String, Object>

  CloseQuestionDto createCloseQuestion(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_CLOSE_QUESTION_DATA + changes
    CloseQuestionDto.builder()
        .questionId(changesWithDefaults.questionId as Long)
        .questionContent(changesWithDefaults.questionContent as String)
        .answerA(changesWithDefaults.answerA as String)
        .answerB(changesWithDefaults.answerB as String)
        .answerC(changesWithDefaults.answerC as String)
        .answerD(changesWithDefaults.answerD as String)
        .correctAnswer(changesWithDefaults.correctAnswer as String)
        .imageLink(changesWithDefaults.imageLink as String)
        .build()
  }

  CreateCloseQuestionDto createNewCloseQuestion(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_CLOSE_QUESTION_DATA + changes
    CreateCloseQuestionDto.builder()
        .questionContent(changesWithDefaults.questionContent as String)
        .answerA(changesWithDefaults.answerA as String)
        .answerB(changesWithDefaults.answerB as String)
        .answerC(changesWithDefaults.answerC as String)
        .answerD(changesWithDefaults.answerD as String)
        .correctAnswer(changesWithDefaults.correctAnswer as String)
        .imageLink(changesWithDefaults.imageLink as String)
        .build()
  }

  void equalsCloseQuestions(List<CloseQuestionDto> result, List<CloseQuestionDto> expected) {
    def comparator = Comparator.comparing(CloseQuestionDto::getQuestionContent)
    result.sort(comparator)
    expected.sort(comparator)
    assert result.questionId == expected.questionId
    assert result.questionContent == expected.questionContent
    assert result.answerA == expected.answerA
    assert result.answerB == expected.answerB
    assert result.answerC == expected.answerC
    assert result.answerD == expected.answerD
    assert result.correctAnswer == expected.correctAnswer
    assert result.imageLink == expected.imageLink
  }
}