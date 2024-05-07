package erykmarnik.eLearn.question.domain;

class LinkProvider {
  static String[] getSupportedImageLinks() {
    return new String[] {"http", "https", "data"};
  }

  static String[] getSupportedMediaLinks() {
    return new String[] {"http", "https"};
  }
}
