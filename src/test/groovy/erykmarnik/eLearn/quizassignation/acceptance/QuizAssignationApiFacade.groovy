package erykmarnik.eLearn.quizassignation.acceptance

import com.fasterxml.jackson.databind.ObjectMapper
import erykmarnik.eLearn.integration.ELearnApi
import erykmarnik.eLearn.quizassignation.dto.CreateQuizAssignationDto
import erykmarnik.eLearn.quizassignation.dto.QuizAssignationDto
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import java.nio.charset.StandardCharsets

class QuizAssignationApiFacade extends ELearnApi {
  private final MockMvc mvc
  private final ObjectMapper mapper

  QuizAssignationApiFacade(MockMvc mvc, ObjectMapper mapper) {
    this.mvc = mvc
    this.mapper = mapper
  }

  QuizAssignationDto createQuizAssignation(CreateQuizAssignationDto createQuizAssignation) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/quizAssignation/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(createQuizAssignation))
    )
    checkResponse(perform.andReturn().response)
    QuizAssignationDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(QuizAssignationDto.class))
    value
  }

  void deleteQuizAssignation(UUID quizId, Long questionId) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.delete("/api/quizAssignation/delete/{quizId}/{questionId}", quizId, questionId)
        .contentType(MediaType.APPLICATION_JSON)
    )
    checkResponse(perform.andReturn().response)
  }

  List<Long> getAllQuestionsAssignedToQuiz(UUID quizId) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/quizAssignation/getQuestions/{quizId}", quizId)
        .contentType(MediaType.APPLICATION_JSON)
    )
    checkResponse(perform.andReturn().response)
    List<Long> value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8),
        mapper.getTypeFactory().constructCollectionType(List.class, Long.class))
    value
  }

  QuizAssignationDto getQuizAssignation(Long assignationId) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/quizAssignation/get/{assignationId}", assignationId)
        .contentType(MediaType.APPLICATION_JSON)
    )
    checkResponse(perform.andReturn().response)
    QuizAssignationDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(QuizAssignationDto.class))
    value
  }

  void cleanup() {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/quizAssignation/cleanup"))
    checkResponse(perform.andReturn().response)
  }
}
