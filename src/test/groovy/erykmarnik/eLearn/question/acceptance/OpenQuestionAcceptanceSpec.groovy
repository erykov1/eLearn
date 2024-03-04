package erykmarnik.eLearn.question.acceptance

import erykmarnik.eLearn.integration.IntegrationSpec
import erykmarnik.eLearn.question.dto.EditQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionType
import erykmarnik.eLearn.question.dto.OpenQuestionDto
import erykmarnik.eLearn.question.samples.OpenQuestionSample
import spock.lang.Unroll

class OpenQuestionAcceptanceSpec extends IntegrationSpec implements OpenQuestionSample {
  def "Should create new open question"() {
    when: "creates new open question"
      OpenQuestionDto openQuestion = api.question().createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of France?",
          correctAnswer: "Paris"
      ))
    then: "open question is created"
      OpenQuestionDto result = api.question().getOpenQuestion(openQuestion.questionId)
      equalsOpenQuestions([result], [createOpenQuestion(
          questionId: result.questionId, questionContent:  "What is the capital of France?", correctAnswer: "Paris"
      )])
  }

  @Unroll
  def "Should create new question when there is already question with the same content"() {
    given: "creates open question"
      api.question().createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of France?",
          correctAnswer: "Paris"
      ))
    when: "creates new open question with the same field"
      OpenQuestionDto newOpenQuestion = api.question().createOpenQuestion(createNewOpenQuestion(
          questionContent: questionContent,
          correctAnswer: correctAnswer
      ))
    then: "question with the same field is created"
      equalsOpenQuestions([newOpenQuestion], [createOpenQuestion(
          questionId: newOpenQuestion.questionId, questionContent:  questionContent, correctAnswer: correctAnswer
      )])
    where:
      questionContent                        | correctAnswer
      "What is the capital of France?"       | "Paris"
      "What is the capital of Poland?"       | "Warsaw"
      "What is the capital of Spain?"        | "Madrid"
      "What is the capital city of Germany?" | "Berlin"
  }

  @Unroll
  def "Should edit question"() {
    given: "there is open question"
    OpenQuestionDto openQuestion = api.question().createOpenQuestion(createNewOpenQuestion(
        questionContent: "What is the capital of France?",
        correctAnswer: "Paris"
    ))
    when: "edits question field"
    OpenQuestionDto openQuestionEdited = api.question().editOpenQuestion(new EditQuestionDto(Map.of("questionContent", questionContent,"correctAnswer", correctAnswer),
        EditQuestionType.OPEN_QUESTION), openQuestion.questionId)
    then: "question is edited and has new field value"
    equalsOpenQuestions([openQuestionEdited], [createOpenQuestion(questionId: openQuestionEdited.questionId, questionContent: questionContent,
        correctAnswer: correctAnswer)])
    where:
      questionContent                                              | correctAnswer
      "What is the largest mammal on Earth?"                       | "Blue Whale"
      "Who wrote the play 'Romeo and Juliet'?"                     | "William Shakespeare"
      "Which planet is known as the 'Red Planet'?"                 | "Mars"
      "What is the capital city of Japan?"                         | "Tokyo"
      "In which year did Christopher Columbus reach the Americas?" | "1492"
  }

  def cleanup() {
    api.question().cleanup()
  }
}
