package erykmarnik.eLearn.question.domain;

import erykmarnik.eLearn.question.exception.InvalidImageLinkException;
import org.apache.commons.validator.routines.UrlValidator;

class ImageLinkValidator {
  private static final String[] SCHEMES = {"http", "https", "data"};

  static String checkIfImageLinkIsValid(String link) {
    UrlValidator urlValidator = new UrlValidator(SCHEMES);
    if (urlValidator.isValid(link) || link == null) {
      return link;
    } else {
      throw new InvalidImageLinkException("Not supported image link type");
    }
  }
}
