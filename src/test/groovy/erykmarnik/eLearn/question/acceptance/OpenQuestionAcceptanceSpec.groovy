package erykmarnik.eLearn.question.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.question.dto.EditQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionType
import erykmarnik.eLearn.question.dto.OpenQuestionDto
import erykmarnik.eLearn.question.samples.LinkSample
import erykmarnik.eLearn.question.samples.OpenQuestionSample
import spock.lang.Unroll

class OpenQuestionAcceptanceSpec extends IntegrationSpec implements OpenQuestionSample, LinkSample {
  QuestionApiFacade questionApiFacade

  def setup() {
    questionApiFacade = new QuestionApiFacade(mockMvc, objectMapper)
  }

  def "Should create new open question"() {
    when: "creates new open question"
      OpenQuestionDto openQuestion = questionApiFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of France?",
          correctAnswer: "Paris",
          imageLink: FRANCE_IMAGE_LINK,
          mediaLink: FRANCE_MEDIA_LINK
      ))
    then: "open question is created"
      OpenQuestionDto result = questionApiFacade.getOpenQuestion(openQuestion.questionId)
      equalsOpenQuestions([result], [createOpenQuestion(
          questionId: result.questionId, questionContent:  "What is the capital of France?", correctAnswer: "Paris",
          imageLink: FRANCE_IMAGE_LINK, mediaLink: FRANCE_MEDIA_LINK
      )])
  }

  @Unroll
  def "Should create new question when there is already question with the same content"() {
    given: "creates open question"
      questionApiFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: questionContent,
          correctAnswer: correctAnswer,
          imageLink: imageLink,
          mediaLink: mediaLink
      ))
    when: "creates new open question with the same field"
      OpenQuestionDto newOpenQuestion = questionApiFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: questionContent,
          correctAnswer: correctAnswer,
          imageLink: imageLink,
          mediaLink: mediaLink
      ))
    then: "question with the same field is created"
      equalsOpenQuestions([newOpenQuestion], [createOpenQuestion(
          questionId: newOpenQuestion.questionId, questionContent:  questionContent, correctAnswer: correctAnswer, imageLink: imageLink, mediaLink: mediaLink
      )])
    where:
      questionContent                   | correctAnswer | imageLink          | mediaLink
      "What is the capital of France?"  | "Paris"       | FRANCE_IMAGE_LINK  | FRANCE_MEDIA_LINK
      "What is the capital of Poland?"  | "Warsaw"      | POLAND_IMAGE_LINK  | POLAND_MEDIA_LINK
      "What is the capital of Spain?"   | "Madrid"      | SPAIN_IMAGE_LINK   | SPAIN_MEDIA_LINK
      "What is the capital of Germany?" | "Berlin"      | GERMANY_IMAGE_LINK | GERMANY_MEDIA_LINK
  }

  @Unroll
  def "Should edit question"() {
    given: "there is open question"
      OpenQuestionDto openQuestion = questionApiFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of France?",
          correctAnswer: "Paris",
          imageLink: imageLink,
          mediaLink: mediaLink
      ))
    when: "edits question field"
      OpenQuestionDto openQuestionEdited = questionApiFacade.editOpenQuestion(new EditQuestionDto(Map.of("questionContent",
          questionContent,"correctAnswer", correctAnswer, "imageLink", imageLink, "mediaLink", mediaLink),
          EditQuestionType.OPEN_QUESTION), openQuestion.questionId)
    then: "question is edited and has new field value"
    equalsOpenQuestions([openQuestionEdited], [createOpenQuestion(questionId: openQuestionEdited.questionId, questionContent: questionContent,
        correctAnswer: correctAnswer, imageLink: imageLink, mediaLink: mediaLink)])
    where:
      questionContent                                              | correctAnswer         | imageLink           | mediaLink
      "What is the largest mammal on Earth?"                       | "Blue Whale"          | MAMMAL_IMAGE_LINK   | WHALE_MEDIA_LINK
      "Who wrote the play 'Romeo and Juliet'?"                     | "William Shakespeare" | POEM_IMAGE_LINK     | POEM_MEDIA_LINK
      "Which planet is known as the 'Red Planet'?"                 | "Mars"                | PLANET_IMAGE_LINK   | PLANET_MEDIA_LINK
      "What is the capital city of Japan?"                         | "Tokyo"               | CITY_IMAGE_LINK     | CITY_MEDIA_LINK
      "In which year did Christopher Columbus reach the Americas?" | "1492"                | COLUMBUS_IMAGE_LINK | COLUMBUS_MEDIA_LINK
  }

  def cleanup() {
    questionApiFacade.cleanup()
  }
}
