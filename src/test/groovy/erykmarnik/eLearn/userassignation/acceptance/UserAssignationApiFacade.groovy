package erykmarnik.eLearn.userassignation.acceptance

import com.fasterxml.jackson.databind.ObjectMapper
import erykmarnik.eLearn.integration.ELearnApi
import erykmarnik.eLearn.userassignation.dto.CreateUserAssignationDto
import erykmarnik.eLearn.userassignation.dto.UserAssignationDto
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import java.nio.charset.StandardCharsets

class UserAssignationApiFacade extends ELearnApi {
  private final MockMvc mvc
  private final ObjectMapper mapper

  UserAssignationApiFacade(MockMvc mvc, ObjectMapper mapper) {
    this.mvc = mvc
    this.mapper = mapper
  }

  UserAssignationDto createUserAssignation(CreateUserAssignationDto createUserAssignation) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/userAssignation/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(createUserAssignation))
    )
    checkResponse(perform.andReturn().response)
    UserAssignationDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8),
        mapper.getTypeFactory().constructType(UserAssignationDto.class))
    value
  }

  UserAssignationDto changeToInProgress(Long id) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.put("/api/userAssignation/inProgress/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
    )
    checkResponse(perform.andReturn().response)
    UserAssignationDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8),
        mapper.getTypeFactory().constructType(UserAssignationDto.class))
    value
  }

  UserAssignationDto changeToCompleted(Long id) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.put("/api/userAssignation/completed/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
    )
    checkResponse(perform.andReturn().response)
    UserAssignationDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8),
        mapper.getTypeFactory().constructType(UserAssignationDto.class))
    value
  }

  void cleanup() {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/userAssignation/cleanup"))
    checkResponse(perform.andReturn().response)
  }
}
