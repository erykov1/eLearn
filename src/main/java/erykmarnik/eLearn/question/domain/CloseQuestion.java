package erykmarnik.eLearn.question.domain;

import erykmarnik.eLearn.question.dto.CloseQuestionDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "close_questions")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class CloseQuestion extends Question {
  @Column(name = "answer_a")
  String answerA;
  @Column(name = "answer_b")
  String answerB;
  @Column(name = "answer_c")
  String answerC;
  @Column(name = "answer_d")
  String answerD;

  @Builder(toBuilder = true)
  public CloseQuestion(Long questionId, String questionContent, String answerA, String answerB,
                       String answerC, String answerD, String correctAnswer, String imageLink, String mediaLink) {
    super(questionId, questionContent, correctAnswer, imageLink, mediaLink);
    this.answerA = answerA;
    this.answerB = answerB;
    this.answerC = answerC;
    this.answerD = answerD;
  }

  CloseQuestionDto dto() {
    return CloseQuestionDto.builder()
        .questionId(super.getQuestionId())
        .questionContent(super.getQuestionContent())
        .answerA(answerA)
        .answerB(answerB)
        .answerC(answerC)
        .answerD(answerD)
        .correctAnswer(super.getCorrectAnswer())
        .imageLink(super.getImageLink())
        .mediaLink(super.getMediaLink())
        .build();
  }
}
