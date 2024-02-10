package erykmarnik.eLearn.user.domain;

import erykmarnik.eLearn.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
  UserRepository userRepository;

  @Autowired
  UserDetailsServiceImplementation(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDto user = userRepository.findByUsername(username).map(User::dto)
        .orElseThrow(() -> new UsernameNotFoundException("Can not find user with such username: " + username));

    return CustomUserDetailsService.builder()
        .userId(user.getUserId())
        .username(user.getUsername())
        .password("{noop}" + user.getPassword())
        .userRole(UserRole.valueOf(user.getUserRole().name()))
        .build();
  }
}
