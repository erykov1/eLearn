package erykmarnik.eLearn.question.samples

import erykmarnik.eLearn.question.dto.CreateOpenQuestionDto
import erykmarnik.eLearn.question.dto.OpenQuestionDto

trait OpenQuestionSample {
  private static final IMAGE_LINK = "https://images.pexels.com/photos/699466/pexels-photo-699466.jpeg"
  private static final MEDIA_LINK = "https://www.youtube.com/watch?v=GoXwIVyNvX0"

  private static Map<String, Object> DEFAULT_OPEN_QUESTION_DATA = [
      questionId: 12L,
      questionContent: "open question content",
      correctAnswer: "correct answer",
      imageLink: IMAGE_LINK,
      mediaLink: MEDIA_LINK
  ] as Map<String, Object>

  OpenQuestionDto createOpenQuestion(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_OPEN_QUESTION_DATA + changes
    OpenQuestionDto.builder()
      .questionId(changesWithDefaults.questionId as Long)
      .questionContent(changesWithDefaults.questionContent as String)
      .correctAnswer(changesWithDefaults.correctAnswer as String)
      .imageLink(changesWithDefaults.imageLink as String)
      .mediaLink(changesWithDefaults.mediaLink as String)
      .build()
  }

  CreateOpenQuestionDto createNewOpenQuestion(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_OPEN_QUESTION_DATA + changes
    CreateOpenQuestionDto.builder()
        .questionContent(changesWithDefaults.questionContent as String)
        .correctAnswer(changesWithDefaults.correctAnswer as String)
        .imageLink(changesWithDefaults.imageLink as String)
        .mediaLink(changesWithDefaults.mediaLink as String)
        .build()
  }

  void equalsOpenQuestions(List<OpenQuestionDto> result, List<OpenQuestionDto> expected) {
    assert result.questionId == expected.questionId
    assert result.questionContent == expected.questionContent
    assert result.correctAnswer == expected.correctAnswer
    assert result.imageLink == expected.imageLink
    assert result.mediaLink == expected.mediaLink
  }

  final static long FAKE_QUESTION_ID = 103L
}