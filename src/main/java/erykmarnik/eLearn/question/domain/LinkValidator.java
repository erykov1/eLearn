package erykmarnik.eLearn.question.domain;

import erykmarnik.eLearn.question.exception.InvalidLinkException;
import org.apache.commons.validator.routines.UrlValidator;

class LinkValidator {
  static String checkIfLinkIsValid(String link, String[] schemes) {
    UrlValidator urlValidator = new UrlValidator(schemes);
    if (urlValidator.isValid(link) || link == null) {
      return link;
    } else {
      throw new InvalidLinkException("Not supported link type");
    }
  }
}
