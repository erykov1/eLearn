package erykmarnik.eLearn.utils;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/time")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class TimeProviderController {
  InstantProvider instantProvider;

  @Autowired
  public TimeProviderController(InstantProvider instantProvider) {
    this.instantProvider = instantProvider;
  }

  @PostMapping("/fixedClock")
  @PreAuthorize("hasRole('ADMIN')")
  @Hidden
  void useFixedClock(@RequestBody Instant instant) {
    instantProvider.useFixedClock(instant);
  }

  @GetMapping("/systemClock")
  @PreAuthorize("hasRole('ADMIN')")
  @Hidden
  void useSystemClock() {
    instantProvider.useSystemClock();
  }
}
