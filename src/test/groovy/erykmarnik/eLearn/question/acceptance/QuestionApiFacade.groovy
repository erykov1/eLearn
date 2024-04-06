package erykmarnik.eLearn.question.acceptance

import com.fasterxml.jackson.databind.ObjectMapper
import erykmarnik.eLearn.integration.ELearnApi
import erykmarnik.eLearn.question.dto.CloseQuestionDto
import erykmarnik.eLearn.question.dto.CreateCloseQuestionDto
import erykmarnik.eLearn.question.dto.CreateOpenQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionDto
import erykmarnik.eLearn.question.dto.OpenQuestionDto
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import java.nio.charset.StandardCharsets

class QuestionApiFacade extends ELearnApi {
  private final MockMvc mvc
  private final ObjectMapper mapper

  QuestionApiFacade(MockMvc mvc, ObjectMapper mapper) {
    this.mvc = mvc
    this.mapper = mapper
  }

  CloseQuestionDto createCloseQuestion(CreateCloseQuestionDto createCloseQuestion) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/question/create/closeQuestion")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(createCloseQuestion))
    )
    checkResponse(perform.andReturn().response)
    CloseQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(CloseQuestionDto.class))
    value
  }

  OpenQuestionDto createOpenQuestion(CreateOpenQuestionDto createCloseQuestion) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/question/create/openQuestion")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(createCloseQuestion))
    )
    checkResponse(perform.andReturn().response)
    OpenQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(OpenQuestionDto.class))
    value
  }

  CloseQuestionDto editCloseQuestion(EditQuestionDto editQuestion, Long questionId) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.patch("/api/question/edit/closeQuestion/{questionId}", questionId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(editQuestion))
    )
    checkResponse(perform.andReturn().response)
    CloseQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(CloseQuestionDto.class))
    value
  }

  OpenQuestionDto editOpenQuestion(EditQuestionDto editQuestion, Long questionId) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.patch("/api/question/edit/openQuestion/{questionId}", questionId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(editQuestion))
    )
    checkResponse(perform.andReturn().response)
    OpenQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(OpenQuestionDto.class))
    value
  }

  CloseQuestionDto getCloseQuestion(Long questionId) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/question/closeQuestion/{questionId}", questionId))
    checkResponse(perform.andReturn().response)
    CloseQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(CloseQuestionDto.class))
    value
  }

  OpenQuestionDto getOpenQuestion(Long questionId) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/question/openQuestion/{questionId}", questionId))
    checkResponse(perform.andReturn().response)
    OpenQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(OpenQuestionDto.class))
    value
  }

  void cleanup() {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/question/cleanup"))
    checkResponse(perform.andReturn().response)
  }
}
