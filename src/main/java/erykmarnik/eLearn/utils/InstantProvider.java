package erykmarnik.eLearn.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class InstantProvider {
  private static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  Clock clock = Clock.systemUTC();

  public Instant now() {
    return Instant.now(clock);
  }

  public void useFixedClock(Instant instant) {
    clock = Clock.fixed(instant, ZoneId.systemDefault());
  }

  public static Instant fromFormatted(String time) {
    LocalDateTime localDate = LocalDateTime.parse(time, SHORT_DATE_FORMATTER);
    ZonedDateTime zonedDateTime = localDate.atZone(ZoneId.of("CET"));
    return zonedDateTime.toInstant();
  }
}
