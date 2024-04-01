package erykmarnik.eLearn.utils.samples

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

trait InstantSample {
  static Instant NOW = Instant.now(Clock.tickMillis(ZoneId.systemDefault()))
  static Instant NOW_SEC = NOW.truncatedTo(ChronoUnit.SECONDS)
  static Instant WEEK_LATER = NOW.plus(7, ChronoUnit.DAYS)
}