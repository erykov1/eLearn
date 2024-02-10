package erykmarnik.eLearn.security;

import erykmarnik.eLearn.security.request.LoginRequest;
import erykmarnik.eLearn.user.domain.UserFacade;
import erykmarnik.eLearn.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class AuthController {
  TokenService tokenService;
  AuthenticationManager authenticationManager;
  PasswordEncoder passwordEncoder;
  UserFacade userFacade;

  @Autowired
  public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserFacade userFacade) {
    this.tokenService = tokenService;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
    this.userFacade = userFacade;
  }

  @PostMapping("/token")
  ResponseEntity<String> generateToken(@RequestBody LoginRequest loginRequest) {
    UserDto user = userFacade.findUserByUsername(loginRequest.getUsername());
    boolean matchPwd = passwordEncoder.bCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword());
    if (matchPwd) {
      Authentication auth = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), user.getPassword())
      );
      return ResponseEntity.ok(tokenService.generateToken(auth));
    } else {
      return ResponseEntity.status(401).body("Bad credentials");
    }
  }
}
