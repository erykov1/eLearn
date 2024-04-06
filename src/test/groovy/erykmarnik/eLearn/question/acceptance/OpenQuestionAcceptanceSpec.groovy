package erykmarnik.eLearn.question.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.question.dto.EditQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionType
import erykmarnik.eLearn.question.dto.OpenQuestionDto
import erykmarnik.eLearn.question.samples.ImageLinkSample
import erykmarnik.eLearn.question.samples.OpenQuestionSample
import spock.lang.Unroll

class OpenQuestionAcceptanceSpec extends IntegrationSpec implements OpenQuestionSample, ImageLinkSample {
  QuestionApiFacade questionApiFacade

  def setup() {
    questionApiFacade = new QuestionApiFacade(mockMvc, objectMapper)
  }

  def "Should create new open question"() {
    when: "creates new open question"
      OpenQuestionDto openQuestion = questionApiFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of France?",
          correctAnswer: "Paris",
          imageLink: FRANCE_IMAGE_LINK
      ))
    then: "open question is created"
      OpenQuestionDto result = questionApiFacade.getOpenQuestion(openQuestion.questionId)
      equalsOpenQuestions([result], [createOpenQuestion(
          questionId: result.questionId, questionContent:  "What is the capital of France?", correctAnswer: "Paris", imageLink: FRANCE_IMAGE_LINK
      )])
  }

  @Unroll
  def "Should create new question when there is already question with the same content"() {
    given: "creates open question"
      questionApiFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: questionContent,
          correctAnswer: correctAnswer,
          imageLink: imageLink
      ))
    when: "creates new open question with the same field"
      OpenQuestionDto newOpenQuestion = questionApiFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: questionContent,
          correctAnswer: correctAnswer,
          imageLink: imageLink
      ))
    then: "question with the same field is created"
      equalsOpenQuestions([newOpenQuestion], [createOpenQuestion(
          questionId: newOpenQuestion.questionId, questionContent:  questionContent, correctAnswer: correctAnswer, imageLink: imageLink
      )])
    where:
      questionContent                        | correctAnswer | imageLink
      "What is the capital of France?"       | "Paris"       | FRANCE_IMAGE_LINK
      "What is the capital of Poland?"       | "Warsaw"      | POLAND_IMAGE_LINK
      "What is the capital of Spain?"        | "Madrid"      | SPAIN_IMAGE_LINK
      "What is the capital of Germany?"      | "Berlin"      | GERMANY_IMAGE_LINK
  }

  @Unroll
  def "Should edit question"() {
    given: "there is open question"
      OpenQuestionDto openQuestion = questionApiFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of France?",
          correctAnswer: "Paris",
          imageLink: imageLink
      ))
    when: "edits question field"
      OpenQuestionDto openQuestionEdited = questionApiFacade.editOpenQuestion(new EditQuestionDto(Map.of("questionContent",
          questionContent,"correctAnswer", correctAnswer, "imageLink", imageLink),
          EditQuestionType.OPEN_QUESTION), openQuestion.questionId)
    then: "question is edited and has new field value"
    equalsOpenQuestions([openQuestionEdited], [createOpenQuestion(questionId: openQuestionEdited.questionId, questionContent: questionContent,
        correctAnswer: correctAnswer, imageLink: imageLink)])
    where:
      questionContent                                              | correctAnswer         | imageLink
      "What is the largest mammal on Earth?"                       | "Blue Whale"          | MAMMAL_IMAGE_LINK
      "Who wrote the play 'Romeo and Juliet'?"                     | "William Shakespeare" | POEM_IMAGE_LINK
      "Which planet is known as the 'Red Planet'?"                 | "Mars"                | PLANET_IMAGE_LINK
      "What is the capital city of Japan?"                         | "Tokyo"               | CITY_IMAGE_LINK
      "In which year did Christopher Columbus reach the Americas?" | "1492"                | COLUMBUS_IMAGE_LINK
  }

  def cleanup() {
    questionApiFacade.cleanup()
  }
}
