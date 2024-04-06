package erykmarnik.eLearn.utils

import com.fasterxml.jackson.databind.ObjectMapper
import erykmarnik.eLearn.integration.ELearnApi
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import java.time.Instant

class TimeProviderApiFacade extends ELearnApi {
  private final MockMvc mvc
  private final ObjectMapper mapper

  TimeProviderApiFacade(MockMvc mvc, ObjectMapper mapper) {
    this.mvc = mvc
    this.mapper = mapper
  }

  void useFixedClock(Instant instant) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/time/fixedClock")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(instant))
    )
    checkResponse(perform.andReturn().response)
  }

  void useSystemClock() {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/time/systemClock")
        .contentType(MediaType.APPLICATION_JSON)
    )
    checkResponse(perform.andReturn().response)
  }
}
