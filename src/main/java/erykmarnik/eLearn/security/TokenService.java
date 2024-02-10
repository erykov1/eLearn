package erykmarnik.eLearn.security;

import erykmarnik.eLearn.utils.InstantProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class TokenService {
  JwtEncoder jwtEncoder;
  InstantProvider instantProvider;

  TokenService(JwtEncoder jwtEncoder, InstantProvider instantProvider) {
    this.jwtEncoder = jwtEncoder;
    this.instantProvider = instantProvider;
  }

  public String generateToken(Authentication authentication) {
    Instant now = instantProvider.now();
    String role = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
    JwtClaimsSet claimsSet = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.HOURS))
        .subject(authentication.getName())
        .claim("role", role)
        .build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
  }
}
