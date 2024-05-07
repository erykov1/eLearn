package erykmarnik.eLearn.question.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.question.dto.CloseQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionType
import erykmarnik.eLearn.question.samples.CloseQuestionSample
import erykmarnik.eLearn.question.samples.LinkSample
import spock.lang.Unroll

class CloseQuestionAcceptanceSpec extends IntegrationSpec implements CloseQuestionSample, LinkSample {
  QuestionApiFacade questionApiFacade

  def setup() {
    questionApiFacade = new QuestionApiFacade(mockMvc, objectMapper)
  }

  def "Should create new close question"() {
    when: "creates new close question"
      CloseQuestionDto closeQuestion = questionApiFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: "Warsaw",
          answerB: "Paris",
          answerC: "Madrid",
          answerD: "Berlin",
          correctAnswer: "Paris",
          imageLink: FRANCE_IMAGE_LINK,
          mediaLink: FRANCE_MEDIA_LINK
      ))
    then: "close question is created"
      CloseQuestionDto result = questionApiFacade.getCloseQuestion(closeQuestion.questionId)
      equalsCloseQuestions([result], [createCloseQuestion(
          questionId: result.questionId, questionContent:  "What is the capital of France?", answerA: "Warsaw", answerB: "Paris",
          answerC: "Madrid", answerD: "Berlin", correctAnswer: "Paris", imageLink: FRANCE_IMAGE_LINK, mediaLink: FRANCE_MEDIA_LINK
      )])
  }

  @Unroll
  def "Should create new question when there is already question with the same content"() {
    given: "creates close question"
      questionApiFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: "Warsaw",
          answerB: "Paris",
          answerC: "Madrid",
          answerD: "Berlin",
          correctAnswer: "Paris",
          imageLink: imageLink,
          mediaLink: mediaLink
      ))
    when: "creates new close question with the same field"
      CloseQuestionDto newCloseQuestion = questionApiFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: questionContent,
          answerA: answerA,
          answerB: answerB,
          answerC: answerC,
          answerD: answerD,
          correctAnswer: correctAnswer,
          imageLink: imageLink,
          mediaLink: mediaLink
      ))
    then: "question with the same field is created"
      equalsCloseQuestions([newCloseQuestion], [createCloseQuestion(
          questionId: newCloseQuestion.questionId, questionContent:  questionContent, answerA: answerA, answerB: answerB,
          answerC: answerC, answerD: answerD, correctAnswer: correctAnswer, imageLink: imageLink, mediaLink: mediaLink
      )])
    where:
      questionContent                        | answerA | answerB  | answerC  | answerD  | correctAnswer | imageLink          | mediaLink
      "What is the capital of France?"       | "Paris" | "Warsaw" | "Madrid" | "Berlin" | "Paris"       | FRANCE_IMAGE_LINK  | FRANCE_MEDIA_LINK
      "What is the capital of Poland?"       | "Paris" | "Warsaw" | "Madrid" | "Berlin" | "Warsaw"      | POLAND_IMAGE_LINK  | POLAND_MEDIA_LINK
      "What is the capital of Spain?"        | "Paris" | "Warsaw" | "Madrid" | "Berlin" | "Madrid"      | SPAIN_IMAGE_LINK   | SPAIN_MEDIA_LINK
      "What is the capital city of Germany?" | "Paris" | "Warsaw" | "Madrid" | "Berlin" | "Berlin"      | GERMANY_IMAGE_LINK | GERMANY_MEDIA_LINK
  }

  @Unroll
  def "Should edit question"() {
    given: "there is close question"
      CloseQuestionDto closeQuestion = questionApiFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: "Warsaw",
          answerB: "Paris",
          answerC: "Madrid",
          answerD: "Berlin",
          correctAnswer: "Paris",
          imageLink: imageLink,
          mediaLink: mediaLink
      ))
    when: "edits question field"
      CloseQuestionDto closeQuestionEdited = questionApiFacade.editCloseQuestion(new EditQuestionDto(Map.of("questionContent", questionContent, "answerA", answerA, "answerB", answerB,
          "answerC", answerC, "answerD", answerD, "correctAnswer", correctAnswer, "imageLink", imageLink), EditQuestionType.CLOSE_QUESTION), closeQuestion.questionId)
    then: "question is edited and has new field value"
      equalsCloseQuestions([closeQuestionEdited], [createCloseQuestion(questionId: closeQuestionEdited.questionId, questionContent: questionContent,
          answerA: answerA, answerB: answerB, answerC: answerC, answerD: answerD, correctAnswer: correctAnswer, imageLink: imageLink, mediaLink: mediaLink)])
    where:
      questionContent                                              | answerA           | answerB       | answerC               | answerD      | correctAnswer         | imageLink           | mediaLink
      "What is the largest mammal on Earth?" | "Elephant" | "Blue Whale" | "Lion" | "Giraffe" | "Blue Whale"                                                          | MAMMAL_IMAGE_LINK   | WHALE_MEDIA_LINK
      "Who wrote the play 'Romeo and Juliet'?"                     | "Charles Dickens" | "Jane Austen" | "William Shakespeare" | "Mark Twain" | "William Shakespeare" | POEM_IMAGE_LINK     | POEM_MEDIA_LINK
      "Which planet is known as the 'Red Planet'?"                 | "Venus"           | "Saturn"      | "Mars"                | "Jupiter"    | "Mars"                | PLANET_IMAGE_LINK   | PLANET_MEDIA_LINK
      "What is the capital city of Japan?"                         | "Tokyo"           | "Beijing"     | "Seoul"               | "Bangkok"    | "Tokyo"               | CITY_IMAGE_LINK     | CITY_MEDIA_LINK
      "In which year did Christopher Columbus reach the Americas?" | "1455"            | "1776"        | "1603"                | "1492"       | "1492"                | COLUMBUS_IMAGE_LINK | COLUMBUS_MEDIA_LINK
  }

  def cleanup() {
    questionApiFacade.cleanup()
  }
}
