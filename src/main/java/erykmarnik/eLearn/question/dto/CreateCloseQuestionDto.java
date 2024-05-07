package erykmarnik.eLearn.question.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateCloseQuestionDto {
  String questionContent;
  String answerA;
  String answerB;
  String answerC;
  String answerD;
  String correctAnswer;
  String imageLink;
  String mediaLink;
}
