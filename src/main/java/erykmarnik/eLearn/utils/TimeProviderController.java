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
@Hidden
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
  void useFixedClock(@RequestBody Instant instant) {
    instantProvider.useFixedClock(instant);
  }

  @GetMapping("/systemClock")
  @PreAuthorize("hasRole('ADMIN')")
  void useSystemClock() {
    instantProvider.useSystemClock();
  }
}
