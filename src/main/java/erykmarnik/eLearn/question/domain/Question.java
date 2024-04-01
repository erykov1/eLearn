package erykmarnik.eLearn.question.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PROTECTED)
abstract class Question {
  @Column(name = "question_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_sequence")
  @SequenceGenerator(name = "question_sequence", sequenceName = "question_sequence", allocationSize = 1)
  @Id
  Long questionId;
  @Column(name = "question_content")
  String questionContent;
  @Column(name = "correct_answer")
  String correctAnswer;
  @Column(name = "image_link")
  String imageLink;
}
