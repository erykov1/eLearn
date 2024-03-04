package erykmarnik.eLearn.question.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@AllArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EditQuestionDto {
  Map<String, String> editedValues;
  EditQuestionType editQuestionType;
}
