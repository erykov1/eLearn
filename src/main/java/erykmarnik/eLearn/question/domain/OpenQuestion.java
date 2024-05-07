package erykmarnik.eLearn.question.domain;

import erykmarnik.eLearn.question.dto.OpenQuestionDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "open_questions")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class OpenQuestion extends Question {

  @Builder(toBuilder = true)
  public OpenQuestion(Long questionId, String questionContent, String correctAnswer, String imageLink, String mediaLink) {
    super(questionId, questionContent, correctAnswer, imageLink, mediaLink);
  }

  OpenQuestionDto dto() {
    return OpenQuestionDto.builder()
        .questionId(super.getQuestionId())
        .questionContent(super.getQuestionContent())
        .correctAnswer(super.getCorrectAnswer())
        .imageLink(super.getImageLink())
        .mediaLink(super.getMediaLink())
        .build();
  }
}
