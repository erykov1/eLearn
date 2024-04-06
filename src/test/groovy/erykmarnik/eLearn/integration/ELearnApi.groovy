package erykmarnik.eLearn.integration

import org.springframework.mock.web.MockHttpServletResponse

class ELearnApi {
  protected static void checkResponse(MockHttpServletResponse response) {
    if(response.status != 200) {
      throw new RuntimeException(response.getIncludedUrl() + " failed with status " + response.status)
    }
  }
}
