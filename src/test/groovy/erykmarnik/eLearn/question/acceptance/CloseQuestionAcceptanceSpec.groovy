package erykmarnik.eLearn.question.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.question.dto.CloseQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionType
import erykmarnik.eLearn.question.samples.CloseQuestionSample
import spock.lang.Unroll

class CloseQuestionAcceptanceSpec extends IntegrationSpec implements CloseQuestionSample {
  def "Should create new close question"() {
    when: "creates new close question"
      CloseQuestionDto closeQuestion = api.question().createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: "Warsaw",
          answerB: "Paris",
          answerC: "Madrid",
          answerD: "Berlin",
          correctAnswer: "Paris"
      ))
    then: "close question is created"
      CloseQuestionDto result = api.question().getCloseQuestion(closeQuestion.questionId)
      equalsCloseQuestions([result], [createCloseQuestion(
          questionId: result.questionId, questionContent:  "What is the capital of France?", answerA: "Warsaw", answerB: "Paris",
          answerC: "Madrid", answerD: "Berlin", correctAnswer: "Paris"
      )])
  }

  @Unroll
  def "Should create new question when there is already question with the same content"() {
    given: "creates close question"
      api.question().createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: "Warsaw",
          answerB: "Paris",
          answerC: "Madrid",
          answerD: "Berlin",
          correctAnswer: "Paris"
      ))
    when: "creates new close question with the same field"
      CloseQuestionDto newCloseQuestion = api.question().createCloseQuestion(createNewCloseQuestion(
          questionContent: questionContent,
          answerA: answerA,
          answerB: answerB,
          answerC: answerC,
          answerD: answerD,
          correctAnswer: correctAnswer
      ))
    then: "question with the same field is created"
      equalsCloseQuestions([newCloseQuestion], [createCloseQuestion(
          questionId: newCloseQuestion.questionId, questionContent:  questionContent, answerA: answerA, answerB: answerB,
          answerC: answerC, answerD: answerD, correctAnswer: correctAnswer
      )])
    where:
      questionContent                        | answerA | answerB  | answerC  | answerD  | correctAnswer
      "What is the capital of France?"       | "Paris" | "Warsaw" | "Madrid" | "Berlin" | "Paris"
      "What is the capital of Poland?"       | "Paris" | "Warsaw" | "Madrid" | "Berlin" | "Warsaw"
      "What is the capital of Spain?"        | "Paris" | "Warsaw" | "Madrid" | "Berlin" | "Madrid"
      "What is the capital city of Germany?" | "Paris" | "Warsaw" | "Madrid" | "Berlin" | "Berlin"
  }

  @Unroll
  def "Should edit question"() {
    given: "there is close question"
      CloseQuestionDto closeQuestion = api.question().createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: "Warsaw",
          answerB: "Paris",
          answerC: "Madrid",
          answerD: "Berlin",
          correctAnswer: "Paris"
      ))
    when: "edits question field"
      CloseQuestionDto closeQuestionEdited = api.question().editCloseQuestion(new EditQuestionDto(Map.of("questionContent", questionContent, "answerA", answerA, "answerB", answerB,
          "answerC", answerC, "answerD", answerD, "correctAnswer", correctAnswer), EditQuestionType.CLOSE_QUESTION), closeQuestion.questionId)
    then: "question is edited and has new field value"
      equalsCloseQuestions([closeQuestionEdited], [createCloseQuestion(questionId: closeQuestionEdited.questionId, questionContent: questionContent,
          answerA: answerA, answerB: answerB, answerC: answerC, answerD: answerD, correctAnswer: correctAnswer)])
    where:
      questionContent                                              | answerA           | answerB       | answerC               | answerD      | correctAnswer
      "What is the largest mammal on Earth?"                       | "Elephant"        | "Blue Whale"  | "Lion"                | "Giraffe"    | "Blue Whale"
      "Who wrote the play 'Romeo and Juliet'?"                     | "Charles Dickens" | "Jane Austen" | "William Shakespeare" | "Mark Twain" | "William Shakespeare"
      "Which planet is known as the 'Red Planet'?"                 | "Venus"           | "Saturn"      | "Mars"                | "Jupiter"    | "Mars"
      "What is the capital city of Japan?"                         | "Tokyo"           | "Beijing"     | "Seoul"               | "Bangkok"    | "Tokyo"
      "In which year did Christopher Columbus reach the Americas?" | "1455"            | "1776"        | "1603"                | "1492"       | "1492"
  }

  def cleanup() {
    api.question().cleanup()
  }
}
