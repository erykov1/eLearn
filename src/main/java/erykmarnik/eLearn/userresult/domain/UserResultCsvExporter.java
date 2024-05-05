package erykmarnik.eLearn.userresult.domain;

import de.siegmar.fastcsv.writer.CsvWriter;
import erykmarnik.eLearn.userresult.dto.UserResultDto;
import erykmarnik.eLearn.userresult.exception.ExportCsvException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserResultCsvExporter {
  static final String[] HEADERS = {"id", "result", "learningObjectId", "completedAt", "userId", "startedAt", "userResultVisibilityType"};
  OutputStreamWriter writer;

  public UserResultCsvExporter(OutputStream writer) {
    this.writer = new OutputStreamWriter(writer, StandardCharsets.UTF_8);
  }

  void exportToCsv(List<UserResultDto> userResults) {
    try (CsvWriter csvWriter = CsvWriter.builder().build(writer)) {
      csvWriter.writeRecord(HEADERS);
      userResults.stream()
          .map(this::buildRecord)
          .forEach(csvWriter::writeRecord);
    } catch (IOException e) {
      throw new ExportCsvException("Error while exporting user results to csv");
    }
  }

  List<String> buildRecord(UserResultDto userResult) {
    return List.of(
        getRecord(userResult.getId().toString()),
        getRecord(userResult.getResult().toString()),
        getRecord(userResult.getLearningObjectId().toString()),
        getInstantRecord(userResult.getCompletedAt()),
        getRecord(userResult.getUserId().toString()),
        getInstantRecord(userResult.getStartedAt()),
        getRecord(userResult.getUserResultVisibilityType().name())
    );
  }

  String getRecord(String value) {
    return Optional.ofNullable(value).orElse("");
  }

  String getInstantRecord(Instant value) {
    return value == null ? "" : Date.from(value).toString();
  }
}
