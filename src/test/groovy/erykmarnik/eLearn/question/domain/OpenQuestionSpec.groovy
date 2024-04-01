package erykmarnik.eLearn.question.domain

import erykmarnik.eLearn.question.dto.EditQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionType
import erykmarnik.eLearn.question.dto.OpenQuestionDto
import erykmarnik.eLearn.question.exception.InvalidImageLinkException
import erykmarnik.eLearn.question.exception.QuestionNotFoundException
import spock.lang.Unroll

class OpenQuestionSpec extends QuestionBaseSpec {
  def "Should create open question"() {
    when: "creates new open question"
      OpenQuestionDto openQuestion = questionFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of France?",
          correctAnswer: "Paris",
          imageLink: FRANCE_IMAGE_LINK
      ))
    then: "open question is created"
      equalsOpenQuestions([openQuestion], [createOpenQuestion(questionId: openQuestion.questionId,
        questionContent: openQuestion.questionContent, correctAnswer: openQuestion.correctAnswer, imageLink: FRANCE_IMAGE_LINK
      )])
  }

  @Unroll
  def "Should create new open question when there is already question with the same content"() {
    given: "creates open question"
      questionFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: questionContent,
          correctAnswer: correctAnswer,
          imageLink: imageLink
      ))
    when: "creates new open question with the same field"
      OpenQuestionDto newOpenQuestion = questionFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: questionContent,
          correctAnswer: correctAnswer,
          imageLink: imageLink
      ))
    then: "question with the same content is created"
      equalsOpenQuestions([newOpenQuestion], [createOpenQuestion(
          questionId: newOpenQuestion.questionId, questionContent: questionContent, correctAnswer: correctAnswer, imageLink: imageLink
      )])
    where:
      questionContent                        | correctAnswer | imageLink
      "What is the capital of France?"       | "Paris"       | FRANCE_IMAGE_LINK
      "What is the capital of Poland?"       | "Warsaw"      | POLAND_IMAGE_LINK
      "What is the capital of Spain?"        | "Madrid"      | SPAIN_IMAGE_LINK
      "What is the capital of Germany?"      | "Berlin"      | GERMANY_IMAGE_LINK
  }

  @Unroll
  def "Should edit answer"() {
    given: "there is open question"
      OpenQuestionDto question = questionFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of France?",
          correctAnswer: "Paris",
          imageLink: imageLink
      ))
    when: "edits answer"
      OpenQuestionDto editedQuestion = questionFacade.editOpenQuestion(
          new EditQuestionDto(Map.of("questionContent", questionContent, "correctAnswer", correctAnswer), EditQuestionType.OPEN_QUESTION), question.questionId)
    then: "question is edited and have new answer"
      equalsOpenQuestions([editedQuestion], [createOpenQuestion(questionId: editedQuestion.questionId, questionContent: questionContent,
          correctAnswer: correctAnswer, imageLink: imageLink)])
    where:
      questionContent                                              | correctAnswer         | imageLink
      "What is the largest mammal on Earth?"                       | "Blue Whale"          | MAMMAL_IMAGE_LINK
      "Who wrote the play 'Romeo and Juliet'?"                     | "William Shakespeare" | POEM_IMAGE_LINK
      "Which planet is known as the 'Red Planet'?"                 | "Mars"                | PLANET_IMAGE_LINK
      "What is the capital city of Japan?"                         | "Tokyo"               | CITY_IMAGE_LINK
      "In which year did Christopher Columbus reach the Americas?" | "1492"                | COLUMBUS_IMAGE_LINK
  }

  def "Should get error if try to edit not existing question"() {
    when: "try to edit question that does not exist"
      questionFacade.editOpenQuestion(
          new EditQuestionDto(Map.of("questionContent", "What is the capital of France?","correctAnswer",
              "Paris"), EditQuestionType.CLOSE_QUESTION), new Random().nextLong())
    then: "gets error of not existing question"
      thrown(QuestionNotFoundException)
  }

  def "Should get error if try to add image link with not supported types"() {
    when: "creates question that contains invalid image link"
      questionFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of France?",
          correctAnswer: "Paris",
          imageLink: imageLink
      ))
    then: "gets error of invalid image link type"
      thrown(InvalidImageLinkException)
    where:
      imageLink << ["htp://data", "script.js", "https:/invalid_link"]
  }
}
