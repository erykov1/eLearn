package erykmarnik.eLearn.userresult.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageInfoDto {
  private static final Integer DEFAULT_PAGE_SIZE = 15;
  public static final PageInfoDto DEFAULT = new PageInfoDto(DEFAULT_PAGE_SIZE, 0);

  @Parameter(description = "Page size", in = ParameterIn.QUERY)
  Integer pageSize;
  @Parameter(description = "Page number", in = ParameterIn.QUERY)
  Integer pageNumber;
}