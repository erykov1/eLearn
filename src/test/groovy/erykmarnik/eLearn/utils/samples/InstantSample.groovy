package erykmarnik.eLearn.utils.samples

import java.time.Clock
import java.time.Instant
import java.time.ZoneId

trait InstantSample {
  static Instant NOW = Instant.now(Clock.tickMillis(ZoneId.systemDefault()))
}