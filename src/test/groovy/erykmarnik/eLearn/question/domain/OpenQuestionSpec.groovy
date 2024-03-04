package erykmarnik.eLearn.question.domain

import erykmarnik.eLearn.question.dto.CloseQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionType
import erykmarnik.eLearn.question.dto.OpenQuestionDto
import erykmarnik.eLearn.question.exception.QuestionNotFoundException
import spock.lang.Unroll

class OpenQuestionSpec extends QuestionBaseSpec {
  def "Should create open question"() {
    when: "creates new open question"
      OpenQuestionDto openQuestion = questionFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of France?",
          correctAnswer: "Paris"
      ))
    then: "open question is created"
      equalsOpenQuestions([openQuestion], [createOpenQuestion(questionId: openQuestion.questionId,
        questionContent: openQuestion.questionContent, correctAnswer: openQuestion.correctAnswer
      )])
  }

  @Unroll
  def "Should create new open question when there is already question with the same content"() {
    given: "creates open question"
    questionFacade.createOpenQuestion(createNewOpenQuestion(
        questionContent: "What is the capital of France?",
        correctAnswer: "Paris"
    ))
    when: "creates new open question with the same field"
      CloseQuestionDto newCloseQuestion = questionFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: questionContent,
          correctAnswer: correctAnswer
      ))
    then: "question with the same content is created"
    equalsCloseQuestions([newCloseQuestion], [createCloseQuestion(
        questionId: newCloseQuestion.questionId, questionContent: questionContent, correctAnswer: correctAnswer
    )])
    where:
      questionContent                        | correctAnswer
      "What is the capital of France?"       | "Paris"
      "What is the capital of Poland?"       | "Warsaw"
      "What is the capital of Spain?"        | "Madrid"
      "What is the capital city of Germany?" | "Berlin"
  }

  @Unroll
  def "Should edit answer"() {
    given: "there is open question"
      OpenQuestionDto question = questionFacade.createOpenQuestion(createNewOpenQuestion(
          questionContent: "What is the capital of France?",
          correctAnswer: "Paris"
      ))
    when: "edits answer"
      OpenQuestionDto editedQuestion = questionFacade.editOpenQuestion(
          new EditQuestionDto(Map.of("questionContent", questionContent, "correctAnswer", correctAnswer), EditQuestionType.OPEN_QUESTION), question.questionId)
    then: "question is edited and have new answer"
      equalsOpenQuestions([editedQuestion], [createOpenQuestion(questionId: editedQuestion.questionId, questionContent: questionContent,
          correctAnswer: correctAnswer)])
    where:
      questionContent                                              | correctAnswer
      "What is the largest mammal on Earth?"                       | "Blue Whale"
      "Who wrote the play 'Romeo and Juliet'?"                     | "William Shakespeare"
      "Which planet is known as the 'Red Planet'?"                 | "Mars"
      "What is the capital city of Japan?"                         | "Tokyo"
      "In which year did Christopher Columbus reach the Americas?" | "1492"
  }

  def "Should get error if try to edit not existing question"() {
    when: "try to edit question that does not exist"
      questionFacade.editOpenQuestion(
          new EditQuestionDto(Map.of("questionContent", "What is the capital of France?","correctAnswer",
              "Paris"), EditQuestionType.CLOSE_QUESTION), new Random().nextLong())
    then: "gets error of not existing question"
      thrown(QuestionNotFoundException)
  }
}
