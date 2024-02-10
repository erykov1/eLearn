package erykmarnik.eLearn.integration

import com.fasterxml.jackson.databind.ObjectMapper
import erykmarnik.eLearn.user.dto.CreateUserDto
import erykmarnik.eLearn.user.dto.UserDto
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.nio.charset.StandardCharsets

class ELearnApi {
  private final UserApi userApi

  ELearnApi(MockMvc mockMvc, ObjectMapper objectMapper) {
    this.userApi = new UserApi(mockMvc, objectMapper)
  }

  UserApi user() {
    userApi
  }

  class UserApi {
    private final MockMvc mvc
    private final ObjectMapper mapper

    UserApi(MockMvc mvc, ObjectMapper mapper) {
      this.mvc = mvc
      this.mapper = mapper
    }

    UserDto registerStudent(CreateUserDto createStudent) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/user/register")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(createStudent))
      )
      checkResponse(perform.andReturn().response)
      UserDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(UserDto.class))
      value
    }

    UserDto registerAdmin(CreateUserDto createAdmin) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/user/register/admin")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(createAdmin))
      )
      checkResponse(perform.andReturn().response)
      UserDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(UserDto.class))
      value
    }

    List<UserDto> getAll() {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/user/all"))
      checkResponse(perform.andReturn().response)
      List<UserDto> value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8),
          mapper.getTypeFactory().constructCollectionType(List.class, UserDto.class))
      value
    }

    void cleanup() {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/user/cleanup"))
      checkResponse(perform.andReturn().response)
    }
  }

  private static void checkResponse(MockHttpServletResponse response) {
    if(response.status != 200) {
      throw new RuntimeException(response.getIncludedUrl() + " failed with status " + response.status)
    }
  }
}
