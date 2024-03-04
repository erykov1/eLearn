package erykmarnik.eLearn.question.domain

import erykmarnik.eLearn.question.dto.CloseQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionType
import erykmarnik.eLearn.question.exception.QuestionNotFoundException
import spock.lang.Unroll
import erykmarnik.eLearn.question.exception.DuplicateAnswerValueException

class CloseQuestionSpec extends QuestionBaseSpec {

  def "Should create close question"() {
    when: "creates new close question"
      CloseQuestionDto result = questionFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: "Warsaw",
          answerB: "Paris",
          answerC: "Madrid",
          answerD: "Berlin",
          correctAnswer: "Paris"
      ))
    then: "question is created"
      equalsCloseQuestions([result], [createCloseQuestion(
          questionId: result.questionId, questionContent:  "What is the capital of France?", answerA: "Warsaw", answerB: "Paris",
          answerC: "Madrid", answerD: "Berlin", correctAnswer: "Paris"
      )])
  }

  @Unroll
  def "Should create new question when there is already question with the same content"() {
    given: "creates close question"
      questionFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: "Warsaw",
          answerB: "Paris",
          answerC: "Madrid",
          answerD: "Berlin",
          correctAnswer: "Paris"
      ))
    when: "creates new close question with the same field"
      CloseQuestionDto newCloseQuestion = questionFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: questionContent,
          answerA: answerA,
          answerB: answerB,
          answerC: answerC,
          answerD: answerD,
          correctAnswer: correctAnswer
      ))
    then: "question with the same content is created"
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
      CloseQuestionDto question = questionFacade.createCloseQuestion(createNewCloseQuestion(
        questionContent: "What is the capital of France?",
        answerA: "Warsaw",
        answerB: "Paris",
        answerC: "Madrid",
        answerD: "Berlin",
        correctAnswer: "Paris"
    ))
    when: "edits given fields"
      CloseQuestionDto editedQuestion = questionFacade.editCloseQuestion(
        new EditQuestionDto(Map.of("questionContent", questionContent, "answerA", answerA, "answerB", answerB,
        "answerC", answerC, "answerD", answerD, "correctAnswer", correctAnswer), EditQuestionType.CLOSE_QUESTION), question.questionId)
    then: "question is edited and have new values for given fields"
      equalsCloseQuestions([editedQuestion], [createCloseQuestion(questionId: editedQuestion.questionId, questionContent: questionContent,
        answerA: answerA, answerB: answerB, answerC: answerC, answerD: answerD, correctAnswer: correctAnswer)])
    where:
      questionContent                                              | answerA           | answerB       | answerC               | answerD      | correctAnswer
      "What is the largest mammal on Earth?"                       | "Elephant"        | "Blue Whale"  | "Lion"                | "Giraffe"    | "Blue Whale"
      "Who wrote the play 'Romeo and Juliet'?"                     | "Charles Dickens" | "Jane Austen" | "William Shakespeare" | "Mark Twain" | "William Shakespeare"
      "Which planet is known as the 'Red Planet'?"                 | "Venus"           | "Saturn"      | "Mars"                | "Jupiter"    | "Mars"
      "What is the capital city of Japan?"                         | "Tokyo"           | "Beijing"     | "Seoul"               | "Bangkok"    | "Tokyo"
      "In which year did Christopher Columbus reach the Americas?" | "1455"            | "1776"        | "1603"                | "1492"       | "1492"
  }

  def "Should find close question by id"() {
    given: "there is close question"
      CloseQuestionDto question = questionFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: "Warsaw",
          answerB: "Paris",
          answerC: "Madrid",
          answerD: "Berlin",
          correctAnswer: "Paris"
      ))
    when: "asks for question"
      CloseQuestionDto result = questionFacade.getCloseQuestion(question.questionId)
    then: "finds question with given id"
      equalsCloseQuestions([result], [createCloseQuestion(
          questionId: result.questionId, questionContent:  "What is the capital of France?", answerA: "Warsaw", answerB: "Paris",
          answerC: "Madrid", answerD: "Berlin", correctAnswer: "Paris"
      )])
  }

  def "Should not create close question if one of the answer has the same value"() {
    when: "creates question that contains the same answers"
      questionFacade.createCloseQuestion(createNewCloseQuestion(
          questionContent: "What is the capital of France?",
          answerA: answerA,
          answerB: answerB,
          answerC: answerC,
          answerD: answerD,
          correctAnswer: "Paris"
      ))
    then: "gets error of duplicate answer"
      thrown(DuplicateAnswerValueException)
    where:
      answerA | answerB  | answerC  | answerD
      "Paris" | "Paris"  | "Madrid" | "Berlin"
      "Paris" | "Warsaw" | "Warsaw" | "Berlin"
      "Paris" | "Warsaw" | "Madrid" | "Madrid"
      "Paris" | "Berlin" | "Madrid" | "Berlin"
  }

  def "Should get error if try to get not existing question"() {
    when: "asks for question that does not exist"
      questionFacade.getCloseQuestion(new Random().nextLong())
    then: "gets error of not founded"
      thrown(QuestionNotFoundException)
  }

  def "Should get error if try to edit not existing question"() {
    when: "try to edit question that does not exist"
      questionFacade.editCloseQuestion(
          new EditQuestionDto(Map.of("questionContent", "What is the capital of France?", "answerA", "Paris",
              "answerB", "Warsaw", "answerC", "Madrid", "answerD", "Berlin", "correctAnswer",
              "Paris"), EditQuestionType.CLOSE_QUESTION), new Random().nextLong())
    then: "gets error of not existing question"
      thrown(QuestionNotFoundException)
  }
}
