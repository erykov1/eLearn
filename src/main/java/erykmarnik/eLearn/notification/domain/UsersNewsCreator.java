package erykmarnik.eLearn.notification.domain;

import java.util.UUID;

class UsersNewsCreator {
  static UsersNews createUsersNews(String userMail) {
    return new UsersNews(UUID.randomUUID(), userMail);
  }
}
