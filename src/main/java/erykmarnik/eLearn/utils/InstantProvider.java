package erykmarnik.eLearn.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Component
@NoArgsConstructor
public class InstantProvider {
  private Clock clock = Clock.systemUTC();

  public Instant now() {
    return Instant.now(clock);
  }

  public void useFixedClock(Instant instant) {
    clock = Clock.fixed(instant, ZoneId.systemDefault());
  }

  public void useSystemClock() {
    clock = Clock.systemUTC();
  }
}
